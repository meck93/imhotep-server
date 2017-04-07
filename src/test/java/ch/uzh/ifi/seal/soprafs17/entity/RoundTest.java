package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 06.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.card.RoundCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
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
public class RoundTest {

    @Test
    public void setId() {
        Round testRound = new Round();
        testRound.setId(1L);
        Assert.assertEquals(testRound.getId(), Long.valueOf(1L));
    }

    @Test
    public void setCard() {
        Round testRound = new Round();
        RoundCard testRoundCard = new RoundCard();
        testRound.setCard(testRoundCard);
        Assert.assertEquals(testRound.getCard(),testRoundCard);
    }

    @Test
    public void setMoves() {
        Round testRound = new Round();
        List<AMove> testMoves = new ArrayList<>();
        testRound.setMoves(testMoves);
        Assert.assertEquals(testRound.getMoves(),testMoves);
    }

    @Test
    public void setGame() {
        Round testRound = new Round();
        Game testGame = new Game();
        testRound.setGame(testGame);
        Assert.assertEquals(testRound.getGame(),testGame);
    }

    @Test
    public void setShips() {
        Round testRound = new Round();
        List<Ship> testShips = new ArrayList<>();
        testRound.setShips(testShips);
        Assert.assertEquals(testRound.getShips(),testShips);
    }

    @Test
    public void setRoundNumber() {
        Round testRound = new Round();
        testRound.setRoundNumber(1);
        Assert.assertEquals(testRound.getRoundNumber(),1);
    }
}
