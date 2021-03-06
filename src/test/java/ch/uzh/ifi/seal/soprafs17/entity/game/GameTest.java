package ch.uzh.ifi.seal.soprafs17.entity.game;

/**
 * Created by Cristian on 05.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.entity.site.MarketPlace;
import ch.uzh.ifi.seal.soprafs17.entity.site.Obelisk;
import ch.uzh.ifi.seal.soprafs17.entity.site.Pyramid;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.site.BuildingSiteService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GameTest {

    @Autowired
    public BuildingSiteService buildingSiteService;

    @Autowired
    public GameService gameService;

    @Test
    public void setId() {
        Game testGame = new Game();
        testGame.setId(1L);
        Assert.assertNotNull(testGame);
        Assert.assertEquals(testGame.getId(), Long.valueOf(1L));
    }

    @Test
    public void setName() {
        Game testGame = new Game();
        testGame.setName("testGame");
        Assert.assertNotNull(testGame);
        Assert.assertEquals(testGame.getName(), "testGame");
    }

    @Test
    public void setOwner() {
        Game testGame = new Game();
        testGame.setOwner("tester");
        Assert.assertNotNull(testGame);
        Assert.assertEquals(testGame.getOwner(), "tester");
    }

    @Test
    public void setStatus() {
        Game testGame = new Game();
        testGame.setStatus(GameStatus.PENDING);
        Assert.assertNotNull(testGame);
        Assert.assertEquals(testGame.getStatus(),GameStatus.PENDING);
    }

    @Test
    public void setCurrentPlayer() {
        Game testGame = new Game();
        testGame.setCurrentPlayer(1);
        Assert.assertNotNull(testGame);
        Assert.assertEquals(testGame.getCurrentPlayer(), Integer.valueOf(1));
    }

    @Test
    public void setCurrentSubRoundPlayer(){
        Game testGame = new Game();
        testGame.setCurrentSubRoundPlayer(1);
        Assert.assertNotNull(testGame);
        Assert.assertEquals(testGame.getCurrentSubRoundPlayer(), 1);
    }

    @Test
    public void setRoundCounter() {
        Game testGame = new Game();
        testGame.setRoundCounter(1);
        Assert.assertNotNull(testGame);
        Assert.assertEquals(testGame.getRoundCounter(), 1);
    }

    @Test
    public void setNumberOfPlayers() {
        Game testGame = new Game();
        testGame.setNumberOfPlayers(4);
        Assert.assertNotNull(testGame);
        Assert.assertEquals(testGame.getNumberOfPlayers(), 4);
    }

    @Test
    public void setMarketPace() {
        Game testGame = new Game();
        MarketPlace testMarketPlace = new MarketPlace();
        testGame.setMarketPlace(testMarketPlace);
        Assert.assertNotNull(testGame);
        Assert.assertNotNull(testMarketPlace);
        Assert.assertEquals(testGame.getMarketPlace(), testMarketPlace);
    }

    @Test
    public void setStoneQuarry() {
        Game testGame = new Game();
        StoneQuarry testStoneQuarry = new StoneQuarry();
        testGame.setStoneQuarry(testStoneQuarry);
        Assert.assertNotNull(testGame);
        Assert.assertNotNull(testStoneQuarry);
        Assert.assertEquals(testGame.getStoneQuarry(), testStoneQuarry);
    }

    @Test
    public void setRounds() {
        Game testGame = new Game();
        List<Round> testRounds = new ArrayList<>();
        testGame.setRounds(testRounds);
        Assert.assertNotNull(testGame);
        Assert.assertEquals(testGame.getRounds(), testRounds);
    }

    @Test
    public void setPlayers() {
        Game testGame = new Game();
        List<Player> testPlayers = new ArrayList<>();
        testGame.setPlayers(testPlayers);
        Assert.assertNotNull(testGame);
        Assert.assertEquals(testGame.getPlayers(), testPlayers);
    }

    @Test
    public void getRoundByRoundCounter() {
        Round testRound = new Round();
        Game testGame = new Game();
        List<Round> testRounds = new ArrayList<>();
        testRounds.add(testRound);
        testRound.setRoundNumber(1);
        testGame.setRoundCounter(1);
        testGame.setRounds(testRounds);
        Assert.assertEquals(testRounds.get(0), testGame.getRoundByRoundCounter());
        try {
            testGame.setRoundCounter(2);
            Assert.assertEquals(testGame.getRoundByRoundCounter(),testRounds.get(0));
        } catch(NotFoundException e) {}
    }

    @Test
    public void getPlayerByPlayerNr() {
        Player testPlayer = new Player();
        Game testGame = new Game();
        List<Player> testPlayers = new ArrayList<>();
        testPlayers.add(testPlayer);
        testGame.setPlayers(testPlayers);
        testPlayer.setPlayerNumber(1);
        Assert.assertNotNull(testPlayer.getPlayerNumber());
        Assert.assertEquals(testGame.getPlayerByPlayerNr(1),testPlayers.get(0));
        try {
            Assert.assertEquals(testGame.getPlayerByPlayerNr(2),testPlayers.get(0));
        } catch(NotFoundException e) {}
    }

    @Test
    public void setBuildingSites() {
        Game testGame = new Game();
        List<BuildingSite> testBuildingSites = new ArrayList<>();
        Pyramid testPyramid = new Pyramid();
        testGame.setBuildingSites(testBuildingSites);
        testBuildingSites.add(testPyramid);
        Assert.assertEquals(testGame.getBuildingSites(),testBuildingSites);
    }

    @Test
    public void BuildingSite() {
        Game testGame = new Game();
        Pyramid testPyramid = new Pyramid();
        List<BuildingSite> testBuildingSites = new ArrayList<>();
        testBuildingSites.add(testPyramid);
        testGame.setBuildingSites(testBuildingSites);
        Assert.assertEquals(testPyramid,testGame.getBuildingSites().get(0));
    }

    @Test
    public void getBuildingSite() {
        Game testGame = new Game();
        BuildingSite obelisk = new Obelisk();
        obelisk.setSiteType(GameConstants.OBELISK);
        List<BuildingSite> testSites = new ArrayList<>();
        testSites.add(obelisk);
        testGame.setBuildingSites(testSites);
        Assert.assertEquals(testGame.getBuildingSite(GameConstants.OBELISK),obelisk );
        try{
            Assert.assertEquals(testGame.getBuildingSite(GameConstants.PYRAMID),obelisk );
        } catch (NotFoundException e) {}
    }

    @Test
    public void getPlayerByColor() {
        Player testPlayer = new Player();
        testPlayer.setColor(GameConstants.BLACK);
        Game testGame = new Game();
        List<Player> testPlayers = new ArrayList<>();
        testPlayers.add(testPlayer);
        testGame.setPlayers(testPlayers);
        testPlayer.setPlayerNumber(1);
        Assert.assertNotNull(testPlayer);
        Assert.assertNotNull(testGame);
        Assert.assertEquals(testGame.getPlayerByColor(GameConstants.BLACK),testPlayers.get(0));
    }

    @Test(expected = NotFoundException.class)
    public void getPlayerByColorException() {
        Player testPlayer = new Player();
        testPlayer.setColor(GameConstants.BLACK);
        Game testGame = new Game();
        List<Player> testPlayers = new ArrayList<>();
        testPlayers.add(testPlayer);
        testGame.setPlayers(testPlayers);
        testPlayer.setPlayerNumber(1);
        Assert.assertNotNull(testPlayer);
        Assert.assertNotNull(testGame);
        Assert.assertEquals(testGame.getPlayerByColor(GameConstants.GRAY),testPlayers.get(0));
    }

    @Test
    public void getSiteById() {
        /*
        Game testGame = gameService.createGame("testName", "testOwner");
        testGame.setId(1L);
        gameService.startGame(1L);
        gameService.initializeGame(1L);
        Assert.assertNotNull(testGame.getSiteById(2L));
         */
    }
}

