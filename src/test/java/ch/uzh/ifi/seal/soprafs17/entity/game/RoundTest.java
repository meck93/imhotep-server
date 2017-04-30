package ch.uzh.ifi.seal.soprafs17.entity.game;

/**
 * Created by Cristian on 06.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.card.RoundCard;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
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
public class RoundTest {

    @Test
    public void setId() {
        Round testRound = new Round();
        testRound.setId(1L);
        Assert.assertNotNull(testRound);
        Assert.assertEquals(testRound.getId(), Long.valueOf(1L));
    }

    @Test
    public void setCard() {
        Round testRound = new Round();
        RoundCard testRoundCard = new RoundCard();
        testRound.setCard(testRoundCard);
        Assert.assertNotNull(testRound);
        Assert.assertNotNull(testRoundCard);
        Assert.assertEquals(testRound.getCard(),testRoundCard);
    }

    @Test
    public void setGame() {
        Round testRound = new Round();
        Game testGame = new Game();
        testRound.setGame(testGame);
        Assert.assertNotNull(testGame);
        Assert.assertNotNull(testRound);
        Assert.assertEquals(testRound.getGame(),testGame);
    }

    @Test
    public void setShips() {
        Round testRound = new Round();
        List<Ship> testShips = new ArrayList<>();
        testRound.setShips(testShips);
        Assert.assertNotNull(testRound);
        Assert.assertEquals(testRound.getShips(),testShips);
    }

    @Test
    public void setRoundNumber() {
        Round testRound = new Round();
        testRound.setRoundNumber(1);
        Assert.assertNotNull(testRound);
        Assert.assertEquals(testRound.getRoundNumber(),1);
    }

    @Test
    public void getShipById() {
        Round testRound = new Round();
        Ship testShip1 = new Ship();
        Ship testShip2 = new Ship();
        testShip1.setId(1L);
        testShip2.setId(2L);
        List<Ship> testShips = new ArrayList<>();
        testShips.add(testShip1);
        testShips.add(testShip2);
        testRound.setShips(testShips);
        Assert.assertEquals(testRound.getShips().get(0),testRound.getShipById(1L));
    }

    @Test(expected = NotFoundException.class)
    public void dontFindShip(){
        Round testRound = new Round();
        Ship testShip1 = new Ship();
        testShip1.setId(1L);
        List<Ship> testShips = new ArrayList<>();
        testShips.add(testShip1);
        testRound.setShips(testShips);

        Assert.assertEquals(testRound.getShips().get(0),testRound.getShipById(2L));
    }
}
