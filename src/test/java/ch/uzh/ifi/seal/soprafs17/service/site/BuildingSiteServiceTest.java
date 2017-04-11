package ch.uzh.ifi.seal.soprafs17.service.site;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.BuildingSiteType;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.repository.ASiteRepository;
import ch.uzh.ifi.seal.soprafs17.service.site.BuildingSiteService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;


/**
 * Test class for the BuildingSiteResource REST resource.
 *
 * @see BuildingSiteService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class BuildingSiteServiceTest {

    @Autowired
    private ASiteRepository aSiteRepository;

    @Autowired
    private BuildingSiteService buildingSiteService;

    @Test
    public void createBuildingSite() {
        BuildingSite testBuildingSite = buildingSiteService.createBuildingSite(BuildingSiteType.OBELISK,1L);
        assertNotNull(aSiteRepository.findBuildingSite(1L, BuildingSiteType.OBELISK));
        Assert.assertEquals(aSiteRepository.findBuildingSite(1L, BuildingSiteType.OBELISK), testBuildingSite);

    }

    @Test
    public void getBuildingSite() {
        BuildingSite testBuildingSite = buildingSiteService.createBuildingSite(BuildingSiteType.OBELISK,1L);
        BuildingSite testBuildingSite2 = buildingSiteService.getBuildingSite(1L,BuildingSiteType.OBELISK);
        Assert.assertEquals(testBuildingSite,testBuildingSite2);
    }
}

