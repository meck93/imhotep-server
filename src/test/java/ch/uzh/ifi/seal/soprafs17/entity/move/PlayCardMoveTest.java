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

import java.util.ArrayList;


@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class PlayCardMoveTest {

    @Test
    public void PlayCardMove() {
        PlayCardMove testMove = new PlayCardMove();
        testMove.setMoveType(GameConstants.PLAY_CARD);
        Assert.assertEquals(testMove.getMoveType(), GameConstants.PLAY_CARD);
    }

    @Test
    public void setCardId() {
        PlayCardMove testPlayCardMove = new PlayCardMove();
        testPlayCardMove.setCardId(1L);
        Assert.assertNotNull(testPlayCardMove);
        Assert.assertEquals(testPlayCardMove.getCardId(),Long.valueOf(1L));
    }

    @Test
    public void setShipId() {
        PlayCardMove testPlayCardMove = new PlayCardMove();
        testPlayCardMove.setShipId(1L);
        Assert.assertNotNull(testPlayCardMove);
        Assert.assertEquals(testPlayCardMove.getShipId(),Long.valueOf(1L));
    }

    @Test
    public void setPlaceOnShip() {
        PlayCardMove testPlayCardMove = new PlayCardMove();
        testPlayCardMove.setPlaceOnShip(1);
        Assert.assertNotNull(testPlayCardMove);
        Assert.assertEquals(testPlayCardMove.getPlaceOnShip(),1);
    }

    @Test
    public void setTargetSiteId() {
        PlayCardMove testPlayCardMove = new PlayCardMove();
        testPlayCardMove.setTargetSiteId(1L);
        Assert.assertNotNull(testPlayCardMove);
        Assert.assertEquals(testPlayCardMove.getTargetSiteId(),1L);
    }

    @Test
    public void setShipId2() {
        PlayCardMove testPlayCardMove = new PlayCardMove();
        testPlayCardMove.setShipId2(1L);
        Assert.assertNotNull(testPlayCardMove);
        Assert.assertEquals(testPlayCardMove.getShipId2(),1L);
    }

    @Test
    public void setPlaceOnShip2() {
        PlayCardMove testPlayCardMove = new PlayCardMove();
        testPlayCardMove.setPlaceOnShip2(1);
        Assert.assertNotNull(testPlayCardMove);
        Assert.assertEquals(testPlayCardMove.getPlaceOnShip2(),1);
    }

    @Test
    public void setUnloadingOrder() {
        PlayCardMove testPlayCardMove = new PlayCardMove();
        ArrayList<Long> testOrder = new ArrayList<>();
        Long testUnloadOrder1 = 1L;
        Long testUnloadOrder2 = 2L;
        testOrder.add(testUnloadOrder1);
        testOrder.add(testUnloadOrder2);
        testPlayCardMove.setUnloadingOrder(testOrder);
        Assert.assertEquals(testPlayCardMove.getUnloadingOrder(),testOrder);
    }
}
