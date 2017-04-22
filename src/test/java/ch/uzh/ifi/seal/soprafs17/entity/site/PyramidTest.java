package ch.uzh.ifi.seal.soprafs17.entity.site;

/**
 * Created by Cristian on 20.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
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
public class PyramidTest {

    @Test
    public void Pyramid() {
        Pyramid testPyramid = new Pyramid(1L);
        Assert.assertNotNull(testPyramid);
        Assert.assertEquals(testPyramid.getGameId(),Long.valueOf(1L));
        Assert.assertEquals(testPyramid.getSiteType(), GameConstants.PYRAMID);
    }

    @Test
    public void getScores() {
        Pyramid testPyramid = new Pyramid(1L);
        int[] scores = {2, 1, 3, 2, 4, 3, 2, 1, 3, 2, 3, 1, 3, 4};
        Assert.assertArrayEquals(testPyramid.getScores(),scores);
    }
}
