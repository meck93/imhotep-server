package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 05.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
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
public class GameTest {

    @Test
    public void setId() {
        Game testGame = new Game();
        testGame.setId(1L);
        Assert.assertEquals(testGame.getId(), Long.valueOf(1L));
    }

    @Test //same for setName()
    public void setName() {
        Game testGame = new Game();
        testGame.setName("testGame");
        Assert.assertEquals(testGame.getName(), "testGame");
    }

    @Test //same for getOwner()
    public void setOwner() {
        Game testGame = new Game();
        testGame.setOwner("tester");
        Assert.assertEquals(testGame.getOwner(), "tester");
    }

    @Test
    public void setStatus() {
        Game testGame = new Game();
        testGame.setStatus(GameStatus.PENDING);
        Assert.assertEquals(testGame.getStatus(),GameStatus.PENDING);
    }

    @Test
    public void setCurrentPlayer() {
        Game testGame = new Game();
        testGame.setCurrentPlayer(1);
        Assert.assertEquals(testGame.getCurrentPlayer(), Integer.valueOf(1));
    }

    @Test
    public void setRoundCounter() {
        Game testGame = new Game();
        testGame.setRoundCounter(1);
        Assert.assertEquals(testGame.getRoundCounter(), 1);
    }

    @Test
    public void setObelisk() {
        Game testGame = new Game();
        BuildingSite testObelisk = new BuildingSite();
        testGame.setObelisk(testObelisk);
        Assert.assertEquals(testGame.getObelisk(), testObelisk);
    }

    @Test
    public void setBurialChamber() {
        Game testGame = new Game();
        BuildingSite testBurialChamber = new BuildingSite();
        testGame.setObelisk(testBurialChamber);
        Assert.assertEquals(testGame.getObelisk(), testBurialChamber);
    }

    @Test
    public void setPyramid() {
        Game testGame = new Game();
        BuildingSite testPyramid = new BuildingSite();
        testGame.setObelisk(testPyramid);
        Assert.assertEquals(testGame.getObelisk(), testPyramid);
    }

    @Test
    public void setTemple() {
        Game testGame = new Game();
        BuildingSite testTemple = new BuildingSite();
        testGame.setObelisk(testTemple);
        Assert.assertEquals(testGame.getObelisk(), testTemple);
    }

    @Test //same for getOwner()
    public void setNumberOfPlayers() {
        Game testGame = new Game();
        testGame.setNumberOfPlayers(4);
        Assert.assertEquals(testGame.getNumberOfPlayers(), 4);
    }

    @Test
    public void setMarketPace() {
        Game testGame = new Game();
        MarketPlace testMarketPlace = new MarketPlace();
        testGame.setMarketPlace(testMarketPlace);
        Assert.assertEquals(testGame.getMarketPlace(), testMarketPlace);
    }

    @Test
    public void setStoneQuarry() {
        Game testGame = new Game();
        StoneQuarry testStoneQuarry = new StoneQuarry();
        testGame.setStoneQuarry(testStoneQuarry);
        Assert.assertEquals(testGame.getStoneQuarry(), testStoneQuarry);
    }

    @Test
    public void setRounds() {
        Game testGame = new Game();
        List<Round> testRounds = new ArrayList<>();
        testGame.setRounds(testRounds);
        Assert.assertEquals(testGame.getRounds(), testRounds);
    }

    @Test
    public void setPlayers() {
        Game testGame = new Game();
        List<Player> testPlayers = new ArrayList<>();
        testGame.setPlayers(testPlayers);
        Assert.assertEquals(testGame.getPlayers(), testPlayers);
    }
}
