package ch.uzh.ifi.seal.soprafs17.service.game;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
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
        User user1 = userService.createUser("testName","testOwner");
        Game game = gameService.createGame("testGame","testOwner");
        Assert.assertNotNull(game);
        try{
            lobbyService.createGame(game,user1.getId());
            Assert.assertNotNull(game);
        } catch (BadRequestHttpException e) {}
    }

    @Test
    public void joinGame() {
        User user1 = userService.createUser("testName","testOwner");
        User user2 = userService.createUser("testName2","testOwner2");
        Game game = gameService.createGame("testGame","testOwner");
        lobbyService.joinGame(1L,2L);
        lobbyService.joinGame(1L,1L);
        Assert.assertEquals(game.getNumberOfPlayers(),2);
    }

    @Test
    public void removePlayer() {
        Game game = gameService.createGame("testGame", "testOwner1");

        User user1 = userService.createUser("testUser1", "testOwner1");
        User user2 = userService.createUser("testUser2", "testOwner2");

        Player player1 = playerService.createPlayer(game.getId(), user1.getId());
        playerService.initializePlayer(game.getId(), player1);
        gameService.addPlayer(game.getId(), player1);
        gameService.updateNrOfPlayers(game.getId());

        Player player2 = playerService.createPlayer(game.getId(), user2.getId());
        playerService.initializePlayer(game.getId(), player2);
        gameService.addPlayer(game.getId(), player2);
        gameService.updateNrOfPlayers(game.getId());

        lobbyService.removePlayer(1L,1L);
        try{
            Assert.assertNull(playerService.findPlayerById(2L));
        } catch (NotFoundException e) {}
    }


    @Test
    public void startGame() {
        User user1 = userService.createUser("testUser1", "test1");
        User user2 = userService.createUser("testUser2", "test2");

        Game game = gameService.createGame("testGame", "test1");

        Player player1 = playerService.createPlayer(game.getId(), user1.getId());
        playerService.initializePlayer(game.getId(), player1);
        gameService.addPlayer(game.getId(), player1);
        gameService.updateNrOfPlayers(game.getId());

        Player player2 = playerService.createPlayer(game.getId(), user2.getId());
        playerService.initializePlayer(game.getId(), player2);
        gameService.addPlayer(game.getId(), player2);
        gameService.updateNrOfPlayers(game.getId());

        Assert.assertEquals(game.getStatus(), GameStatus.PENDING);
        lobbyService.startGame(game.getId(),player1.getId());
        Assert.assertEquals(game.getStatus(), GameStatus.RUNNING);
    }

    @Test
    public void stopGame() {
        User user1 = userService.createUser("testUser1", "test1");
        User user2 = userService.createUser("testUser2", "test2");

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
        lobbyService.stopGame(1L);
        Assert.assertEquals(game.getStatus(),GameStatus.FINISHED);
    }

    @Test
    public void deleteGame() {
        User user1 = userService.createUser("testUser1", "test1");
        User user2 = userService.createUser("testUser2", "test2");

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
        User user1 = userService.createUser("testUser1", "test1");
        User user2 = userService.createUser("testUser2", "test2");

        Game game = gameService.createGame("testGame", "test1");

        Player player1 = playerService.createPlayer(game.getId(), user1.getId());
        playerService.initializePlayer(game.getId(), player1);
        gameService.addPlayer(game.getId(), player1);
        gameService.updateNrOfPlayers(game.getId());

        Player player2 = playerService.createPlayer(game.getId(), user2.getId());
        playerService.initializePlayer(game.getId(), player2);
        gameService.addPlayer(game.getId(), player2);
        gameService.updateNrOfPlayers(game.getId());

        try{
            lobbyService.startGame(game.getId(),player1.getId());
            lobbyService.fastForward(game.getId(),player1.getId());
        } catch (BadRequestHttpException e) {}

        User user3 = userService.createUser("testUser3", "test3");
        User user4 = userService.createUser("testUser4", "test4");

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

        List<MarketCard> handcardsP1 = new ArrayList<>();
        game2.getPlayerByPlayerNr(1).setHandCards(handcardsP1);

        List<MarketCard> handcardsP2 = new ArrayList<>();
        game2.getPlayerByPlayerNr(2).setHandCards(handcardsP2);

        lobbyService.fastForward(game2.getId(),player3.getId());

        Assert.assertEquals(game2.getRoundCounter(),6);
        Assert.assertNotNull(game2.getRoundByRoundCounter());

        Assert.assertEquals(game2.getPlayerByPlayerNr(1).getHandCards().size(),3);
        Assert.assertEquals(game2.getPlayerByPlayerNr(2).getHandCards().size(),4);

        Assert.assertEquals(game2.getPlayerByPlayerNr(1).getHandCards().get(0).getColor(), GameConstants.BLUE);
        Assert.assertEquals(game2.getPlayerByPlayerNr(1).getHandCards().get(1).getColor(),GameConstants.BLUE);
        Assert.assertEquals(game2.getPlayerByPlayerNr(1).getHandCards().get(2).getColor(), GameConstants.GREEN);

        Assert.assertEquals(game2.getPlayerByPlayerNr(2).getHandCards().get(0).getColor(), GameConstants.VIOLET);
        Assert.assertEquals(game2.getPlayerByPlayerNr(2).getHandCards().get(1).getColor(), GameConstants.VIOLET);
        Assert.assertEquals(game2.getPlayerByPlayerNr(2).getHandCards().get(2).getColor(), GameConstants.VIOLET);
        Assert.assertEquals(game2.getPlayerByPlayerNr(2).getHandCards().get(3).getColor(), GameConstants.VIOLET);

        Assert.assertEquals(game2.getPlayerByPlayerNr(1).getPoints()[0],6);
        Assert.assertEquals(game2.getPlayerByPlayerNr(2).getPoints()[0],11);

        Assert.assertEquals(game2.getPlayerByPlayerNr(1).getPoints()[1],1);
        Assert.assertEquals(game2.getPlayerByPlayerNr(2).getPoints()[1],3);

        Assert.assertEquals(game2.getBuildingSite(GameConstants.OBELISK).getStones().size(),7);
        Assert.assertEquals(game2.getBuildingSite(GameConstants.PYRAMID).getStones().size(),7);
        Assert.assertEquals(game2.getBuildingSite(GameConstants.TEMPLE).getStones().size(),4);
        Assert.assertEquals(game2.getBuildingSite(GameConstants.BURIAL_CHAMBER).getStones().size(),12);

        Assert.assertEquals(game2.getStoneQuarry().getBlackStones().size(),18);
        Assert.assertEquals(game2.getStoneQuarry().getWhiteStones().size(),8);

        Assert.assertNotNull(gameRepository.findById(game2.getId()));


    }
}
