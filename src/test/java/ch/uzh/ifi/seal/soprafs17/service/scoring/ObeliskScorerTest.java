package ch.uzh.ifi.seal.soprafs17.service.scoring;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
import ch.uzh.ifi.seal.soprafs17.exceptions.InternalServerException;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.repository.RoundRepository;
import ch.uzh.ifi.seal.soprafs17.repository.StoneQuarryRepository;
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
 * Test class for the ObeliskScorer REST resource.
 *
 * @see ObeliskScorer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class ObeliskScorerTest {

    @Autowired
    private GameService gameService;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PlayerService playerService;

    private ObeliskScorer obeliskScorer;
    private Game game;

    @Before
    public void createEnvironment(){
        // Set Up for supports()
        obeliskScorer = new ObeliskScorer();

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
        Assert.assertEquals(obeliskScorer.supports(game.getBuildingSite(GameConstants.OBELISK).getSiteType()),true);
        Assert.assertEquals(obeliskScorer.supports(game.getBuildingSite(GameConstants.BURIAL_CHAMBER).getSiteType()),false);
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

        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        this.gameRepository.save(game);

        // Asserting the the scoreNow() and scoreEndOfRound() return null
        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Scoring the Obelisk with the scoreEndOfGame()
        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        // Assert that the correct scores have been set
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 10);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 0);
    }

    @Test
    public void testTwoPlayersWhiteMore(){
        this.additionalEnvironment2Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        // Creating 2 BLACK Stones
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BLACK);
        stoneList.add(stone2);
        stoneList.add(stone2);

        // Creating 3 WHITE Stones
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.WHITE);
        stoneList.add(stone4);
        stoneList.add(stone4);
        stoneList.add(stone4);

        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        this.gameRepository.save(game);

        // Asserting the the scoreNow() and scoreEndOfRound() return null
        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Scoring the Obelisk with the scoreEndOfGame()
        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        // Assert that the correct scores have been set
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 1);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 10);
    }

    @Test
    public void testTwoPlayersBlackMore(){
        this.additionalEnvironment2Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        // Creating 2 BLACK Stones
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BLACK);
        stoneList.add(stone2);
        stoneList.add(stone2);

        // Creating 1 WHITE Stone
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.WHITE);
        stoneList.add(stone4);

        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        this.gameRepository.save(game);

        // Asserting the the scoreNow() and scoreEndOfRound() return null
        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Scoring the Obelisk with the scoreEndOfGame()
        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        // Assert that the correct scores have been set
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 10);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 1);
    }

    @Test
    public void testTwoPlayersSame(){
        this.additionalEnvironment2Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        // Creating 1 BLACK Stone
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BLACK);
        stoneList.add(stone2);

        // Creating 1 WHITE Stone
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.WHITE);
        stoneList.add(stone4);

        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        this.gameRepository.save(game);

        // Asserting the the scoreNow() and scoreEndOfRound() return null
        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Scoring the Obelisk with the scoreEndOfGame()
        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        // Assert that the correct scores have been set
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 5);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 5);
    }

    @Test
    public void testThreePlayerOnly2Score(){
        this.additionalEnvironment3Players();

        // Creating DummyStones for the Obelisk
        List<Stone> stoneList = game.getBuildingSite(GameConstants.OBELISK).getStones();

        // Creating 1 BROWN Dummy Stones
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);

        // Creating 2 WHITE Dummy Stones
        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        stoneList.add(stone1);

        // Adding the Dummy Stones to the Obelisk
        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        // Asserting the the scoreNow() and scoreEndOfRound() return null
        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Scoring the Obelisk with the scoreEndOfGame()
        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        // Assert that the correct scores have been set
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 0);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 12);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 6);
    }

    @Test
    public void testThreePlayerAllDifferent(){
        this.additionalEnvironment3Players();

        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Creating DummyStones for the Obelisk
        List<Stone> stoneList = game.getBuildingSite(GameConstants.OBELISK).getStones();

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

        // Adding the Dummy Stones to the Obelisk
        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        // Asserting the the scoreNow() and scoreEndOfRound() return null
        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Scoring the Obelisk with the scoreEndOfGame()
        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        // Assert that the correct scores have been set
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 1);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 12);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 6);
    }

    @Test
    public void testThreePlayerTwoFirstOneLast(){
        this.additionalEnvironment3Players();

        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Creating DummyStones for the Obelisk
        List<Stone> stoneList = game.getBuildingSite(GameConstants.OBELISK).getStones();

        // Creating 1 BLACK Dummy Stone
        Stone stone11 = new Stone();
        stone11.setColor(GameConstants.BLACK);
        stoneList.add(stone11);

        // Creating 2 BROWN Dummy Stones
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);
        stoneList.add(stone2);

        // Creating 2 WHITE Dummy Stones
        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        stoneList.add(stone1);

        // Adding the Dummy Stones to the Obelisk
        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        // Asserting the the scoreNow() and scoreEndOfRound() return null
        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Scoring the Obelisk with the scoreEndOfGame()
        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        // Assert that the correct scores have been set
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 1);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 9);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 9);
    }

    @Test
    public void testThreePlayerOneFirstTwoLast(){
        this.additionalEnvironment3Players();

        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Creating DummyStones for the Obelisk
        List<Stone> stoneList = game.getBuildingSite(GameConstants.OBELISK).getStones();

        // Creating 1 BLACK Dummy Stone
        Stone stone11 = new Stone();
        stone11.setColor(GameConstants.BLACK);
        stoneList.add(stone11);

        // Creating 1 BROWN Dummy Stones
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);

        // Creating 2 WHITE Dummy Stones
        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        stoneList.add(stone1);

        // Adding the Dummy Stones to the Obelisk
        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        // Asserting the the scoreNow() and scoreEndOfRound() return null
        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Scoring the Obelisk with the scoreEndOfGame()
        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        // Assert that the correct scores have been set
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 3);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 12);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 3);
    }

    @Test
    public void testThreePlayerAllSameScore(){
        this.additionalEnvironment3Players();

        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Creating DummyStones for the Obelisk
        List<Stone> stoneList = game.getBuildingSite(GameConstants.OBELISK).getStones();

        // Creating 1 BLACK Dummy Stone
        Stone stone11 = new Stone();
        stone11.setColor(GameConstants.BLACK);
        stoneList.add(stone11);

        // Creating 2 BROWN Dummy Stones
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);

        // Creating 3 WHITE Dummy Stones
        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);

        // Adding the Dummy Stones to the Obelisk
        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        // Asserting the the scoreNow() and scoreEndOfRound() return null
        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Scoring the Obelisk with the scoreEndOfGame()
        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        // Assert that the correct scores have been set
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 6);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 6);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 6);
    }

    @Test
    public void testFourPlayers2LastPlayersHaveSameScore(){
        this.additionalEnvironment4Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        // Creating 1 WHITE Stone
        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);

        // Creating 2 BROWN Stones
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);
        stoneList.add(stone2);

        // Creating 3 BLACK Stones
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.BLACK);
        stoneList.add(stone4);
        stoneList.add(stone4);
        stoneList.add(stone4);

        // Creating 1 GRAY Stone
        Stone stone7 = new Stone();
        stone7.setColor(GameConstants.GRAY);
        stoneList.add(stone7);

        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        this.gameRepository.save(game);

        // Asserting the the scoreNow() and scoreEndOfRound() return null
        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Scoring the Obelisk with the scoreEndOfGame()
        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        // Assert that the correct scores have been set
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 15);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 3);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 10);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(4).getPoints()[2], 3);
    }

    @Test
    public void testFourPlayersOnly3HaveScore(){
        this.additionalEnvironment4Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        // Creating Stones for Player BLACK (3), BROWN(2), WHITE(1)
        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);
        stoneList.add(stone2);
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.BLACK);
        stoneList.add(stone4);
        stoneList.add(stone4);
        stoneList.add(stone4);

        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        this.gameRepository.save(game);

        // Asserting the the scoreNow() and scoreEndOfRound() return null
        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Scoring the Obelisk with the scoreEndOfGame()
        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        // Assert that the correct scores have been set
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 15);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 5);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 10);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(4).getPoints()[2], 0);
    }

    @Test
    public void testFourPlayersNoSameScore(){
        this.additionalEnvironment4Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        // Creating 1 GRAY DummyStones
        Stone stone13 = new Stone();
        stone13.setColor(GameConstants.GRAY);
        stoneList.add(stone13);

        // Creating 2 WHITE DummyStones
        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        stoneList.add(stone1);

        // Creating 3 BROWN DummyStones
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);
        stoneList.add(stone2);
        stoneList.add(stone2);

        // Creating 4 BLACK DummyStones
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.BLACK);
        stoneList.add(stone4);
        stoneList.add(stone4);
        stoneList.add(stone4);
        stoneList.add(stone4);

        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        this.gameRepository.save(game);

        // Asserting the the scoreNow() and scoreEndOfRound() return null
        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Scoring the Obelisk with the scoreEndOfGame()
        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        // Assert that the correct scores have been set
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 15);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 5);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 10);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(4).getPoints()[2], 1);
    }

    @Test
    public void testFourPlayersAllSameScore(){
        this.additionalEnvironment4Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        // Creating a Stone for every Player/Color
        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.BLACK);
        stoneList.add(stone4);
        Stone stone7 = new Stone();
        stone7.setColor(GameConstants.GRAY);
        stoneList.add(stone7);

        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        this.gameRepository.save(game);

        // Asserting the the scoreNow() and scoreEndOfRound() return null
        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Scoring the Obelisk with the scoreEndOfGame()
        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        // All four Players must have the same score
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 7);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 7);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 7);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(4).getPoints()[2], 7);
    }
}
