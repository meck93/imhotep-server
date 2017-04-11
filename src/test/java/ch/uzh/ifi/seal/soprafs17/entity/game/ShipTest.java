package ch.uzh.ifi.seal.soprafs17.entity.game;

/**
 * Created by Cristian on 06.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class ShipTest {

    @Test
    public void setId() {
        Ship testShip = new Ship();
        testShip.setId(1L);
        Assert.assertEquals(testShip.getId(), Long.valueOf(1L));
    }

    @Test
    public void setStones() {
        Ship testShip = new Ship();
        List<Stone> testStones = new ArrayList<>();
        testShip.setStones(testStones);
        Assert.assertEquals(testShip.getStones(),testStones);
    }

    @Test
    public void setGameId() {
        Ship testShip = new Ship();
        testShip.setGameId(1L);
        Assert.assertEquals(testShip.getGameId(),Long.valueOf(1L));
    }

    @Test
    public void getMIN_STONES() {
        Ship testShip = new Ship(1,2);
        Assert.assertEquals(testShip.getMIN_STONES(),1);
    }

    @Test
    public void getMAX_STONES() {
        Ship testShip = new Ship(1,2);
        Assert.assertEquals(testShip.getMAX_STONES(),2);
    }

    @Test
    public void setHasSailed() {
        Ship testShip = new Ship();
        testShip.setHasSailed(true);
        Assert.assertEquals(testShip.isHasSailed(),true);
    }

    @Test
    public void setTargetSite() {
        Ship testShip = new Ship();
        BuildingSite testBuildingSite = new BuildingSite();
        testShip.setTargetSite(testBuildingSite);
        Assert.assertEquals(testShip.getTargetSite(),testBuildingSite);
    }
}

