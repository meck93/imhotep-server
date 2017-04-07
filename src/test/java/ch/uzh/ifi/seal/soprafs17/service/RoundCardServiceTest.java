package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.repository.RoundCardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Test class for the PlayerResource REST resource.
 *
 * @see PlayerService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class RoundCardServiceTest {

    @Autowired
    private RoundCardService roundCardService;

    @Autowired
    private RoundCardRepository roundCardRepository;

    @Test
    public void createRoundCards() {
        // TODO: test roundCardService.createRoundCards()
    }

    @Test
    public void createTwoHeadRoundCardSet() {
        // TODO: test roundCardService.createTwoHeadRoundCardSet()
    }

    @Test
    public void createFourThreeRoundCardSet() {
        // TODO: test roundCardService.createThreeHeadRoundCardSet()
    }

    @Test
    public void createFourHeadRoundCardSet() {
        // TODO: test roundCardService.createFourHeadRoundCardSet()
    }

    @Test
    public void createRoundCard() {
        // TODO: test roundCardService.createRoundCard()
    }

    @Test
    public void getRoundCard() {
        // TODO: test roundCardService.getRoundCard()
    }
}
