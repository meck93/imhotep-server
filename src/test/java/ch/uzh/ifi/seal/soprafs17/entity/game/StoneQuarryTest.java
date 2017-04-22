package ch.uzh.ifi.seal.soprafs17.entity.game;

/**
 * Created by Cristian on 06.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
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
public class StoneQuarryTest {

    @Test
    public void setId() {
        StoneQuarry testStoneQuarry = new StoneQuarry();
        testStoneQuarry.setId(1L);
        Assert.assertNotNull(testStoneQuarry);
        Assert.assertEquals(testStoneQuarry.getId(), Long.valueOf(1L));
    }

    @Test
    public void setBlackStones() {
        StoneQuarry testStoneQuarry = new StoneQuarry();
        List<Stone> testBlackStones = new ArrayList<>();
        testStoneQuarry.setBlackStones(testBlackStones);
        Assert.assertNotNull(testStoneQuarry);
        Assert.assertEquals(testStoneQuarry.getBlackStones(),testBlackStones);
    }

    @Test
    public void getBlackStones() {
        StoneQuarry testStoneQuarry = new StoneQuarry();
        List<Stone> testBlackStones = new ArrayList<>();
        testStoneQuarry.setBlackStones(testBlackStones);
        Assert.assertNotNull(testStoneQuarry);
        Assert.assertEquals(testStoneQuarry.getBlackStones(),testBlackStones);
    }

    @Test
    public void setWhiteStones() {
        StoneQuarry testStoneQuarry = new StoneQuarry();
        List<Stone> testWhiteStones = new ArrayList<>();
        testStoneQuarry.setWhiteStones((testWhiteStones));
        Assert.assertNotNull(testStoneQuarry);
        Assert.assertEquals(testStoneQuarry.getWhiteStones(),testWhiteStones);
    }

    @Test
    public void setBrownStones() {
        StoneQuarry testStoneQuarry = new StoneQuarry();
        List<Stone> testBrownStones = new ArrayList<>();
        testStoneQuarry.setBrownStones(testBrownStones);
        Assert.assertNotNull(testStoneQuarry);
        Assert.assertEquals(testStoneQuarry.getBrownStones(),testBrownStones);
    }

    @Test
    public void setGrayStones() {
        StoneQuarry testStoneQuarry = new StoneQuarry();
        List<Stone> testGrayStones = new ArrayList<>();
        testStoneQuarry.setGrayStones(testGrayStones);
        Assert.assertNotNull(testStoneQuarry);
        Assert.assertEquals(testStoneQuarry.getGrayStones(),testGrayStones);
    }

    @Test
    public void getStonesByPlayerNr() {
        Player testPlayer1 = new Player();
        Player testPlayer2 = new Player();
        Player testPlayer3 = new Player();
        Player testPlayer4 = new Player();
        Stone testStone = new Stone();
        List<Stone> testStones = new ArrayList<>();
        Game testGame = new Game();
        testPlayer1.setPlayerNumber(1);
        testPlayer2.setPlayerNumber(2);
        testPlayer3.setPlayerNumber(3);
        testPlayer4.setPlayerNumber(4);
        testStones.add(testStone);
        StoneQuarry testStoneQuarry = new StoneQuarry();
        testGame.setStoneQuarry(testStoneQuarry);
        testStoneQuarry.setBlackStones(testStones);
        testStoneQuarry.setWhiteStones(testStones);
        testStoneQuarry.setBrownStones(testStones);
        testStoneQuarry.setGrayStones(testStones);
        Assert.assertEquals(testStoneQuarry.getStonesByPlayerNr(1),testStones);
        Assert.assertEquals(testStoneQuarry.getStonesByPlayerNr(2),testStones);
        Assert.assertEquals(testStoneQuarry.getStonesByPlayerNr(3),testStones);
        Assert.assertEquals(testStoneQuarry.getStonesByPlayerNr(4),testStones);
    }

    @Test
    public void setGame() {
        Game testGame = new Game();
        StoneQuarry testStoneQuarry = new StoneQuarry();
        testStoneQuarry.setGame(testGame);
        Assert.assertEquals(testStoneQuarry.getGame(),testGame);
    }
}
