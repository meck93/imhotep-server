package ch.uzh.ifi.seal.soprafs17.entity.game;

/**
 * Created by Cristian on 06.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class StoneTest {

    @Test
    public void setId() {
        Stone testStone = new Stone();
        testStone.setId(1L);
        Assert.assertNotNull(testStone);
        Assert.assertEquals(testStone.getId(), Long.valueOf(1L));
    }

    @Test
    public void setColor() {
        Stone testStone = new Stone();
        testStone.setColor("black");
        Assert.assertNotNull(testStone);
        Assert.assertEquals(testStone.getColor(),"black");
    }

    @Test
    public void setPlaceOnShip() {
        Stone testStone = new Stone();
        testStone.setPlaceOnShip(1);
        Assert.assertEquals(testStone.getPlaceOnShip(), 1);
    }
}
