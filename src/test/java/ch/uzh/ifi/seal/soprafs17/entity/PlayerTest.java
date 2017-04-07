package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 06.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.SupplySled;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
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
public class PlayerTest {

    @Test
    public void setId() {
        Player testPlayer = new Player();
        testPlayer.setId(1L);
        Assert.assertEquals(testPlayer.getId(), Long.valueOf(1L));
    }

    @Test
    public void setPoints() {
        Player testPlayer = new Player();
        testPlayer.setPoints(10);
        Assert.assertEquals(testPlayer.getPoints(), 10);
    }

    @Test
    public void setColor() {
        Player testPlayer = new Player();
        testPlayer.setColor("red");
        Assert.assertEquals(testPlayer.getColor(),"red");
    }

    @Test
    public void setPlayerNumber() {
        Player testPlayer = new Player();
        testPlayer.setPlayerNumber(4);
        Assert.assertEquals(testPlayer.getPlayerNumber(),4);
    }

    @Test
    public void setSupplySled() {
        Player testPlayer = new Player();
        SupplySled testSupplySled = new SupplySled();
        testPlayer.setSupplySled(testSupplySled);
        Assert.assertEquals(testPlayer.getSupplySled(),testSupplySled);
    }

    @Test
    public void setUser() {
        Player testPlayer = new Player();
        User testUser = new User();
        testPlayer.setUser(testUser);
        Assert.assertEquals(testPlayer.getUser(),testUser);
    }

    @Test
    public void setMoves() {
        Player testPlayer = new Player();
        List<AMove> testMoves = new ArrayList<>();
        testPlayer.setMoves(testMoves);
        Assert.assertEquals(testPlayer.getMoves(),testMoves);
    }

    @Test
    public void setGame() {
        Player testPlayer = new Player();
        Game testGame = new Game();
        testPlayer.setGame(testGame);
        Assert.assertEquals(testPlayer.getGame(),testGame);
    }

    @Test
    public void setUsername() {
        Player testPlayer = new Player();
        testPlayer.setUsername("testUsername");
        Assert.assertEquals(testPlayer.getUsername(),"testUsername");
    }

    @Test
    public void setHandCards() {
        Player testPlayer = new Player();
        List<MarketCard> testMarketCards = new ArrayList<>();
        testPlayer.setHandCards(testMarketCards);
        Assert.assertEquals(testPlayer.getHandCards(),testMarketCards);
    }
}