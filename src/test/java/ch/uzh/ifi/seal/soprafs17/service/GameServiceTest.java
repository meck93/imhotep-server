package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.BadRequestHttpException;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.service.user.PlayerService;
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
 * Test class for the GameResource REST resource.
 *
 * @see GameService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class GameServiceTest {

    @Autowired
    public GameService gameService;

    @Autowired
    public GameRepository gameRepository;

    @Autowired
    public UserService userService;

    @Autowired
    public PlayerService playerService;

    @Test
    public void createGame() {
        try{
            Assert.assertNull(gameRepository.findById(1L));
        } catch (BadRequestHttpException e) {}
        Game testGame = gameService.createGame("testName","testName");
        testGame.setId((1L));
        Assert.assertNotNull(gameRepository.findById(1L));
        Assert.assertEquals(gameRepository.findById(1L),testGame);
        try{
            Game testGame2 = gameService.createGame("testName","testName");
        }catch (BadRequestHttpException e) {}
    }

    @Test
    public void deleteGame() {
        Assert.assertNull(gameRepository.findById(1L));
        Game testGame = gameService.createGame("testName","testName");
        testGame.setId(1L);
        Assert.assertNotNull(gameRepository.findById(1L));
        gameService.deleteGame(1L);
        Assert.assertNull(gameRepository.findById(1L));
    }

    @Test
    public void addPlayer() {
        Game testGame = gameService.createGame("testName","testName");
        Assert.assertNotNull(gameRepository.findById(1L));
        Assert.assertEquals(gameRepository.findById(1L),testGame);
        try{
            User user1 = userService.createUser("testName","testName");
            Player player1 = playerService.createPlayer(1L,1L);
            playerService.initializePlayer(testGame.getId(), player1);
            gameService.addPlayer(1L,player1);
        } catch (BadRequestHttpException e) {}
    }

    @Test
    public void updateNrOfPlayers() {
        Game testGame = gameService.createGame("testName","testName");
        testGame.setId((1L));
        gameService.updateNrOfPlayers(1L);
        gameRepository.findNrOfPlayers(1L);
        Assert.assertNotNull(gameRepository.findNrOfPlayers(1L));
    }

    @Test
    public void listGames() {
        Assert.assertNull(gameRepository.findById(1L));
        List<Game> testResult = new ArrayList<>();
        Game testGame = gameService.createGame("testName","testName");
        testGame.setId(1L);
        testResult.add((testGame));
        Assert.assertNotNull(gameRepository.findById(1L));
        Assert.assertNotNull(gameService.listGames());
        Assert.assertEquals(testResult,gameService.listGames());
    }

    @Test
    public void findById() {
        Assert.assertNull(gameRepository.findById(1L));
        Game testGame = gameService.createGame("testName","testName");
        testGame.setId(1L);
        Assert.assertNotNull(gameRepository.findById(1L));
        Assert.assertEquals(testGame,gameRepository.findById(1L));
    }

    @Test
    public void findNrOfPlayers() {
        Assert.assertNull(gameRepository.findById(1L));
        Game testGame = gameService.createGame("testName","testName");
        testGame.setId(1L);
        Assert.assertEquals(testGame.getNumberOfPlayers(),gameService.findNrOfPlayers(1L));
    }

    @Test
    public void findPlayersByGameId() {
        Assert.assertNull(gameRepository.findById(1L));
        Game testGame = gameService.createGame("testName","testName");
        testGame.setId(1L);
        Player testPlayer = new Player();
        List<Player> testPlayers = new ArrayList<>();
        testPlayers.add(testPlayer);
        testGame.setPlayers(testPlayers);
        Assert.assertNotNull(String.valueOf(testGame.getPlayers()),gameRepository.findPlayersByGameId(1L));
    }

    @Test
    public void startGame() {
        // test initializeGame() and initializeRound() first
    }

    @Test
    public void initializeGame() {
         // TODO: test createRoundCards(), createMarketCardSet(), createMarketPlace(), createStoneQuarry(), fillQuarry(), setStoneQuarry() and createBuildingSite() first!
    }

    @Test
    public void initializeRound() {
        // TODO: test createRound(), initializeRound(), getMarketCardDeck() and setMarketCards() first!
    }

    @Test
    public void stopGame() {
        Assert.assertNull(gameRepository.findById(1L));
        Game testGame = gameService.createGame("testName","testName");
        testGame.setId(1L);
        Assert.assertNotNull(gameRepository.findById(1L));
        gameService.stopGame(1L);
        Assert.assertEquals(gameRepository.findById(1L).getStatus(), GameStatus.FINISHED);
    }

    @Test
    public void sizeOfQuarry() {
        //TODO: test sizeOfQuarry()
    }

}
