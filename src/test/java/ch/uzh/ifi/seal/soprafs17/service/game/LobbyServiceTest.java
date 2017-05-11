package ch.uzh.ifi.seal.soprafs17.service.game;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.BadRequestHttpException;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.repository.StoneQuarryRepository;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
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
 * Test class for the LobbyResource REST resource.
 *
 * @see LobbyService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class LobbyServiceTest {

    @Autowired
    public LobbyService lobbyService;

    @Autowired
    public GameService gameService;

    @Autowired
    public GameRepository gameRepository;

    @Autowired
    public PlayerService playerService;

    @Autowired
    public UserService userService;

    @Autowired
    public StoneQuarryRepository stoneQuarryRepository;


    @Test
    public void listGames() {
        Assert.assertNull(gameRepository.findById(1L));
        List<Game> testResult = new ArrayList<>();
        Game testGame = gameService.createGame("testName","testName");
        testGame.setId(1L);
        testResult.add((testGame));
        Assert.assertNotNull(gameRepository.findById(1L));
        Assert.assertNotNull(lobbyService.listGames());
        Assert.assertEquals(testResult,lobbyService.listGames());
    }

    @Test
    public void createGame() {
        Game testGame = new Game();
        User user1 = userService.createUser("testOwner");
        testGame.setId(1L);
        testGame.setOwner("testOwner");
        testGame.setName("testGame");
        Assert.assertNotNull(lobbyService.createGame(testGame,user1.getId()));

        //catching the exception thrown if the User could not be converted into a Player
        try{
            Game testGame2 = new Game();
            testGame2.setId(2L);
            testGame2.setOwner("testOwner");
            testGame2.setName("testGame2");
            lobbyService.createGame(testGame2,user1.getId());
            gameRepository.findById(testGame2.getId());
        } catch (BadRequestHttpException e) {}

    }

    @Test
    public void joinGame() {
        User user1 = userService.createUser("testOwner");
        User user2 = userService.createUser("testOwner2");
        Game game = gameService.createGame("testGame","testOwner");
        lobbyService.joinGame(1L,2L);
        lobbyService.joinGame(1L,1L);
        Assert.assertEquals(game.getNumberOfPlayers(),2);
    }

    @Test
    public void removePlayer() {
        Game game = gameService.createGame("testGame", "testOwner1");

        User user1 = userService.createUser("test1");
        User user2 = userService.createUser("test2");

        Player player1 = playerService.createPlayer(game.getId(), user1.getId());
        playerService.initializePlayer(game.getId(), player1);
        gameService.addPlayer(game.getId(), player1);
        gameService.updateNrOfPlayers(game.getId());

        Player player2 = playerService.createPlayer(game.getId(), user2.getId());
        playerService.initializePlayer(game.getId(), player2);
        gameService.addPlayer(game.getId(), player2);
        gameService.updateNrOfPlayers(game.getId());

        lobbyService.removePlayer(1L,user1.getId());
        try{
            Assert.assertNull(playerService.findPlayerById(2L));
        } catch (NotFoundException e) {}
    }


    @Test
    public void startGame() {
        User user1 = userService.createUser("test1");
        User user2 = userService.createUser("test2");

        Game game = gameService.createGame("testGame", "test1");

        Player player1 = playerService.createPlayer(game.getId(), user1.getId());
        playerService.initializePlayer(game.getId(), player1);
        gameService.addPlayer(game.getId(), player1);
        gameService.updateNrOfPlayers(game.getId());

        Player player2 = playerService.createPlayer(game.getId(), user2.getId());
        playerService.initializePlayer(game.getId(), player2);
        gameService.addPlayer(game.getId(), player2);
        gameService.updateNrOfPlayers(game.getId());

        //Check if the gameStatus is set to PENDING
        Assert.assertEquals(game.getStatus(), GameStatus.PENDING);

        //Throw exception for trying to start a game that is already running
        try{
            lobbyService.startGame(game.getId(),player1.getId());
            Assert.assertEquals(game.getStatus(), GameStatus.RUNNING);
            lobbyService.startGame(game.getId(),player1.getId());
        }catch (BadRequestHttpException e) {}
    }

    @Test
    public void startGameWithException() {
        User user1 = userService.createUser("test1");
        User user2 = userService.createUser("test2");

        Game game = gameService.createGame("testGame", "test1");

        Player player1 = playerService.createPlayer(game.getId(), user1.getId());
        playerService.initializePlayer(game.getId(), player1);
        gameService.addPlayer(game.getId(), player1);
        gameService.updateNrOfPlayers(game.getId());

        //Throw exception for trying to start a game when the minimum amount of player isn't met
        try{
            lobbyService.startGame(game.getId(),player1.getId());
        }catch (BadRequestHttpException e) {}

        Player player2 = playerService.createPlayer(game.getId(), user2.getId());
        playerService.initializePlayer(game.getId(), player2);
        gameService.addPlayer(game.getId(), player2);
        gameService.updateNrOfPlayers(game.getId());

        //Throw exception for trying to start a game when you are not the owner of the game
        try{
            lobbyService.startGame(game.getId(),player2.getId());
        }catch (BadRequestHttpException e) {}
    }

    @Test
    public void deleteGame() {
        User user1 = userService.createUser("test1");
        User user2 = userService.createUser("test2");

        Game game = gameService.createGame("testGame", "test1");

        Player player1 = playerService.createPlayer(game.getId(), user1.getId());
        playerService.initializePlayer(game.getId(), player1);
        gameService.addPlayer(game.getId(), player1);
        gameService.updateNrOfPlayers(game.getId());

        Player player2 = playerService.createPlayer(game.getId(), user2.getId());
        playerService.initializePlayer(game.getId(), player2);
        gameService.addPlayer(game.getId(), player2);
        gameService.updateNrOfPlayers(game.getId());

        lobbyService.startGame(game.getId(),player1.getId());
        lobbyService.deleteGame(game.getId());

        Assert.assertEquals(gameRepository.findPlayersByGameId(1L).size(),0);
    }

    @Test
    public void fastForward() {
        /*
            FIRST TEST:
        */

        //Creating prerequisites
        User user1 = userService.createUser("test1");
        User user2 = userService.createUser("test2");

        Game game = gameService.createGame("testGame", "test1");

        Player player1 = playerService.createPlayer(game.getId(), user1.getId());
        playerService.initializePlayer(game.getId(), player1);
        gameService.addPlayer(game.getId(), player1);
        gameService.updateNrOfPlayers(game.getId());

        Player player2 = playerService.createPlayer(game.getId(), user2.getId());
        playerService.initializePlayer(game.getId(), player2);
        gameService.addPlayer(game.getId(), player2);
        gameService.updateNrOfPlayers(game.getId());

        //Cannot fastForward a game that is already started
        try{
            lobbyService.startGame(game.getId(),player1.getId());
            lobbyService.fastForward(game.getId(),player1.getId());
        } catch (BadRequestHttpException e) {}

        /*
            SECOND TEST:
        */

        //Creating prerequisites
        User user3 = userService.createUser("test3");
        User user4 = userService.createUser("test4");

        Game game2 = gameService.createGame("testGame2", "test3");

        Player player3 = playerService.createPlayer(game2.getId(), user3.getId());
        playerService.initializePlayer(game2.getId(), player3);
        gameService.addPlayer(game2.getId(), player3);
        gameService.updateNrOfPlayers(game2.getId());

        // Can't start if there aren't at least 2 players in a game
        try{
            lobbyService.fastForward(game2.getId(),player3.getId());
        } catch (BadRequestHttpException e) {}

        // Can't start the start if player is not the owner
        try{
            lobbyService.fastForward(game2.getId(),player2.getId());
        } catch (BadRequestHttpException e){}

        Player player4 = playerService.createPlayer(game2.getId(), user4.getId());
        playerService.initializePlayer(game2.getId(), player4);
        gameService.addPlayer(game2.getId(), player4);
        gameService.updateNrOfPlayers(game2.getId());

        game2.setStoneQuarry(this.stoneQuarryRepository.findOne(1L));

        //FastForwarding to round 6
        lobbyService.fastForward(game2.getId(),player3.getId());

        //Check if roundCounter is correctly set to 6
        Assert.assertEquals(game2.getRoundCounter(),6);
        Assert.assertNotNull(game2.getRoundByRoundCounter());

        //Check if handCards are set correctly
        Assert.assertEquals(game2.getPlayerByPlayerNr(1).getHandCards().size(),3);
        Assert.assertEquals(game2.getPlayerByPlayerNr(2).getHandCards().size(),5);

        //Check if the marketCards are correctly set in the first players' handCards
        Assert.assertEquals(game2.getPlayerByPlayerNr(1).getHandCards().get(0).getColor(), GameConstants.BLUE);
        Assert.assertEquals(game2.getPlayerByPlayerNr(1).getHandCards().get(1).getColor(),GameConstants.BLUE);
        Assert.assertEquals(game2.getPlayerByPlayerNr(1).getHandCards().get(2).getColor(), GameConstants.GREEN);

        //Check if the marketCards are correctly set in the second players' handCards
        Assert.assertEquals(game2.getPlayerByPlayerNr(2).getHandCards().get(0).getColor(), GameConstants.VIOLET);
        Assert.assertEquals(game2.getPlayerByPlayerNr(2).getHandCards().get(1).getColor(), GameConstants.VIOLET);
        Assert.assertEquals(game2.getPlayerByPlayerNr(2).getHandCards().get(2).getColor(), GameConstants.VIOLET);
        Assert.assertEquals(game2.getPlayerByPlayerNr(2).getHandCards().get(3).getColor(), GameConstants.VIOLET);

        //Check if the score is set correctly
        Assert.assertEquals(game2.getPlayerByPlayerNr(1).getPoints()[0],6);
        Assert.assertEquals(game2.getPlayerByPlayerNr(2).getPoints()[0],11);

        //Check if the score is set correctly
        Assert.assertEquals(game2.getPlayerByPlayerNr(1).getPoints()[1],1);
        Assert.assertEquals(game2.getPlayerByPlayerNr(2).getPoints()[1],3);

        //Check if the stones have been setup correctly on each building site
        Assert.assertEquals(game2.getBuildingSite(GameConstants.OBELISK).getStones().size(),7);
        Assert.assertEquals(game2.getBuildingSite(GameConstants.PYRAMID).getStones().size(),7);
        Assert.assertEquals(game2.getBuildingSite(GameConstants.TEMPLE).getStones().size(),4);
        Assert.assertEquals(game2.getBuildingSite(GameConstants.BURIAL_CHAMBER).getStones().size(),12);

        //Check if stoneQuarry holds the correct amount of stones for each player in the game
        Assert.assertEquals(game2.getStoneQuarry().getBlackStones().size(),18);
        Assert.assertEquals(game2.getStoneQuarry().getWhiteStones().size(),8);

        //Check if the game has been saved correctly
        Assert.assertNotNull(gameRepository.findById(game2.getId()));
    }
}
