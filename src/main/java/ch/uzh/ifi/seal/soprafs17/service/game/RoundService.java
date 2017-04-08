package ch.uzh.ifi.seal.soprafs17.service.game;

import ch.uzh.ifi.seal.soprafs17.entity.card.RoundCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import ch.uzh.ifi.seal.soprafs17.repository.RoundRepository;
import ch.uzh.ifi.seal.soprafs17.service.card.MarketCardService;
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
        private final StoneService stoneService;

        @Autowired
        public RoundService(RoundRepository roundRepository, RoundCardService roundCardService, ShipService shipService, StoneService stoneService, MarketCardService marketCardService) {
            this.roundRepository = roundRepository;
            this.roundCardService = roundCardService;
            this.shipService = shipService;
            this.stoneService = stoneService;
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
            newRound.setShips(new ArrayList<>());
            newRound.setRoundNumber(0);

            // getting a new roundCard
            RoundCard newRoundCard = roundCardService.getRoundCard(gameId);
            newRound.setCard(newRoundCard);

            roundRepository.save(newRound);

            return newRound;
        }

        public Round initializeRound(Long roundId, int roundNumber) {
            log.debug("Initializing Round: " + roundId);

            Round round = roundRepository.findById(roundId);

            // setting the correct round number
            round.setRoundNumber(roundNumber);
            // adding ships to the round
            List<Ship> currentShips = shipService.createShips(round.getCard());
            round.setShips(currentShips);

            roundRepository.save(round);

            return round;
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

            if (resultRound == null) throw new NotFoundException(gameId, "Round");

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

        public void createDummyData(Long gameId, int roundNr) {
            Round round = this.getRoundByNr(gameId, roundNr);
            List<Ship> ships = round.getShips();

            for (Ship ship : ships) {
                ArrayList<Stone> testStones = new ArrayList<>();
                switch (ship.getMAX_STONES()) {
                    case 1: testStones.add(stoneService.createStone("BLACK")); ship.setStones(testStones); break;
                    case 2: testStones.add(stoneService.createStone("GRAY")); testStones.add(stoneService.createStone("GRAY")); ship.setStones(testStones); break;
                    case 3: testStones.add(stoneService.createStone("BROWN")); testStones.add(stoneService.createStone("BROWN")); testStones.add(stoneService.createStone("BROWN")); ship.setStones(testStones); break;
                    case 4: testStones.add(stoneService.createStone("WHITE")); testStones.add(stoneService.createStone("WHITE")); testStones.add(stoneService.createStone("WHITE")); ship.setStones(testStones); break;
                }
            }

            roundRepository.save(round);
        }
}