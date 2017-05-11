package ch.uzh.ifi.seal.soprafs17.service.scoring;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
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
 * Test class for the BurialChamberScorer REST resource.
 *
 * @see BurialChamberScorer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class BurialChamberScorerTest {

    @Autowired
    public GameService gameService;
    @Autowired
    public PlayerService playerService;
    @Autowired
    public UserService userService;

    private  BurialChamberScorer burialChamberScorer = new BurialChamberScorer();
    
    @Test
    public void supports() {
        Assert.assertEquals(burialChamberScorer.supports(GameConstants.BURIAL_CHAMBER),true);
    }

    @Test
    public void score() {
        try {
            Assert.assertNull(burialChamberScorer.score(new Game()));
        } catch (NullPointerException e) {}
    }

    @Test
    public void scoreNow() {
        try {
            // score the chamber now. (Should return null, since the chamber gets scored at the end of the game)
            Assert.assertNull(burialChamberScorer.scoreNow(new Game()));
        } catch (NullPointerException e) {}
    }

    @Test
    public void scoreEndOfRound() {
        try {
            // score the chamber at the end of the round. (Should return null, since the chamber gets scored at the end of the game)
            Assert.assertNull(burialChamberScorer.scoreEndOfRound(new Game()));
        } catch (NullPointerException e) {}
    }

    @Test
    public void convertToArray(){
        // Create List of stones
        List<Stone> stones = new ArrayList<>();
        // Create stones
        Stone stone1 = new Stone();
        Stone stone2 = new Stone();
        Stone stone3 = new Stone();
        Stone stone4 = new Stone();
        Stone stone5 = new Stone();
        Stone stone6 = new Stone();
        Stone stone7 = new Stone();
        Stone stone8 = new Stone();


        // testArray: Player 1's stones = 1, Player 2's stones = 2, Player 3's stones = 33, Player 4's stones = 4,
        int[] testArray ={
                1,4,3,0,0,0,0,0,0,0,
                2,1,4,0,0,0,0,0,0,0,
                3,2,0,0,0,0,0,0,0,0
        };

        // Set the stone's color according to the testArray
        stone1.setColor(GameConstants.BLACK);
        stone2.setColor(GameConstants.WHITE);
        stone3.setColor(GameConstants.BROWN);
        stone4.setColor(GameConstants.GRAY);
        stone5.setColor(GameConstants.BLACK);
        stone6.setColor(GameConstants.WHITE);
        stone7.setColor(GameConstants.BROWN);
        stone8.setColor(GameConstants.GRAY);

        // Add the stones to the list
        stones.add(stone1);
        stones.add(stone2);
        stones.add(stone3);
        stones.add(stone4);

        stones.add(stone5);
        stones.add(stone6);
        stones.add(stone7);
        stones.add(stone8);

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
        // Create game and user
        Game game = gameService.createGame("testName", "testOwner");
        User user1 = userService.createUser("test1");
        // Convert user to player
        Player player1 = playerService.createPlayer(1L,1L);
        user1.setPlayer(player1);
        // Add player to game
        List<Player> players = new ArrayList<>();
        players.add(player1);
        game.setPlayers(players);
        // Initialize player points
        int[] points = {0,0,0,0};
        player1.setPoints(points);
        // score points
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
        // testArray
        int[] testArray ={
                0,1,1,1,1,0,0,0,0,0,
                1,0,1,0,1,0,0,0,0,0,
                0,1,1,1,0,0,0,0,0,0
        };

        // create game and user
        Game game = gameService.createGame("testName", "testOwner");
        User user1 = userService.createUser("test1");
        // convert user to player
        Player player1 = playerService.createPlayer(1L,1L);
        user1.setPlayer(player1);
        // add player to the game
        List<Player> players = new ArrayList<>();
        players.add(player1);
        game.setPlayers(players);
        // initialize player score
        int[] points = {0,0,0,0};
        player1.setPoints(points);
        // score chamber
        burialChamberScorer.scoreChamber(testArray,1,game);
        Assert.assertEquals(player1.getPoints()[3],24);
    }

   /* @Test
    public void scoreEndOfGame(){
        Game game = gameService.createGame("testName", "testOwner");
        game.setId(1L);
        User user1 = userService.createUser("testName","testUser");
        Player player1 = playerService.createPlayer(1L,1L);
        user1.setPlayer(player1);
        List<Player> players = new ArrayList<>();
        players.add(player1);
        *//*User user2 = userService.createUser("testName2","testUser2");
        Player player2 = playerService.createPlayer(1L,2L);
        user2.setPlayer(player2);
        players.add(player2);
        game.setPlayers(players);
*//*
        int[] points = {0,0,0,0};
        player1.setPoints(points);
        *//*player2.setPoints(points);*//*
        int[] testArray ={
                1,0,0,1,0,2,0,1,0,2,
                1,0,0,1,2,2,2,1,0,0,
                1,1,1,1,0,2,0,0,0,1
        };
        burialChamberScorer.scoreEndOfGame(game);
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[3],25);
       *//* Assert.assertEquals(player2.getPoints()[3],18);*//*
    }*/
}