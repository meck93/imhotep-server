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

import java.util.List;


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

        // Repository needs at least two cards to work properly
        MarketCard testMarketCard1 = marketCardService.createMarketCard(1L,GameConstants.BLUE, MarketCardType.CHISEL);
        MarketCard testMarketCard2 = marketCardService.createMarketCard(1L,GameConstants.BLUE, MarketCardType.CHISEL);

        Assert.assertEquals(testMarketCard1, marketCardService.getMarketCard(1L));
    }

    @Test
    public void getMarketCardDeck() {
        Game testGame = gameService.createGame("testName", "testName");
        testGame.setId(1L);

        this.marketCardService.createMarketCardSet(1L);

        List<MarketCard> testCards = (List<MarketCard>) this.marketCardRepository.findAll();

        for (MarketCard testCard : testCards){
            // TestCard cannot have an existing position yet
            Assert.assertNotNull(testCard.getPositionOnMarketPlace());
            Assert.assertEquals(testCard.getPositionOnMarketPlace(), 0);
            // TestCard cannot be chosen yet
            Assert.assertFalse(testCard.isAlreadyChosen());
        }

        // Retrieve a deck of four MarketCards
        List<MarketCard> fourCards = this.marketCardService.getMarketCardDeck(1L);

        // The size of the deck must be 4
        Assert.assertEquals(fourCards.size(), 4);

        //
        for (MarketCard marketCard : fourCards){
            // The MarketCard must be market as chosen
            Assert.assertTrue(marketCard.isAlreadyChosen());
            // The MarketCard cannot have position 0
            Assert.assertNotEquals(marketCard.getPositionOnMarketPlace(), 0);
        }
    }

    @Test
    public void createMarketCardSet() {
        Game testGame = gameService.createGame("testName", "testName");
        testGame.setId(1L);

        Assert.assertNull(marketCardRepository.findOne(1L));

        this.marketCardService.createMarketCardSet(1L);

        List<MarketCard> allCards = this.marketCardRepository.findAllMarketCards(1L);

        int violet = 0;
        int green = 0;
        int blue = 0;
        int red = 0;

        for (MarketCard marketCard : allCards){
            if (marketCard.getColor().equals(GameConstants.BLUE)){
                blue++;
            }
            else if (marketCard.getColor().equals(GameConstants.GREEN)){
                green++;
            }
            else if (marketCard.getColor().equals(GameConstants.VIOLET)){
                violet++;
            }
            else {
                red++;
            }
        }

        Assert.assertEquals(violet, 10);
        Assert.assertEquals(green, 8);
        Assert.assertEquals(blue, 10);
        Assert.assertEquals(red, 6);
    }
}