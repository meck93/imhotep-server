package ch.uzh.ifi.seal.soprafs17.service.scoring;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.site.ASite;
import ch.uzh.ifi.seal.soprafs17.entity.site.BurialChamber;
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
    private GameService gameService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private UserService userService;

    private  BurialChamberScorer burialChamberScorer = new BurialChamberScorer();

    @Test
    public void convertToArray(){
        List<Stone> stones = new ArrayList<>();
        Stone stone1 = new Stone();
        Stone stone2 = new Stone();
        Stone stone3 = new Stone();
        Stone stone4 = new Stone();

        stone1.setColor(GameConstants.BLACK);
        stone2.setColor(GameConstants.WHITE);
        stone3.setColor(GameConstants.BROWN);
        stone4.setColor(GameConstants.GRAY);

        stones.add(stone1);
        stones.add(stone2);
        stones.add(stone3);
        stones.add(stone4);

        int[] resultArray ={
                1,2,3,4,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0
        };

        int [] testArray = burialChamberScorer.convertToArray(stones);

        for (int i = 0; i<30; i++) {
            Assert.assertEquals(resultArray[i], testArray[i]);
        }
    }

    @Test
    public void isValid(){
        int pos1 = 0;
        int pos2 = -360;
        int pos3 = 420;

        Assert.assertEquals(burialChamberScorer.isValid(pos1),true);
        Assert.assertEquals(burialChamberScorer.isValid(pos2),false);
        Assert.assertEquals(burialChamberScorer.isValid(pos3),false);
    }

    @Test
    public void lookDown(){
        int[] testArray ={
                1,2,3,4,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0
        };

        int pos1 = 0;
        int pos2 = 23;
        int pos3 = 420;
        int pos4 = -360;

        Assert.assertEquals(burialChamberScorer.lookDown(testArray,pos1),10);
        Assert.assertEquals(burialChamberScorer.lookDown(testArray,pos2),-1);
        Assert.assertEquals(burialChamberScorer.lookDown(testArray,pos3),-1);
        Assert.assertEquals(burialChamberScorer.lookDown(testArray,pos4),-1);
    }

    @Test
    public void lookUp(){
        int[] testArray ={
                1,2,3,4,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0
        };

        int pos1 = 0;
        int pos2 = 23;
        int pos3 = 420;
        int pos4 = -360;

        Assert.assertEquals(burialChamberScorer.lookUp(testArray,pos1),-1);
        Assert.assertEquals(burialChamberScorer.lookUp(testArray,pos2),13);
        Assert.assertEquals(burialChamberScorer.lookUp(testArray,pos3),-1);
        Assert.assertEquals(burialChamberScorer.lookUp(testArray,pos4),-1);
    }

    @Test
    public void lookRight(){
        int[] testArray ={
                1,2,3,4,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0
        };

        int pos1 = 0;
        int pos2 = 9;
        int pos3 = 420;
        int pos4 = -360;

        Assert.assertEquals(burialChamberScorer.lookRight(testArray,pos1),1);
        Assert.assertEquals(burialChamberScorer.lookRight(testArray,pos2),-1);
        Assert.assertEquals(burialChamberScorer.lookRight(testArray,pos3),-1);
        Assert.assertEquals(burialChamberScorer.lookRight(testArray,pos4),-1);
    }

    @Test
    public void incrementCount() {
        int counter1 = 0;
        int counter2 = 420;
        int counter3 = -360;
        int counter4 = 20;

        int[] testArray ={
                1,2,3,4,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,0
        };

        Assert.assertEquals(burialChamberScorer.incrementCount(counter1,testArray),10);
        Assert.assertEquals(burialChamberScorer.incrementCount(counter2,testArray),-1);
        Assert.assertEquals(burialChamberScorer.incrementCount(counter3,testArray),-1);
        Assert.assertEquals(burialChamberScorer.incrementCount(counter4,testArray),1);
    }

    @Test
    public void findLookUpIndex(){

        int[] testArray ={
                1,-2,3,4,0,0,0,0,0,0,
                0,0,1,0,0,0,3,0,0,0,
                0,0,1,0,0,0,4,0,0,0
        };

        Assert.assertEquals(burialChamberScorer.findLookUpIndex(testArray),1);

    }

    @Test
    public void addPoints(){
        Game game = gameService.createGame("testName", "testOwner");
        User user1 = userService.createUser("testName","testUser");
        Player player1 = playerService.createPlayer(1L,1L);
        user1.setPlayer(player1);
        List<Player> players = new ArrayList<>();
        players.add(player1);
        game.setPlayers(players);

        int[] points = {0,0,0,0};
        player1.setPoints(points);

        burialChamberScorer.addPoints(game,1,0);
        Assert.assertEquals(player1.getPoints()[3],0);

        burialChamberScorer.addPoints(game,1,1);
        Assert.assertEquals(player1.getPoints()[3],1);

        burialChamberScorer.addPoints(game,1,2);
        Assert.assertEquals(player1.getPoints()[3],4);

        burialChamberScorer.addPoints(game,1,3);
        Assert.assertEquals(player1.getPoints()[3],10);

        burialChamberScorer.addPoints(game,1,4);
        Assert.assertEquals(player1.getPoints()[3],20);

        burialChamberScorer.addPoints(game,1,5);
        Assert.assertEquals(player1.getPoints()[3],35);

        burialChamberScorer.addPoints(game,1,6);
        Assert.assertEquals(player1.getPoints()[3],52);
    }

    @Test
    public void scoreChamber(){

        int[] testArray ={
                1,0,0,1,0,0,0,1,0,0,
                1,0,0,1,0,0,0,1,0,0,
                1,1,1,1,0,0,0,0,0,1
        };

        Game game = gameService.createGame("testName", "testOwner");
        User user1 = userService.createUser("testName","testUser");
        Player player1 = playerService.createPlayer(1L,1L);
        user1.setPlayer(player1);
        List<Player> players = new ArrayList<>();
        players.add(player1);
        game.setPlayers(players);

        int[] points = {0,0,0,0};
        player1.setPoints(points);

        burialChamberScorer.scoreChamber(testArray,1,game);
        Assert.assertEquals(player1.getPoints()[3],25);
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
