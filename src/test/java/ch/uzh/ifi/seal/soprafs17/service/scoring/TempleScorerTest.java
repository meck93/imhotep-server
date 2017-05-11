package ch.uzh.ifi.seal.soprafs17.service.scoring;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
import ch.uzh.ifi.seal.soprafs17.exceptions.InternalServerException;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.user.PlayerService;
import ch.uzh.ifi.seal.soprafs17.service.user.UserService;
import org.junit.Assert;
import org.junit.Before;
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
 * Test class for the TempleScorer REST resource.
 *
 * @see TempleScorer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class TempleScorerTest {

    @Autowired
    private GameService gameService;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PlayerService playerService;

    private TempleScorer templeScorer;
    private Game game;

    @Before
    public void createEnvironment(){
        // Set Up for supports()
        templeScorer = new TempleScorer();

        // Set Up the Environment for the Tests (1 Game with 2 Players)
        game = this.gameService.createGame("Test", "test1");
        Assert.assertNotNull(game);
        Assert.assertEquals(game, this.gameService.findById(game.getId()));

        // Creating 2 Users
        User user1 = userService.createUser("test1");
        User user2 = userService.createUser("test2");
        Assert.assertNotNull(user1);
        Assert.assertNotNull(user2);

        // Converting the first user into the first player
        Player player1 = this.playerService.createPlayer(game.getId(), user1.getId());
        Assert.assertNotNull(player1);
        Assert.assertEquals(player1, this.playerService.findPlayerById(player1.getId()));
        this.playerService.initializePlayer(game.getId(), player1);
        // adding the player to the game
        this.gameService.addPlayer(game.getId(), player1);
        this.gameService.updateNrOfPlayers(game.getId());

        // Converting the second user into the second player
        Player player2 = this.playerService.createPlayer(game.getId(), user2.getId());
        Assert.assertNotNull(player2);
        Assert.assertEquals(player2, this.playerService.findPlayerById(player2.getId()));
        this.playerService.initializePlayer(game.getId(), player2);
        // adding the player to the game
        this.gameService.addPlayer(game.getId(), player2);
        this.gameService.updateNrOfPlayers(game.getId());

        Assert.assertEquals(game.getPlayers().size(), 2);
        Assert.assertEquals(game.getStatus(), GameStatus.PENDING);

        this.gameRepository.save(game);
    }

    /**
     * Setting up additional environment for 2 PLAYERS
     */
    public void additionalEnvironment2Players(){
        // No additional setup for two players is required
        this.gameService.startGame(game.getId());
    }

    /**
     * Setting up additional environment for 3 PLAYERS
     */
    public void additionalEnvironment3Players(){
        // Creating User 3
        User user3 = this.userService.createUser("test3");
        Assert.assertNotNull(user3);

        // Converting the third user into the third player
        Player player3 = this.playerService.createPlayer(game.getId(), user3.getId());
        Assert.assertNotNull(player3);
        Assert.assertEquals(player3, this.playerService.findPlayerById(player3.getId()));
        this.playerService.initializePlayer(game.getId(), player3);

        // Adding the Player to the Game
        this.gameService.addPlayer(game.getId(), player3);
        this.gameService.updateNrOfPlayers(game.getId());

        // Starting the Game
        this.gameService.startGame(game.getId());
    }

    /**
     * Setting up additional environment for 4 PLAYERS
     */
    public void additionalEnvironment4Players(){
        // Creating User 3 & 4
        User user3 = this.userService.createUser("test3");
        Assert.assertNotNull(user3);
        User user4 = this.userService.createUser("test4");
        Assert.assertNotNull(user4);

        // Converting the third user into the third player
        Player player3 = this.playerService.createPlayer(game.getId(), user3.getId());
        Assert.assertNotNull(player3);
        Assert.assertEquals(player3, this.playerService.findPlayerById(player3.getId()));
        this.playerService.initializePlayer(game.getId(), player3);

        // Adding the Player to the Game
        this.gameService.addPlayer(game.getId(), player3);
        this.gameService.updateNrOfPlayers(game.getId());


        // Converting the fourth user into the fourth player
        Player player4 = this.playerService.createPlayer(game.getId(), user4.getId());
        Assert.assertNotNull(player4);
        Assert.assertEquals(player4, this.playerService.findPlayerById(player4.getId()));
        this.playerService.initializePlayer(game.getId(), player4);

        // Adding the Player to the Game
        this.gameService.addPlayer(game.getId(), player4);
        this.gameService.updateNrOfPlayers(game.getId());

        // Starting the Game
        this.gameService.startGame(game.getId());
    }

    @Test
    public void supports() {
        this.additionalEnvironment2Players();

        // Asserting that the Obelisk Scorer supports the OBELISK and nothing else
        Assert.assertEquals(templeScorer.supports(game.getBuildingSite(GameConstants.TEMPLE).getSiteType()),true);
        Assert.assertEquals(templeScorer.supports(game.getBuildingSite(GameConstants.BURIAL_CHAMBER).getSiteType()),false);
    }

    @Test(expected = InternalServerException.class)
    public void testWrongColorException(){
        this.additionalEnvironment2Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        // Creating a Stone with the WRONG_COLOR
        Stone stone1 = new Stone();
        // Wrong Color for the Exception Test
        stone1.setColor("WRONG_COLOR");
        stoneList.add(stone1);

        // Creating a Stone with the correct color
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BLACK);
        stoneList.add(stone2);

        game.getBuildingSite(GameConstants.TEMPLE).setStones(stoneList);

        this.gameRepository.save(game);

        // Asserting the the scoreNow() and scoreEndOfGame() return null
        Assert.assertEquals(this.templeScorer.scoreNow(game), null);
        Assert.assertEquals(this.templeScorer.scoreEndOfGame(game), null);

        // Scoring the Obelisk with the scoreEndOfGame()
        Game newGame = this.templeScorer.scoreEndOfRound(game);
    }

    @Test
    public void test2Player(){
        this.additionalEnvironment2Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        // Creating 2 BLACK Stones
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BLACK);
        stoneList.add(stone2);

        // Creating 3 WHITE Stones
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.WHITE);
        stoneList.add(stone4);
        stoneList.add(stone4);
        stoneList.add(stone4);

        game.getBuildingSite(GameConstants.TEMPLE).setStones(stoneList);

        this.gameRepository.save(game);

        // Asserting the the scoreNow() and scoreEndOfGame() return null
        Assert.assertEquals(this.templeScorer.scoreNow(game), null);
        Assert.assertEquals(this.templeScorer.scoreEndOfGame(game), null);

        // Scoring the Obelisk with the scoreEndOfGame()
        Game newGame = this.templeScorer.scoreEndOfRound(game);

        // Assert that the correct scores have been set (Only the last 4 Stones will be scored)
        // Player 1 / BLACK = 1 Point even though there are 2 BLACK Stones on TEMPLE
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[1], 1);
        // Player 2 / WHITE = 3 Points
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[1], 3);
    }

    @Test
    public void test3Player(){
        this.additionalEnvironment3Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        // Creating 1 BLACK Dummy Stone
        Stone stone11 = new Stone();
        stone11.setColor(GameConstants.BLACK);
        stoneList.add(stone11);

        // Creating 2 BROWN Dummy Stones
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);
        stoneList.add(stone2);

        // Creating 3 WHITE Dummy Stones
        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        stoneList.add(stone1);
        stoneList.add(stone1);

        game.getBuildingSite(GameConstants.TEMPLE).setStones(stoneList);

        this.gameRepository.save(game);

        // Asserting the the scoreNow() and scoreEndOfGame() return null
        Assert.assertEquals(this.templeScorer.scoreNow(game), null);
        Assert.assertEquals(this.templeScorer.scoreEndOfGame(game), null);

        // Scoring the Obelisk with the scoreEndOfGame()
        Game newGame = this.templeScorer.scoreEndOfRound(game);

        // Assert that the correct scores have been set (Only the last 5 Stones will be scored)
        // Player 1 / BLACK = 0 Points even though there is 1 BLACK Stone on TEMPLE
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[1], 0);
        // Player 2 / WHITE = 3 Points
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[1], 3);
        // Player 3 / BROWN = 2 Points
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[1], 2);
    }

    @Test
    public void test4Player(){
        this.additionalEnvironment4Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        // Creating 1 BLACK Dummy Stone
        Stone stone11 = new Stone();
        stone11.setColor(GameConstants.BLACK);
        stoneList.add(stone11);

        // Creating 1 BROWN Dummy Stone
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);

        // Creating 1 WHITE Dummy Stone
        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);

        // Creating 2 GRAY Dummy Stone
        Stone stone3 = new Stone();
        stone3.setColor(GameConstants.GRAY);
        stoneList.add(stone3);
        stoneList.add(stone3);

        game.getBuildingSite(GameConstants.TEMPLE).setStones(stoneList);

        this.gameRepository.save(game);

        // Asserting the the scoreNow() and scoreEndOfGame() return null
        Assert.assertEquals(this.templeScorer.scoreNow(game), null);
        Assert.assertEquals(this.templeScorer.scoreEndOfGame(game), null);

        // Scoring the Obelisk with the scoreEndOfGame()
        Game newGame = this.templeScorer.scoreEndOfRound(game);

        // Assert that the correct scores have been set (Only the last 5 Stones will be scored)
        // Player 1 / BLACK = 1 Point
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[1], 1);
        // Player 2 / WHITE = 1 Point
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[1], 1);
        // Player 3 / BROWN = 1 Point
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[1], 1);
        // Player 4 / GRAY = 2 Points
        Assert.assertEquals(newGame.getPlayerByPlayerNr(4).getPoints()[1], 2);
    }
}
