package ch.uzh.ifi.seal.soprafs17.entity.site;

/**
 * Created by Cristian on 20.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.exceptions.InternalServerException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class ObeliskTest {

    @Test
    public void Obelisk() {
        Obelisk testObelisk = new Obelisk(1L);
        Assert.assertNotNull(testObelisk);
        Assert.assertEquals(testObelisk.getGameId(),Long.valueOf(1L));
        Assert.assertEquals(testObelisk.getSiteType(), GameConstants.OBELISK);
    }

    @Test
    public void getScoresTwoPlayer() {
        Obelisk testObelisk = new Obelisk();
        int[] testScoreTwoPlayer = {10,1};
        Assert.assertArrayEquals(testObelisk.getScoresTwoPlayer(),testScoreTwoPlayer);
    }

    @Test
    public void getScoresThreePlayer() {
        Obelisk testObelisk = new Obelisk();
        int[] testScoreThreePlayer = {12, 6, 1};
        Assert.assertArrayEquals(testObelisk.getScoresThreePlayer(),testScoreThreePlayer);
    }

    @Test
    public void getScoresFourPlayer() {
        Obelisk testObelisk = new Obelisk();
        int[] testScoreFourPlayer = {15, 10, 5, 1};
        Assert.assertArrayEquals(testObelisk.getScoresFourPlayer(),testScoreFourPlayer);
    }

    @Test
    public void getScores() {
        Obelisk testObelisk = new Obelisk();
        int[] testScoreTwoPlayer = {10,1};
        int[] testScoreThreePlayer = {12, 6, 1};
        int[] testScoreFourPlayer = {15, 10, 5, 1};
        Assert.assertArrayEquals(testObelisk.getScores(2),testScoreTwoPlayer);
        Assert.assertArrayEquals(testObelisk.getScores(3),testScoreThreePlayer);
        Assert.assertArrayEquals(testObelisk.getScores(4),testScoreFourPlayer);
        try{
            Assert.assertEquals(testObelisk.getScores(6),null);
        } catch (InternalServerException e) {}
    }
}
