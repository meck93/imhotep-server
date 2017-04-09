package ch.uzh.ifi.seal.soprafs17.service.game;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
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

    public StoneQuarry createStoneQuarry(Game game){
        log.debug("Creating a StoneQuarry");

        StoneQuarry stoneQuarry = new StoneQuarry();
        stoneQuarry.setGame(game);

        // Creating the StoneQuarry for the minimum Nr of Players
        stoneQuarry.setBlackStones(new ArrayList<>());
        stoneQuarry.setWhiteStones(new ArrayList<>());

        switch (game.getPlayers().size()) {
            // Creating 1 additional StoneQuarry for Player Nr 3
            case 3: stoneQuarry.setBrownStones(new ArrayList<>()); break;
            // Creating 2 additional StoneQuarry for Player Nr 3 & 4
            case 4: stoneQuarry.setBrownStones(new ArrayList<>()); stoneQuarry.setGrayStones(new ArrayList<>()); break;
        }

        stoneQuarryRepository.save(stoneQuarry);

        return stoneQuarry;
    }

    public void fillQuarry(StoneQuarry stoneQuarry){
        log.debug("Filling the StoneQuarry with the initial stones of each color");

        // Filling the first two StoneQuarrys with the amount of Starting-Stones
        stoneQuarry.setBlackStones(stoneService.createStones(GameConstants.BLACK));
        stoneQuarry.setWhiteStones(stoneService.createStones(GameConstants.WHITE));

        switch (stoneQuarry.getGame().getPlayers().size()) {
            // Creating 1 additional StoneQuarry for Player Nr 3
            case 3:
                stoneQuarry.setBrownStones(stoneService.createStones(GameConstants.BROWN)); break;
            // Creating 2 additional StoneQuarry for Player Nr 3 & 4
            case 4:
                stoneQuarry.setBrownStones(stoneService.createStones(GameConstants.BROWN));
                stoneQuarry.setGrayStones(stoneService.createStones(GameConstants.GRAY)); break;
        }

        stoneQuarryRepository.save(stoneQuarry);
    }
}