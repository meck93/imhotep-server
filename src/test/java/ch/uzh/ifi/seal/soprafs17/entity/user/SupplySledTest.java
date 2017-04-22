package ch.uzh.ifi.seal.soprafs17.entity.user;

/**
 * Created by Cristian on 06.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class SupplySledTest {

    @Test
    public void setId() {
        SupplySled testSupplySled = new SupplySled();
        testSupplySled.setId(1L);
        Assert.assertNotNull(testSupplySled);
        Assert.assertEquals(testSupplySled.getId(), Long.valueOf(1L));
    }

    @Test
    public void setStones() {
        SupplySled testSupplySled = new SupplySled();
        List<Stone> testStones = new ArrayList<>();
        testSupplySled.setStones(testStones);
        Assert.assertNotNull(testSupplySled);
        Assert.assertEquals(testSupplySled.getStones(),testStones);
    }

    @Test
    public void setPlayerId() {
        SupplySled testSupplySled = new SupplySled();
        Player testPlayer = new Player();
        testSupplySled.setPlayerId(1L);
        Assert.assertNotNull(testPlayer);
        Assert.assertNotNull(testSupplySled);
        Assert.assertEquals(testSupplySled.getPlayerId(),Long.valueOf(1L));
    }
}
