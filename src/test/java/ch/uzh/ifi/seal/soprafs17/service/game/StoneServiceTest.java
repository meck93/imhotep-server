package ch.uzh.ifi.seal.soprafs17.service.game;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.repository.StoneRepository;
import ch.uzh.ifi.seal.soprafs17.service.game.StoneService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Test class for the StoneResource REST resource.
 *
 * @see StoneService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class StoneServiceTest {

    @Autowired
    private StoneService stoneService;

    @Autowired
    private StoneRepository stoneRepository;

    @Test
    public void createStone() {
        // TODO: test stoneService.createStone()
    }

    @Test
    public void createStones() {
        // TODO: test stoneService.createStones()
    }
}
