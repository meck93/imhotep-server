package ch.uzh.ifi.seal.soprafs17.service.card;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.repository.MarketCardRepository;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
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
        MarketCard testMarketCard = marketCardService.createMarketCard(1L, GameConstants.BLUE, MarketCardType.CHISEL);
        Assert.assertEquals(testMarketCard, marketCardRepository.findOne(1L));
    }

    @Test
    public void getMarketCard() {
        Game testGame = gameService.createGame("testName", "testName");
        testGame.setId(1L);
        MarketCard testMarketCard1 = marketCardService.createMarketCard(1L,GameConstants.BLUE, MarketCardType.CHISEL);
        MarketCard testMarketCard2 = marketCardService.createMarketCard(1L,GameConstants.BLUE, MarketCardType.CHISEL);
        Assert.assertEquals(testMarketCard1, marketCardService.getMarketCard(1L));
    }

    @Test
    public void getMarketCardDeck() {
        Game testGame = gameService.createGame("testName", "testName");
        testGame.setId(1L);
        Assert.assertNull(marketCardRepository.findOne(1L));
        MarketCard testMarketCard1 = marketCardService.createMarketCard(1L,GameConstants.BLUE, MarketCardType.CHISEL);
        MarketCard testMarketCard2 = marketCardService.createMarketCard(1L,GameConstants.BLUE, MarketCardType.CHISEL);
        Assert.assertNotNull(testMarketCard1);
        Assert.assertNotNull(testMarketCard2);

    }

    @Test
    public void getMarketCardSet() {
        Game testGame = gameService.createGame("testName", "testName");
        testGame.setId(1L);
        MarketCard testMarketCard = marketCardService.createMarketCard(1L, GameConstants.RED, MarketCardType.PAVED_PATH);
        marketCardService.createMarketCardSet(1L);
        Assert.assertEquals(marketCardRepository.findOne(1L),testMarketCard);
    }
}