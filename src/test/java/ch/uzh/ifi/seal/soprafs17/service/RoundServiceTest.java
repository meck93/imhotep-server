package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.repository.RoundRepository;
import ch.uzh.ifi.seal.soprafs17.service.game.RoundService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Test class for the RoundResource REST resource.
 *
 * @see RoundService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class RoundServiceTest {

    @Autowired
    private RoundService roundService;

    @Autowired
    private RoundRepository roundRepository;

    @Test
    public void createRound() {
        // TODO: test roundService.createRound()
    }

    @Test
    public void initializeRound() {
        // TODO: test roundService.initializeRound()
    }

    @Test
    public void listRounds() {
        // TODO: test roundService.listRounds()
    }

    @Test
    public void getRoundByNr() {
        // TODO: test roundService.getRoundByNr()
    }


}
