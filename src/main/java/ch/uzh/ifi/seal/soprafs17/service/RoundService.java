package ch.uzh.ifi.seal.soprafs17.service;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.entity.Round;
import ch.uzh.ifi.seal.soprafs17.entity.RoundCard;
import ch.uzh.ifi.seal.soprafs17.repository.RoundRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoundService {

        private final Logger log = LoggerFactory.getLogger(UserService.class);
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

                log.debug("creating new round: ");
                Round newRound = new Round();
                RoundCard newRoundCard = roundCardService.getRoundCard(gameId);                 // getting a new card
                roundCardService.deleteCard(newRoundCard);                                      // delete the dealt card
                newRound.setShips(shipService.createShips(game,game.getRoundCounter()));        // adding ships to a round
                roundRepository.save(newRound);

                return newRound;
        }
}
