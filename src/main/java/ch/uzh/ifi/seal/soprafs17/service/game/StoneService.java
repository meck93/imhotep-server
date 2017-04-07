package ch.uzh.ifi.seal.soprafs17.service.game;

/**
 * Created by Cristian on 26.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.repository.StoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StoneService {

    private final Logger log = LoggerFactory.getLogger(StoneService.class);
    private final StoneRepository stoneRepository;

    @Autowired
    public StoneService(StoneRepository stoneRepository) {
        this.stoneRepository = stoneRepository;
    }

    public Stone createStone(String color) {
        log.debug("Creating a stone");

        Stone stone = new Stone();
        stone.setColor(color);

        stoneRepository.save(stone);

        return stone;
    }

    public List<Stone> createStones(String color) {
        log.debug("Creating the initial 30 stones of each color");

        ArrayList<Stone> stones = new ArrayList<>();
        // Creating the initial 30 stones
        for(int i = 0; i < GameConstants.START_STONES; i++) {
            Stone stone = createStone(color);
            stones.add(stone);
        }
        // Ensuring the initial size is correct
        if (stones.size() == GameConstants.START_STONES) {
            return stones;
        }
        else {
            log.error("Failed to initial stones for color: " + color);
            return null;
        }
    }
}
