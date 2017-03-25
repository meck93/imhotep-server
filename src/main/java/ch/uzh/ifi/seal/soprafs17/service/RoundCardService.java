package ch.uzh.ifi.seal.soprafs17.service;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.repository.RoundCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoundCardService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final RoundCardRepository roundCardRepository;

    @Autowired
    public RoundCardService(RoundCardRepository roundCardRepository) {
        this.roundCardRepository = roundCardRepository;
    }
}
