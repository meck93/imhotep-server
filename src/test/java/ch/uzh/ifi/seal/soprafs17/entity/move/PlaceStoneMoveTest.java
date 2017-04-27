package ch.uzh.ifi.seal.soprafs17.entity.move;

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
public class PlaceStoneMoveTest {

    @Test
    public void PlaceStoneMove() {
        PlaceStoneMove testMove = new PlaceStoneMove("TEST");
        Assert.assertNotNull(testMove);
        Assert.assertEquals(testMove.getMoveType(),"TEST");
    }

    @Test
    public void setShipId() {
        PlaceStoneMove testPlaceStoneMove = new PlaceStoneMove();
        testPlaceStoneMove.setShipId(1L);
        Assert.assertNotNull(testPlaceStoneMove);
        Assert.assertEquals(testPlaceStoneMove.getShipId(),Long.valueOf(1L));
    }

    @Test
    public void setPlaceOnShip() {
        PlaceStoneMove testPlaceStoneMove = new PlaceStoneMove();
        testPlaceStoneMove.setPlaceOnShip(1);
        Assert.assertNotNull(testPlaceStoneMove);
        Assert.assertEquals(testPlaceStoneMove.getPlaceOnShip(),1);
    }
}
