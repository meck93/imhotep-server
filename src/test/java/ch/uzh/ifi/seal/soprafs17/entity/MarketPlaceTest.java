package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 06.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.entity.site.MarketPlace;
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
public class MarketPlaceTest {

    @Test
    public void getGameId() {
        MarketPlace testMarketPlace = new MarketPlace();
        testMarketPlace.setGameId(1L);
        Assert.assertEquals(testMarketPlace.getGameId(), Long.valueOf(1L));

    }

    @Test
    public void setMarketCards() {
        MarketPlace testMarketPlace = new MarketPlace();
        List<MarketCard> testMarketCards = new ArrayList<>();
        testMarketPlace.setMarketCards(testMarketCards);
        Assert.assertEquals(testMarketPlace.getMarketCards(),testMarketCards);
    }

    @Test
    public void setSiteType() {
        MarketPlace testMarketPlace = new MarketPlace();
        testMarketPlace.setSiteType("TEST_NAME");
        Assert.assertEquals(testMarketPlace.getSiteType(),"TEST_NAME");
    }
}