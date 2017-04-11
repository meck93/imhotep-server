package ch.uzh.ifi.seal.soprafs17.entity.card;

/**
 * Created by Cristian on 06.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class MarketCardTest {

    @Test
    public void setId() {
        MarketCard testMarketCard = new MarketCard();
        testMarketCard.setId(1L);
        Assert.assertEquals(testMarketCard.getId(), Long.valueOf(1L));
    }

    @Test
    public void getGameId() {
        MarketCard testMarketCard = new MarketCard();
        testMarketCard.setGameId(1L);
        Assert.assertEquals(testMarketCard.getGameId(), Long.valueOf(1L));
    }

    @Test
    public void setMarketCardType() {
        MarketCard testMarketCard = new MarketCard();
        testMarketCard.setMarketCardType(MarketCardType.SARCOPHAGUS);
        Assert.assertEquals(testMarketCard.getMarketCardType(),MarketCardType.SARCOPHAGUS);
    }

    @Test
    public void setAlreadyChosen() {
        MarketCard testMarketCard = new MarketCard();
        testMarketCard.setAlreadyChosen(true);
        Assert.assertEquals(testMarketCard.isAlreadyChosen(),true);
    }

    @Test
    public void setColor() {
        MarketCard testMarketCard = new MarketCard();
        testMarketCard.setColor("red");
        Assert.assertEquals(testMarketCard.getColor(),"red");
    }



}

