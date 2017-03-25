package ch.uzh.ifi.seal.soprafs17.service;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.Round;
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

        @Autowired
        public RoundService(RoundRepository roundRepository) {
            this.roundRepository = roundRepository;
        }

        public Round testRound(){
                Round testRound = new Round();
                return testRound;
        }
}
