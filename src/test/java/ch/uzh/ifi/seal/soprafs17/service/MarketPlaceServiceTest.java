package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.site.MarketPlace;
import ch.uzh.ifi.seal.soprafs17.repository.ASiteRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Test class for the MarketPlaceResource REST resource.
 *
 * @see MarketPlaceService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class MarketPlaceServiceTest {

    @Autowired
    private MarketPlaceService marketPlaceService;

    @Autowired
    private GameService gameService;

    @Autowired
    private ASiteRepository aSiteRepository;

    @Test
    public void createMarketPlace() {
        Game testGame = gameService.createGame("testName","testName");
        MarketPlace testMarketPlace = marketPlaceService.createMarketPlace(1L);
        testGame.setId(1L);
        testGame.setMarketPlace(testMarketPlace);
        Assert.assertNotNull(testMarketPlace);
        Assert.assertEquals(testGame.getMarketPlace(),aSiteRepository.findMarketPlace(1L));
    }

    @Test
    public void getMarketPlace() {
        Game testGame = gameService.createGame("testName","testName");
        MarketPlace testMarketPlace = marketPlaceService.createMarketPlace(1L);
        testGame.setId(1L);
        Assert.assertEquals(marketPlaceService.getMarketPlace(1L),aSiteRepository.findMarketPlace(1L));
    }
}
