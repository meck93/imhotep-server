package ch.uzh.ifi.seal.soprafs17.service.game;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.repository.StoneRepository;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Test class for the StoneResource REST resource.
 *
 * @see StoneService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class StoneServiceTest {

    @Autowired
    public StoneService stoneService;

    @Autowired
    public StoneRepository stoneRepository;

    @Autowired
    public GameService gameService;

    @Test
    public void createStone() {
        Game testGame = gameService.createGame("testGame", "testOwner");
        testGame.setId(1L);
        Stone testStone = stoneService.createStone(GameConstants.BLACK);
        testStone.setId(1L);
        Assert.assertEquals(testStone, stoneRepository.findOne(1L));
    }

    @Test
    public void createStones() {
        Game testGame = gameService.createGame("testGame", "testOwner");
        testGame.setId(1L);
        Stone testStone1 = stoneService.createStone(GameConstants.BLACK);
        testStone1.setId(1L);
        Stone testStone2 = stoneService.createStone(GameConstants.BLACK);
        testStone2.setId(2L);
        List<Stone> testStones = new ArrayList<>();
        testStones.add(testStone1);
        testStones.add(testStone2);

        Assert.assertEquals(testStones,stoneRepository.findAll());
    }
}
