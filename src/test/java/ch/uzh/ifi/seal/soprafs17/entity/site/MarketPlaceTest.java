package ch.uzh.ifi.seal.soprafs17.entity.site;

/**
 * Created by Cristian on 06.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
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
}