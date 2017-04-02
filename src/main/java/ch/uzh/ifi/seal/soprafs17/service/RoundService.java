package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.entity.Round;
import ch.uzh.ifi.seal.soprafs17.entity.RoundCard;
import ch.uzh.ifi.seal.soprafs17.entity.Ship;
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
            newRound.setShips(new ArrayList<>());
            newRound.setMoves(new ArrayList<>());

            // getting a new roundCard
            RoundCard newRoundCard = roundCardService.getRoundCard(gameId);
            newRound.setCard(newRoundCard);

            roundRepository.save(newRound);

            return newRound;
        }

        public Round initializeRound(Long roundId) {
            log.debug("Initializing Round: " + roundId);

            Round round = roundRepository.findById(roundId);

            // adding ships to the round
            List<Ship> currentShips = shipService.createShips(round.getCard());
            round.setShips(currentShips);

            roundRepository.save(round);

            return round;
        }
}