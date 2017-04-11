package ch.uzh.ifi.seal.soprafs17.entity.game;

/**
 * Created by Cristian on 06.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.game.StoneQuarry;
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
public class StoneQuarryTest {

    @Test
    public void setId() {
        StoneQuarry testStoneQuarry = new StoneQuarry();
        testStoneQuarry.setId(1L);
        Assert.assertEquals(testStoneQuarry.getId(), Long.valueOf(1L));
    }

    @Test
    public void setBlackStones() {
        StoneQuarry testStoneQuarry = new StoneQuarry();
        List<Stone> testBlackStones = new ArrayList<>();
        testStoneQuarry.setBlackStones(testBlackStones);
        Assert.assertEquals(testStoneQuarry.getBlackStones(),testBlackStones);
    }

    @Test
    public void setWhiteStones() {
        StoneQuarry testStoneQuarry = new StoneQuarry();
        List<Stone> testWhiteStones = new ArrayList<>();
        testStoneQuarry.setBlackStones(testWhiteStones);
        Assert.assertEquals(testStoneQuarry.getBlackStones(),testWhiteStones);
    }

    @Test
    public void setBrownStones() {
        StoneQuarry testStoneQuarry = new StoneQuarry();
        List<Stone> testBrownStones = new ArrayList<>();
        testStoneQuarry.setBlackStones(testBrownStones);
        Assert.assertEquals(testStoneQuarry.getBlackStones(),testBrownStones);
    }

    @Test
    public void setGrayStones() {
        StoneQuarry testStoneQuarry = new StoneQuarry();
        List<Stone> testGrayStones = new ArrayList<>();
        testStoneQuarry.setBlackStones(testGrayStones);
        Assert.assertEquals(testStoneQuarry.getBlackStones(),testGrayStones);
    }
}
