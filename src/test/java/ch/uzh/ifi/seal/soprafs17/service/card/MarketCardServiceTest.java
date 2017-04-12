package ch.uzh.ifi.seal.soprafs17.service.card;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.repository.MarketCardRepository;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.card.MarketCardService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Test class for the MarketCardResource REST resource.
 *
 * @see MarketCardService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class MarketCardServiceTest {

    @Autowired
    private MarketCardService marketCardService;

    @Autowired
    private GameService gameService;

    @Autowired
    private MarketCardRepository marketCardRepository;

    @Test
    public void createMarketCard() {
        MarketCard testMarketCard = marketCardService.createMarketCard(1L,"test", MarketCardType.CHISEL);
        Assert.assertEquals(testMarketCard, marketCardRepository.findOne(1L));
    }

    @Test
    public void getMarketCard() {
        // TODO: test marketCardService.getMarketCard()
         /*
        Game testGame = gameService.createGame("testName", "testName");
        testGame.setId(1L);
        MarketCard testMarketCard = new MarketCard();
        MarketPlace testMarketPlace = new MarketPlace();
        testGame.setMarketPlace(testMarketPlace);
        List<MarketCard> testMarketCards = new ArrayList<>();
        testMarketCards.add(testMarketCard);
        testMarketPlace.setMarketCards(testMarketCards);
        Assert.assertEquals(testMarketCards, marketCardService.getMarketCard(1L));
    */
    }

    @Test
    public void getMarketCardDeck() {
        // TODO: test marketCardService.getMarketCardDeck()
    }

    @Test
    public void getMarketCardSet() {
        // TODO: test marketCardService.getMarketCardSet()
    }
}