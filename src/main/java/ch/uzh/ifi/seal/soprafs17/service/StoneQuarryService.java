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

import java.util.ArrayList;

@Service
@Transactional
public class StoneQuarryService {

    private final Logger log = LoggerFactory.getLogger(StoneQuarryService.class);
    private final StoneQuarryRepository stoneQuarryRepository;

    @Autowired
    public StoneQuarryService(StoneQuarryRepository stoneQuarryRepository) {
        this.stoneQuarryRepository = stoneQuarryRepository;
    }

    public StoneQuarry createStoneQuarry(){
        log.debug("Creating a StoneQuarry");

        StoneQuarry stoneQuarry = new StoneQuarry();

        stoneQuarry.setBlackStones(new ArrayList<>());
        stoneQuarry.setWhiteStones(new ArrayList<>());
        stoneQuarry.setBrownStones(new ArrayList<>());
        stoneQuarry.setGrayStones(new ArrayList<>());

        stoneQuarryRepository.save(stoneQuarry);

        return stoneQuarry;
    }

}