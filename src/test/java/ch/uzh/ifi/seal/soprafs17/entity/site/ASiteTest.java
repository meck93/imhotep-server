package ch.uzh.ifi.seal.soprafs17.entity.site;

/**
 * Created by Cristian on 13.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

//TODO The ASite entity holds now the boolean isDocked

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class ASiteTest {

    @Test
    public void setId() {
        BuildingSite testSite = new BuildingSite(1L);
        testSite.setId(2L);
        Assert.assertNotNull(testSite);
        Assert.assertEquals(testSite.getId(), Long.valueOf(2L));
    }

    @Test
    public void getGameId() {
        BuildingSite testBuildingSite = new BuildingSite();
        testBuildingSite.setGameId(1L);
        Assert.assertNotNull(testBuildingSite);
        Assert.assertEquals(testBuildingSite.getGameId(), Long.valueOf(1L));
    }

    @Test
    public void setSiteType() {
        BuildingSite testBuildingSite = new BuildingSite();
        testBuildingSite.setSiteType("TEST_NAME");
        Assert.assertNotNull(testBuildingSite);
        Assert.assertEquals(testBuildingSite.getSiteType(),"TEST_NAME");
    }
}

