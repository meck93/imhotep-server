package ch.uzh.ifi.seal.soprafs17.entity.move;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

//TODO the target site is now a object of the type ASite

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class SailShipMoveTest {

    @Test
    public void setShipId() {
        SailShipMove testSailShipMove = new SailShipMove();
        testSailShipMove.setShipId(1L);
        Assert.assertNotNull(testSailShipMove);
        Assert.assertEquals(testSailShipMove.getShipId(),Long.valueOf(1L));
    }

    @Test
    public void setTargetSiteId() {
        SailShipMove testSailShipMove = new SailShipMove();
        testSailShipMove.setTargetSiteId(1L);
        Assert.assertNotNull(testSailShipMove);
        Assert.assertEquals(testSailShipMove.getTargetSiteId(),1L);
    }

    @Test
    public void SailShipMove() {
        SailShipMove testMove = new SailShipMove();
        testMove.setMoveType(GameConstants.SAIL_SHIP);
        Assert.assertEquals(testMove.getMoveType(),GameConstants.SAIL_SHIP);
    }

}
