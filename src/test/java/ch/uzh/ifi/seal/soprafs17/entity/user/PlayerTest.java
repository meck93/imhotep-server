package ch.uzh.ifi.seal.soprafs17.entity.user;

/**
 * Created by Cristian on 06.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
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
public class PlayerTest {

    @Test
    public void setId() {
        Player testPlayer = new Player();
        testPlayer.setId(1L);
        Assert.assertNotNull(testPlayer);
        Assert.assertEquals(testPlayer.getId(), Long.valueOf(1L));
    }

    @Test
    public void setGameId() {
        Game testGame = new Game();
        testGame.setId(1L);
        Assert.assertNotNull(testGame);
        Assert.assertEquals(testGame.getId(),Long.valueOf(1L));
    }

    @Test
    public void setPoints() {
        Player testPlayer = new Player();
        int[] points = {10, 0, 0, 0, 0, 0};
        testPlayer.setPoints(points);
        Assert.assertNotNull(testPlayer);
        Assert.assertEquals(testPlayer.getPoints()[0], 10);
    }

    @Test
    public void setColor() {
        Player testPlayer = new Player();
        testPlayer.setColor("red");
        Assert.assertNotNull(testPlayer);
        Assert.assertEquals(testPlayer.getColor(),"red");
    }

    @Test
    public void setPlayerNumber() {
        Player testPlayer = new Player();
        testPlayer.setPlayerNumber(4);
        Assert.assertNotNull(testPlayer);
        Assert.assertEquals(testPlayer.getPlayerNumber(),4);
    }

    @Test
    public void setSupplySled() {
        Player testPlayer = new Player();
        SupplySled testSupplySled = new SupplySled();
        testPlayer.setSupplySled(testSupplySled);
        Assert.assertNotNull(testPlayer);
        Assert.assertNotNull(testSupplySled);
        Assert.assertEquals(testPlayer.getSupplySled(),testSupplySled);
    }

    @Test
    public void setUser() {
        Player testPlayer = new Player();
        User testUser = new User();
        testPlayer.setUser(testUser);
        Assert.assertNotNull(testPlayer);
        Assert.assertNotNull(testUser);
        Assert.assertEquals(testPlayer.getUser(),testUser);
    }

    @Test
    public void setGame() {
        Player testPlayer = new Player();
        Game testGame = new Game();
        testPlayer.setGame(testGame);
        Assert.assertNotNull(testPlayer);
        Assert.assertNotNull(testGame);
        Assert.assertEquals(testPlayer.getGame(),testGame);
    }

    @Test
    public void setUsername() {
        Player testPlayer = new Player();
        testPlayer.setUsername("testUsername");
        Assert.assertNotNull(testPlayer);
        Assert.assertEquals(testPlayer.getUsername(),"testUsername");
    }

    @Test
    public void setHandCards() {
        Player testPlayer = new Player();
        List<MarketCard> testMarketCards = new ArrayList<>();
        testPlayer.setHandCards(testMarketCards);
        Assert.assertNotNull(testPlayer);
        Assert.assertEquals(testPlayer.getHandCards(),testMarketCards);
    }

    @Test
    public void getMarketCardById() {
        Player testPlayer = new Player();
        List<Player> players = new ArrayList<>();
        players.add(testPlayer);
        MarketCard testCard1 = new MarketCard();
        testCard1.setId(1L);
        MarketCard testCard2 = new MarketCard();
        testCard2.setId(2L);
        List<MarketCard> testCards = new ArrayList<>();
        testCards.add(testCard1);
        testCards.add(testCard2);
        testPlayer.setHandCards(testCards);
        Assert.assertEquals(testPlayer.getMarketCardById(1L),testCard1);
    }
}
