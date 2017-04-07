package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 06.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.RoundCardType;
import ch.uzh.ifi.seal.soprafs17.constant.ShipSize;
import ch.uzh.ifi.seal.soprafs17.entity.card.RoundCard;
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
public class RoundCardTest {

    @Test
    public void setId() {
        RoundCard testRoundCard = new RoundCard();
        testRoundCard.setId(1L);
        Assert.assertEquals(testRoundCard.getId(), Long.valueOf(1L));
    }

    @Test
    public void setAlreadyChosen() {
        RoundCard testRoundCard = new RoundCard();
        testRoundCard.setAlreadyChosen(true);
        Assert.assertEquals(testRoundCard.isAlreadyChosen(),true);
    }

    @Test
    public void setHeads() {
        RoundCard testRoundCard = new RoundCard();
        testRoundCard.setHeads(RoundCardType.THREE_HEADS);
        Assert.assertEquals(testRoundCard.getHeads(),RoundCardType.THREE_HEADS);
    }

    @Test
    public void setShipSizes() {
        RoundCard testRoundCard = new RoundCard();
        List<ShipSize> testShipSizes = new ArrayList<>();
        testRoundCard.setShipSizes(testShipSizes);
        Assert.assertEquals(testRoundCard.getShipSizes(),testShipSizes);
    }

    @Test
    public void setGameId() {
        RoundCard testRoundCard = new RoundCard();
        testRoundCard.setGameId(1L);
        Assert.assertEquals(testRoundCard.getGameId(),Long.valueOf(1L));
    }
}

