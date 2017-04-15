package ch.uzh.ifi.seal.soprafs17.service.game;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.repository.StoneQuarryRepository;
import ch.uzh.ifi.seal.soprafs17.service.game.StoneQuarryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Test class for the StoneQuarryResource REST resource.
 *
 * @see StoneQuarryService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class StoneQuarryServiceTest {

    @Autowired
    private StoneQuarryService stoneQuarryService;

    @Autowired
    private StoneQuarryRepository stoneQuarryRepository;

    @Test
    public void createStoneQuarry() {
        // TODO: test stoneQuarryService.createStoneQuarry()
    }

    @Test
    public void fillQuarry() {
        // TODO: test stoneQuarryService.fillQuarry()
    }
}