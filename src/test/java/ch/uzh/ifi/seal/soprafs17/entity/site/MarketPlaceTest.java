package ch.uzh.ifi.seal.soprafs17.entity.site;

/**
 * Created by Cristian on 06.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
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
public class MarketPlaceTest {

    @Test
    public void setMarketCards() {
        MarketPlace testMarketPlace = new MarketPlace();
        List<MarketCard> testMarketCards = new ArrayList<>();
        testMarketPlace.setMarketCards(testMarketCards);
        Assert.assertNotNull(testMarketPlace);
        Assert.assertEquals(testMarketPlace.getMarketCards(),testMarketCards);
    }

    @Test
    public void setDockedShipId() {
        MarketPlace testMarketPlace = new MarketPlace();
        testMarketPlace.setDockedShipId(1L);
        Assert.assertNotNull(testMarketPlace);
        Assert.assertEquals(testMarketPlace.getDockedShipId(),Long.valueOf(1L));
    }

    @Test
    public void getMarketCardById() {
        MarketPlace testMarketPlace = new MarketPlace();
        MarketCard testCard = new MarketCard();
        testCard.setId(1L);
        List<MarketCard> testCards = new ArrayList<>();
        testCards.add(testCard);
        testMarketPlace.setMarketCards(testCards);
        Assert.assertEquals(testMarketPlace.getMarketCardById(1L),testCard);
        try{
            Assert.assertEquals(testMarketPlace.getMarketCardById(6L),null);
        } catch (NotFoundException e) {}
    }
}