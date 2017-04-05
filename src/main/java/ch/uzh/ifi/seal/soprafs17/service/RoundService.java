package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.entity.card.RoundCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.repository.RoundRepository;
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

        public List<Round> listRounds(Long gameId) {
            log.debug("List all Rounds of Game: " + gameId);

            List<Round> allRounds = (List<Round>) roundRepository.findAll();
            ArrayList<Round> result = new ArrayList<>();

            for (Round round : allRounds) {
                if (round.getGame().getId().equals(gameId)){
                    result.add(round);
                }
            }
            return result;
        }

        public Round getRoundByNr(Long gameId, int roundNumber) {
            log.debug("Round: " + roundNumber + " of Game: " + gameId);

            List<Round> allRounds = this.listRounds(gameId);

            for (Round round : allRounds) {
                if (round.getRoundNumber() == roundNumber) {
                    return round;
                }
            }

            log.error("Round: " + roundNumber + " in Game: " + gameId + " not found!");
            return null;
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