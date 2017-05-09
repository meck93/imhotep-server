package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.BadRequestHttpException;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.repository.RoundRepository;
import ch.uzh.ifi.seal.soprafs17.repository.StoneQuarryRepository;
import ch.uzh.ifi.seal.soprafs17.service.game.StoneService;
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
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    public StoneQuarryRepository stoneQuarryRepository;

    @Autowired
    public StoneService stoneService;

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
            User user1 = userService.createUser("testName");
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

    public void createEnvironment(){
        // Set Up for validate()
        Game game = this.gameService.createGame("Test", "test");
        Assert.assertNotNull(game);
        Assert.assertEquals(game, this.gameService.findById(game.getId()));

        User user1 = this.userService.createUser("test");
        Assert.assertNotNull(user1);
        User user2 = this.userService.createUser("test2");
        Assert.assertNotNull(user2);

        Player player1 = this.playerService.createPlayer(game.getId(), user1.getId());
        Assert.assertNotNull(player1);
        Assert.assertEquals(player1, this.playerService.findPlayerById(player1.getId()));
        this.playerService.initializePlayer(game.getId(), player1);
        this.gameService.addPlayer(game.getId(), player1);
        this.gameService.updateNrOfPlayers(game.getId());

        Player player2 = this.playerService.createPlayer(game.getId(), user2.getId());
        Assert.assertNotNull(player2);
        Assert.assertEquals(player2, this.playerService.findPlayerById(player2.getId()));
        this.playerService.initializePlayer(game.getId(), player2);
        this.gameService.addPlayer(game.getId(), player2);
        this.gameService.updateNrOfPlayers(game.getId());

        this.gameRepository.save(game);
    }

    @Test
    public void initializeGame() {
        this.createEnvironment();

        Game game = this.gameService.findById(1L);
        Assert.assertNotNull(game);

        // Assert that there doesn't exist a StoneQuarry, a MarketPlace and all the BuildingSites
        Assert.assertNull(game.getStoneQuarry());
        Assert.assertNull(game.getMarketPlace());
        Assert.assertEquals(game.getBuildingSites().size(), 0);
        // Assert that the SupplySleds don't exist
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getSupplySled().getStones().size(), 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getSupplySled().getStones().size(), 0);
        // Assert that the GameStatus is Pending and the currentPlayer = 0
        Assert.assertEquals(game.getStatus(), GameStatus.PENDING);
        Assert.assertEquals(game.getCurrentPlayer().intValue(), 0);

        this.gameService.initializeGame(1L);

        Game newGame = this.gameService.findById(1L);
        Assert.assertNotNull(newGame);

        // Assert that there exists a StoneQuarry, a MarketPlace and all the BuildingSites
        Assert.assertNotNull(newGame.getStoneQuarry());
        Assert.assertNotNull(newGame.getMarketPlace());
        Assert.assertEquals(newGame.getBuildingSites().size(), 4);
        // Assert that the SupplySleds have the correct amount of stones on them
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getSupplySled().getStones().size(), 2);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getSupplySled().getStones().size(), 3);
        // Assert that the GameStatus is RUNNING and the currentPlayer = 1
        Assert.assertEquals(newGame.getStatus(), GameStatus.RUNNING);
        Assert.assertEquals(newGame.getCurrentPlayer().intValue(), 1);
    }

    @Test
    public void initializeRound() {
        this.createEnvironment();

        Game game = this.gameService.findById(1L);
        Assert.assertNotNull(game);

        this.gameService.initializeGame(1L);

        // Assert that there exist no MarketCards on the MarketPlace, no Rounds, RoundCounter = 0
        Assert.assertEquals(game.getMarketPlace().getMarketCards().size(), 0);
        Assert.assertEquals(game.getRounds().size(), 0);
        Assert.assertEquals(game.getRoundCounter(), 0);

        this.gameService.initializeRound(1L);

        Game newGame = this.gameService.findById(1L);
        Assert.assertNotNull(newGame);

        // Assert that
        Assert.assertEquals(game.getMarketPlace().getMarketCards().size(), 4);
        Assert.assertEquals(game.getRounds().size(), 1);
        Assert.assertEquals(game.getRoundCounter(), 1);
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

        //Starting the game and setting the stoneQuarry
        gameService.startGame(game.getId());
        game.setStoneQuarry(stoneQuarryRepository.findOne(1L));

        //Catching exception for illegal playerNumber
        try{
            Assert.assertNull(gameService.sizeOfQuarry(game.getId(),3));
        } catch (NotFoundException e) {}

        //Check if actual stones in the StoneQuarry are equal to the sizeOfQuarry value for both players
        Assert.assertEquals(gameService.sizeOfQuarry(game.getId(),1),game.getStoneQuarry().getBlackStones().size());
        Assert.assertEquals(gameService.sizeOfQuarry(game.getId(),2),game.getStoneQuarry().getWhiteStones().size());
    }
}
