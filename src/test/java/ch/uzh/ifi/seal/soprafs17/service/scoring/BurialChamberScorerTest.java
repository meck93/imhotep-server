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

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for the BurialChamberScorer REST resource.
 *
 * @see BurialChamberScorer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class BurialChamberScorerTest {

    private Game game;

    @Autowired
    private GameService gameService;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PlayerService playerService;

    private  BurialChamberScorer burialChamberScorer;
    
    @Test
    public void supports() {
        Assert.assertEquals(burialChamberScorer.supports(GameConstants.BURIAL_CHAMBER),true);
    }

    @Before
    public void createEnvironment(){
        // Creating the burialChamberScorer
        burialChamberScorer = new BurialChamberScorer();
        Assert.assertNotNull(burialChamberScorer);

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

        // Starting the Game
        this.gameService.startGame(1L);
        Assert.assertEquals(game.getStatus(), GameStatus.RUNNING);

        this.gameRepository.save(game);
    }

    @Test
    public void convertToArray(){

        // Creating a list for the DummyStones
        List<Stone> stones = new ArrayList<>();

        // Creating four DummyStones (all different Color)
        Stone stone1 = new Stone();
        Stone stone2 = new Stone();
        Stone stone3 = new Stone();
        Stone stone4 = new Stone();

        // Set the stone's color according to the testArray
        stone1.setColor(GameConstants.BLACK);
        stone2.setColor(GameConstants.WHITE);
        stone3.setColor(GameConstants.BROWN);
        stone4.setColor(GameConstants.GRAY);

        // Adding the four Stones to the List for the first time
        stones.add(stone1);
        stones.add(stone2);
        stones.add(stone3);
        stones.add(stone4);
      
        // Adding the same four Stones again
        stones.add(stone1);
        stones.add(stone2);
        stones.add(stone3);
        stones.add(stone4);

        int[] testArray ={
                1,4,3,0,0,0,0,0,0,0,
                2,1,4,0,0,0,0,0,0,0,
                3,2,0,0,0,0,0,0,0,0
        };

        // Convert the list to an array to compute the algorithm
        int[] resultArray = burialChamberScorer.convertToArray(stones);

        // First stone is a black stone (player 1)
        Assert.assertEquals(resultArray[0],1);
        // Second stone is a white stone (player 2)
        Assert.assertEquals(resultArray[10],2);
        // Third stone is a brown stone (player 3)
        Assert.assertEquals(resultArray[20],3);
        // Fourth stone is a gray stone (player 4)
        Assert.assertEquals(resultArray[1],4);

        for (int i = 0; i < resultArray.length;i++) {
            // check if the list was correctly converted
            Assert.assertEquals(resultArray[i], testArray[i]);
        }
    }

    @Test
    public void isValid(){
        // Test index 0
        int pos1 = 0;
        // Test a negative index
        int pos2 = -360;
        // Test a index not contained in our array
        int pos3 = 420;

        // 0 is a valid index
        Assert.assertEquals(burialChamberScorer.isValid(pos1),true);
        // -360 is an invalid index
        Assert.assertEquals(burialChamberScorer.isValid(pos2),false);
        // 420 is an invalid index
        Assert.assertEquals(burialChamberScorer.isValid(pos3),false);
    }

    @Test
    public void lookDown(){
        // testArray
        int[] testArray ={
                1,2,3,4,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0
        };

        int pos1 = 0;
        int pos2 = 23;
        int pos3 = 420;
        int pos4 = -360;

        // Valid lookDown
        Assert.assertEquals(burialChamberScorer.lookDown(testArray,pos1),10);
        // Invalid lookDown
        Assert.assertEquals(burialChamberScorer.lookDown(testArray,pos2),-1);
        // Invalid lookDown
        Assert.assertEquals(burialChamberScorer.lookDown(testArray,pos3),-1);
        // Invalid lookDown
        Assert.assertEquals(burialChamberScorer.lookDown(testArray,pos4),-1);
    }

    @Test
    public void lookUp(){
        // testArray
        int[] testArray ={
                1,2,3,4,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0
        };

        int pos1 = 0;
        int pos2 = 23;
        int pos3 = 420;
        int pos4 = -360;

        // Invalid lookUp
        Assert.assertEquals(burialChamberScorer.lookUp(testArray,pos1),-1);
        // Valid lookUp
        Assert.assertEquals(burialChamberScorer.lookUp(testArray,pos2),13);
        // Invalid lookUp
        Assert.assertEquals(burialChamberScorer.lookUp(testArray,pos3),-1);
        // Invalid lookUp
        Assert.assertEquals(burialChamberScorer.lookUp(testArray,pos4),-1);
    }

    @Test
    public void lookRight(){
        // testArray
        int[] testArray ={
                1,2,3,4,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0
        };

        int pos1 = 0;
        int pos2 = 9;
        int pos3 = 420;
        int pos4 = -360;

        // Valid lookRight
        Assert.assertEquals(burialChamberScorer.lookRight(testArray,pos1),1);
        // Invalid lookRight
        Assert.assertEquals(burialChamberScorer.lookRight(testArray,pos2),-1);
        // Invalid lookRight
        Assert.assertEquals(burialChamberScorer.lookRight(testArray,pos3),-1);
        // Invalid lookRight
        Assert.assertEquals(burialChamberScorer.lookRight(testArray,pos4),-1);
    }

    @Test
    public void lookLeft(){
        // testArray
        int[] testArray ={
                1,2,3,4,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0
        };

        int pos1 = 0;
        int pos2 = 9;
        int pos3 = 420;
        int pos4 = -360;

        // Invalid lookLeft
        Assert.assertEquals(burialChamberScorer.lookLeft(testArray,pos1),-1);
        // Valid lookLeft
        Assert.assertEquals(burialChamberScorer.lookLeft(testArray,pos2),8);
        // Invalid lookLeft
        Assert.assertEquals(burialChamberScorer.lookLeft(testArray,pos3),-1);
        // Invalid lookLeft
        Assert.assertEquals(burialChamberScorer.lookLeft(testArray,pos4),-1);
    }

    @Test
    public void incrementCount() {
        // test counters
        int counter1 = 0;
        int counter2 = 420;
        int counter3 = -360;
        int counter4 = 20;

        // testArray
        int[] testArray ={
                1,2,3,4,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0
        };

        // Valid lookRight
        Assert.assertEquals(burialChamberScorer.incrementCount(counter1,testArray),10);
        // Invalid lookRight
        Assert.assertEquals(burialChamberScorer.incrementCount(counter2,testArray),-1);
        // Invalid lookRight
        Assert.assertEquals(burialChamberScorer.incrementCount(counter3,testArray),-1);
        // Valid lookRight
        Assert.assertEquals(burialChamberScorer.incrementCount(counter4,testArray),1);
    }

    @Test
    public void findLookUpIndex(){
        // testArray
        int[] testArray ={
                1,-2,3,4,0,0,0,0,0,0,
                0,0,1,0,0,0,3,0,0,0,
                0,0,1,0,0,0,4,0,0,0
        };

        // find lookUpIndex
        Assert.assertEquals(burialChamberScorer.findLookUpIndex(testArray),1);
    }

    @Test
    public void addPoints(){
        Player player1 = game.getPlayerByPlayerNr(1);

        burialChamberScorer.addPoints(game,1,0);
        Assert.assertEquals(player1.getPoints()[3],0);
        // score points
        burialChamberScorer.addPoints(game,1,1);
        Assert.assertEquals(player1.getPoints()[3],1);
        // score points
        burialChamberScorer.addPoints(game,1,2);
        Assert.assertEquals(player1.getPoints()[3],4);
        // score points
        burialChamberScorer.addPoints(game,1,3);
        Assert.assertEquals(player1.getPoints()[3],10);
        // score points
        burialChamberScorer.addPoints(game,1,4);
        Assert.assertEquals(player1.getPoints()[3],20);
        // score points
        burialChamberScorer.addPoints(game,1,5);
        Assert.assertEquals(player1.getPoints()[3],35);
        // score points
        burialChamberScorer.addPoints(game,1,6);
        Assert.assertEquals(player1.getPoints()[3],52);
    }

    @Test
    public void scoreChamber(){
        game = this.gameService.findById(1L);
      
        // testArray
        int[] testArray ={
                0,1,1,1,1,0,0,0,0,0,
                1,0,1,0,1,0,0,0,0,0,
                0,1,1,1,0,0,0,0,0,0
        };

        burialChamberScorer.scoreChamber(testArray,1, game);

        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[3],24);
    }

    @Test
    public void scoreEndOfGame(){
        // Retrieving the Game
        game = this.gameService.findById(1L);

        // Creating a list for the testStones
        List<Stone> stoneList = new ArrayList<>();

        // Dummy Stone Color BLACK
        Stone stoneBlack = new Stone();
        stoneBlack.setColor(GameConstants.BLACK);

        // Dummy Stone Color WHITE
        Stone stoneWhite = new Stone();
        stoneWhite.setColor(GameConstants.WHITE);

        /* Creating a form on the BURIAL CHAMBER that looks like this
           1 = BLACK Color 2 = WHITE Color

           1 2 2 1 2
           1 2 2 1 2
           1 1 1 1 2

           Player with color BLACK has 8 connected Stone-Blocks
           Player with color WHITE has 4 and 3 connected Stone-Blocks

         */

        // First Row
        stoneList.add(stoneBlack);
        stoneList.add(stoneBlack);
        stoneList.add(stoneBlack);

        // Second Row
        stoneList.add(stoneWhite);
        stoneList.add(stoneWhite);
        stoneList.add(stoneBlack);

        // Third Row
        stoneList.add(stoneWhite);
        stoneList.add(stoneWhite);
        stoneList.add(stoneBlack);

        // Fourth Row
        stoneList.add(stoneBlack);
        stoneList.add(stoneBlack);
        stoneList.add(stoneBlack);

        // Fifth Row
        stoneList.add(stoneWhite);
        stoneList.add(stoneWhite);
        stoneList.add(stoneWhite);

        // Adding the Test-Stones to the BURIAL CHAMBER
        game.getBuildingSite(GameConstants.BURIAL_CHAMBER).setStones(stoneList);

        // Scoring the BURIAL CHAMBER
        burialChamberScorer.scoreEndOfGame(game);

        // Asserting that the score is correct
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[3],21);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[3],16);
    }

    /*
    The following methods are not supported by the BURIAL CHAMBER
    And will always return null.
     */

    @Test
    public void scoreNow() {
        Assert.assertNull(burialChamberScorer.scoreNow(new Game()));
    }

    @Test
    public void scoreEndOfRound() {
        Assert.assertNull(burialChamberScorer.scoreNow(new Game()));
    }
}