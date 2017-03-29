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

        public Round createRound(Long gameId, Game game){
                // HERE EVERYTHING NEEDS TO BE DONE BEFORE A NEW ROUND CAN START
                log.debug("creating new round: ");
                Round newRound = new Round();
                RoundCard newRoundCard = roundCardService.getRoundCard(gameId);
                roundCardService.deleteCard(newRoundCard);
                //TODO Implement the creation of a round - including whatever
                newRound.setShips(shipService.createShips(game,game.getRoundCounter()));
                roundRepository.save(newRound);

                return newRound;
        }
}
