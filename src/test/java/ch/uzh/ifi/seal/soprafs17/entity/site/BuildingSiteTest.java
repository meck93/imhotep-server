package ch.uzh.ifi.seal.soprafs17.entity.site;


import ch.uzh.ifi.seal.soprafs17.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class BuildingSiteTest {

   /* @Test
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

    @Test
    public void setBuildingSiteType() {
        BuildingSite testBuildingSite = new BuildingSite();
        testBuildingSite.setBuildingSiteType(BuildingSiteType.OBELISK);
        Assert.assertNotNull(testBuildingSite);
        Assert.assertEquals(testBuildingSite.getBuildingSiteType(),BuildingSiteType.OBELISK);
    }

    @Test
    public void setStones() {
        BuildingSite testBuildingSite = new BuildingSite();
        List<Stone> testStones = new ArrayList<>();
        testBuildingSite.setStones(testStones);
        Assert.assertNotNull(testBuildingSite);
        Assert.assertEquals(testBuildingSite.getStones(),testStones);
    }

    @Test
    public void setDockedShip() {
        BuildingSite testBuildingSite = new BuildingSite();
        Ship testDockedShip = new Ship();
        testBuildingSite.setDockedShip(testDockedShip);
        Assert.assertNotNull(testBuildingSite);
        Assert.assertNotNull(testDockedShip);
        Assert.assertEquals(testBuildingSite.getDockedShip(),testDockedShip);
    }*/
}
