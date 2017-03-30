package ch.uzh.ifi.seal.soprafs17.service;

/**
 * Created by Cristian on 26.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.Player;
import ch.uzh.ifi.seal.soprafs17.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.SupplySled;
import ch.uzh.ifi.seal.soprafs17.repository.SupplySledRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SupplySledService {

    private final Logger log = LoggerFactory.getLogger(SupplySledService.class);

    private static final int MAX_STONES = 5;

    private final SupplySledRepository supplySledRepository;

    @Autowired
    public SupplySledService(SupplySledRepository supplySledRepository) {
        this.supplySledRepository = supplySledRepository;
    }

    public SupplySled createSupplySled(Player player){
        // initializing a SupplySled
        log.debug("creating SupplySled for Player with playerId: " + player.getId());
        SupplySled supplySled = new SupplySled();
        supplySled.setStones(new ArrayList<>());
        supplySled.setPlayer(player);

        supplySledRepository.save(supplySled);

        return supplySled;
    }

    /*
     * @Param SupplySled, Stone to be added
     * This methods adds a stone to the supply sled
     */
    public void addStone(SupplySled supplySled, Stone stone) {
        log.debug("Adding stone to SupplySled");
        if (supplySled.getStones().size() < MAX_STONES) {
            List<Stone> stones = supplySled.getStones();
            stones.add(stone);
            supplySled.setStones(stones);

            supplySledRepository.save(supplySled);
        }
        else {
            log.debug("Failed to add Stone to the SupplySled");
        }
    }
}
