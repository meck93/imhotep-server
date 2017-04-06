package ch.uzh.ifi.seal.soprafs17.service;

/**
 * Created by Cristian on 26.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.StoneQuarry;
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
    private final StoneService stoneService;

    @Autowired
    public StoneQuarryService(StoneQuarryRepository stoneQuarryRepository, StoneService stoneService) {
        this.stoneQuarryRepository = stoneQuarryRepository;
        this.stoneService = stoneService;
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

    public void fillQuarry(StoneQuarry stoneQuarry){
        log.debug("Filling the StoneQuarry with the initial stones of each color");

        stoneQuarry.setBlackStones(stoneService.createStones(GameConstants.BLACK));
        stoneQuarry.setWhiteStones(stoneService.createStones(GameConstants.WHITE));
        stoneQuarry.setBrownStones(stoneService.createStones(GameConstants.BROWN));
        stoneQuarry.setGrayStones(stoneService.createStones(GameConstants.GRAY));

        stoneQuarryRepository.save(stoneQuarry);
    }
}