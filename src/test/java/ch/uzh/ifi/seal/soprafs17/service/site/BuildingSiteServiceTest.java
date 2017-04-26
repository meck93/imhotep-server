package ch.uzh.ifi.seal.soprafs17.service.site;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import ch.uzh.ifi.seal.soprafs17.repository.ASiteRepository;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;


/**
 * Test class for the BuildingSiteResource REST resource.
 *
 * @see BuildingSiteService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class BuildingSiteServiceTest {

    @Autowired
    public ASiteRepository aSiteRepository;

    @Autowired
    public BuildingSiteService buildingSiteService;

    @Autowired
    public GameService gameService;

    @Test
    public void createBuildingSite() {
        BuildingSite testBuildingSite = buildingSiteService.createBuildingSite(GameConstants.OBELISK,1L);
        assertNotNull(aSiteRepository.findBuildingSite(1L, GameConstants.OBELISK));
        Assert.assertEquals(aSiteRepository.findBuildingSite(1L, GameConstants.OBELISK), testBuildingSite);
        try {
            Game testGame = gameService.createGame("name","Owner");
            gameService.startGame(1L);
            buildingSiteService.createBuildingSite(GameConstants.OBELISK,1L);
        } catch (NotFoundException e) {}
    }

    @Test
    public void getBuildingSite() {
        try {
            buildingSiteService.getBuildingSite(1L,GameConstants.OBELISK);
        } catch (NotFoundException e) {}
        BuildingSite testBuildingSite = buildingSiteService.createBuildingSite(GameConstants.OBELISK,1L);
        BuildingSite testBuildingSite2 = buildingSiteService.getBuildingSite(1L, GameConstants.OBELISK);
        Assert.assertEquals(testBuildingSite,testBuildingSite2);
    }
}

