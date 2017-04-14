package ch.uzh.ifi.seal.soprafs17.entity.site;


import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
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
public class BuildingSiteTest {

    @Test
    public void setStones() {
        BuildingSite testBuildingSite = new BuildingSite();
        List<Stone> testStones = new ArrayList<>();
        testBuildingSite.setStones(testStones);
        Assert.assertNotNull(testBuildingSite);
        Assert.assertEquals(testBuildingSite.getStones(),testStones);
    }

    // TODO adapt to changes

    /*@Test
    public void setDockedShip() {
        BuildingSite testBuildingSite = new BuildingSite();
        boolean dockedShip = true;
        testBuildingSite.setDockedShip(dockedShip);
        Assert.assertNotNull(testBuildingSite);
        Assert.assertEquals(testBuildingSite.isDockedShip(),true);
    }*/
}
