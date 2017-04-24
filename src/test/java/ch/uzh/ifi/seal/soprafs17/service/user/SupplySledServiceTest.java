package ch.uzh.ifi.seal.soprafs17.service.user;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.user.SupplySled;
import ch.uzh.ifi.seal.soprafs17.repository.SupplySledRepository;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import org.junit.Assert;
import org.junit.Test;
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
public class SupplySledServiceTest {

    @Autowired
    public SupplySledService supplySledService;

    @Autowired
    public SupplySledRepository supplySledRepository;

    @Autowired
    public GameService gameService;

    @Test
    public void createSupplySled() {
        SupplySled testSupplySled = supplySledService.createSupplySled(1L);
        Assert.assertNotNull(testSupplySled);
        Assert.assertEquals(testSupplySled, supplySledRepository.findOne(1L));
    }

    @Test
    public void getSupplySledByGameId() {
        Game testGame = new Game();
        SupplySled testSupplySled = supplySledService.createSupplySled(1L);
        Assert.assertNotNull(testSupplySled);
        Assert.assertEquals(supplySledService.getSupplySledByGameId(testGame,1L), testSupplySled);
    }

    @Test
    public void fillSupplySled() {
       //TODO: Test fillSupplySled()
    }

    @Test
    public void fillSupplySleds() {
        //TODO: Test fillSupplySleds()
    }
}

