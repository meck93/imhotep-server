package ch.uzh.ifi.seal.soprafs17.service;

/**
 * Created by Cristian on 26.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.StoneQuarry;
import ch.uzh.ifi.seal.soprafs17.repository.StoneQuarryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StoneQuarryService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final StoneQuarryRepository stoneQuarryRepository;

    @Autowired
    public StoneQuarryService(StoneQuarryRepository stoneQuarryRepository) {
        this.stoneQuarryRepository = stoneQuarryRepository;
    }

    public StoneQuarry createStoneQuarry(Long gameId, int amountOfPlayer){
        log.debug("creating new stone quarry: ");
        StoneQuarry stoneQuarry = new StoneQuarry();
        stoneQuarryRepository.save(stoneQuarry);
        return stoneQuarry;
    }

}