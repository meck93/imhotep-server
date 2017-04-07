package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.game.SupplySled;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.repository.PlayerRepository;
import ch.uzh.ifi.seal.soprafs17.repository.SupplySledRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Test class for the SupplySledResource REST resource.
 *
 * @see SupplySledService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class SupplySledService {

    @Autowired
    SupplySledService supplySledService;

    @Autowired
    SupplySledRepository supplySledRepository;

    public void createSupplySled() {
        SupplySled testSupplySled = new SupplySled();
        Player testPlayer = new Player();
        supplySledService.createSupplySled();

    }

    public void addStone() {
        Stone testStone = new Stone();

    }


}
