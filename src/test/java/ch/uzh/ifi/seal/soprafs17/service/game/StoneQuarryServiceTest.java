package ch.uzh.ifi.seal.soprafs17.service.game;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.game.StoneQuarry;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.exceptions.InternalServerException;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import ch.uzh.ifi.seal.soprafs17.repository.StoneQuarryRepository;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.user.UserService;
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
 * Test class for the StoneQuarryResource REST resource.
 *
 * @see StoneQuarryService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class StoneQuarryServiceTest {

    @Autowired
    public StoneQuarryService stoneQuarryService;

    @Autowired
    public StoneQuarryRepository stoneQuarryRepository;

    @Autowired
    public GameService gameService;

    @Autowired
    public UserService userService;

    @Test
    public void createStoneQuarry() {
        Game testGame = gameService.createGame("testGame", "testOwner1");
        testGame.setNumberOfPlayers(2);
        Assert.assertNotNull(stoneQuarryService.createStoneQuarry(testGame));
        Assert.assertNotNull(stoneQuarryRepository.findOne(1L).getBlackStones());
        Assert.assertNotNull(stoneQuarryRepository.findOne(1L).getWhiteStones());
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        List<Player> testPlayers = new ArrayList<>();
        testPlayers.add(player1);
        testPlayers.add(player2);
        testPlayers.add(player3);
        testGame.setPlayers(testPlayers);
        Assert.assertNotNull(stoneQuarryService.createStoneQuarry(testGame));
        Assert.assertNotNull(stoneQuarryRepository.findOne(2L).getBrownStones());
        Player player4 = new Player();
        testPlayers.add(player4);
        testGame.setPlayers(testPlayers);
        Assert.assertNotNull(stoneQuarryService.createStoneQuarry(testGame));
        Assert.assertNotNull(stoneQuarryRepository.findOne(3L).getGrayStones());
    }

    @Test
    public void fillQuarry() {
        Game testGame = gameService.createGame("testGame", "testOwner1");
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        Player player4 = new Player();
        List<Player> testPlayers = new ArrayList<>();
        testPlayers.add(player1);
        testPlayers.add(player2);
        testPlayers.add(player3);
        testPlayers.add(player4);
        testGame.setPlayers(testPlayers);
        StoneQuarry stoneQuarry1 = stoneQuarryService.createStoneQuarry(testGame);
        stoneQuarryService.fillQuarry(stoneQuarry1);
        Assert.assertEquals(stoneQuarryRepository.findOne(1L).getBlackStones().size(), GameConstants.START_STONES);
        Assert.assertEquals(stoneQuarryRepository.findOne(1L).getWhiteStones().size(), GameConstants.START_STONES);
        Assert.assertEquals(stoneQuarryRepository.findOne(1L).getBrownStones().size(), GameConstants.START_STONES);
        Assert.assertEquals(stoneQuarryRepository.findOne(1L).getGrayStones().size(), GameConstants.START_STONES);
    }

    @Test
    public void getStone() {
        Game testGame = gameService.createGame("testGame", "testOwner1");
        StoneQuarry testQuarry = stoneQuarryService.createStoneQuarry(testGame);
        try{
            stoneQuarryService.getStone(1L,1);
        } catch(InternalServerException e) {}
        try{
            List<Stone> testStones = new ArrayList<>();
            testQuarry.setBlackStones(testStones);
        } catch(InternalServerException e) {}
        stoneQuarryService.fillQuarry(testQuarry);
        Assert.assertNotNull(stoneQuarryService.getStone(1L,1));
    }

    @Test
    public void getStoneQuarryByGameId() {
        Game testGame = gameService.createGame("testGame", "testOwner1");
        try{
            stoneQuarryService.getStoneQuarryByGameId(1L);
        } catch(NotFoundException e) {}

        testGame.setNumberOfPlayers(2);
        StoneQuarry testQuarry = stoneQuarryService.createStoneQuarry(testGame);
        Assert.assertEquals(stoneQuarryService.getStoneQuarryByGameId(1L),testQuarry);
    }
}
