package ch.uzh.ifi.seal.soprafs17.service.game;

import ch.uzh.ifi.seal.soprafs17.entity.card.RoundCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import ch.uzh.ifi.seal.soprafs17.repository.RoundRepository;
import ch.uzh.ifi.seal.soprafs17.service.card.RoundCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoundService {

        private final Logger log = LoggerFactory.getLogger(RoundService.class);
  
        private final RoundRepository roundRepository;
        private final RoundCardService roundCardService;
        private final ShipService shipService;

        @Autowired
        public RoundService(RoundRepository roundRepository, RoundCardService roundCardService, ShipService shipService) {
            this.roundRepository = roundRepository;
            this.roundCardService = roundCardService;
            this.shipService = shipService;
        }

        /**
         * Calls the roundCardService and gets one roundcard which belongs to the game
         * and deltes the dealt card from the repository.
         * @param gameId
         * @param game
         * @pre game =/= NULL
         * @post round.getRounds.get(old_index+1) > round.getRounds.get(old_index)
         * @return RoundCard
         */
        public Round createRound(Long gameId, Game game){
            log.debug("Creating new round for Game: " + gameId);

            // Creating a new Round
            Round newRound = new Round();
            newRound.setGame(game);
            newRound.setRoundNumber(game.getRounds().size() + 1);

            roundRepository.save(newRound);

            return newRound;
        }

        public Round initializeRound(Long roundId, Long gameId) {
            log.debug("Initializing Round: " + roundId);

            Round round = roundRepository.findById(roundId);

            // getting a new roundCard
            RoundCard newRoundCard = roundCardService.getRoundCard(gameId);
            round.setCard(newRoundCard);

            // adding ships to the round
            List<Ship> currentShips = shipService.createShips(newRoundCard);
            round.setShips(currentShips);

            roundRepository.save(round);

            return round;
        }

        /*
         * Checks whether all ships in the round have been sailed
         */
        public boolean goToNextRound(Round round){
            log.debug("Checking whether all ships have sailed in RoundNr: " + round.getRoundNumber() + " RoundNr: " + round.getGame().getId());

            for (Ship ship : round.getShips()){
                if (!ship.isHasSailed()){
                    return false;
                }
            }
            return true;
        }

        /*
         * Finding all the rounds associated with a specific Game
         */
        public List<Round> listRounds(Long gameId) {
            log.debug("List all Rounds of Game: " + gameId);

            List<Round> allRounds = (List<Round>) roundRepository.findAll();
            ArrayList<Round> result = new ArrayList<>();

            // Adding all the required Rounds to the returnList
            for (Round round : allRounds) {
                if (round.getGame().getId().equals(gameId)){
                    result.add(round);
                }
            }

            // If the list is empty -> no rounds exist -> throw exception
            if (result.isEmpty()) throw new NotFoundException(gameId, "Rounds");

            return result;
        }

        /*
         * Finding a specific Round in a Game
         */
        public Round getRoundByNr(Long gameId, int roundNumber) {
            log.debug("Round: " + roundNumber + " of Game: " + gameId);

            List<Round> allRounds = this.listRounds(gameId);
            Round resultRound = null;

            for (Round round : allRounds) {
                if (round.getRoundNumber() == roundNumber) {
                    resultRound = round;
                }
            }

            if (resultRound == null) throw new NotFoundException(roundNumber, "Round");

            return resultRound;
        }

        /*
         * Finding all the Ships in a Round
         */
        public List<Ship> getShips(Long gameId, int roundNumber){
            log.debug("Extracting the Ships of Round: " + roundNumber + " of Game: " + gameId);

            Round round = this.getRoundByNr(gameId, roundNumber);

            if (round.getShips().isEmpty()) throw new NotFoundException(gameId, "Ships");

            return round.getShips();
        }

        /*
         * Finding a specific ship in a Round
         */
        public Ship getShip(Long shipId){
            return shipService.findShip(shipId);
        }
}