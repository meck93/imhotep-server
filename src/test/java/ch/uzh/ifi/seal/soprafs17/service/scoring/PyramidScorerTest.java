package ch.uzh.ifi.seal.soprafs17.service.scoring;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
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
 * Test class for the PyramidScorer REST resource.
 *
 * @see PyramidScorer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class PyramidScorerTest {

    @Autowired
    private GameService gameService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private UserService userService;
    @Autowired
    private GameRepository gameRepository;

    private PyramidScorer pyramidScorer;
    private Game game;

    @Test
    public void supports()  {
        Assert.assertEquals(pyramidScorer.supports(GameConstants.PYRAMID),true);
    }

    @Test
    public void score() throws Exception{
        try {
            Assert.assertNull(pyramidScorer.score(new Game()));
        } catch (NullPointerException e) {}
    }

    @Before
    public void createEnvironment(){
        pyramidScorer = new PyramidScorer();

        game = gameService.createGame("testName", "testOwner");
        Assert.assertNotNull(game);

        User user1 = userService.createUser("testOwner");
        Player player1 = playerService.createPlayer(1L,1L);
        this.playerService.initializePlayer(game.getId(), player1);
        this.gameService.addPlayer(game.getId(), player1);
        this.gameService.updateNrOfPlayers(game.getId());

        User user2 = userService.createUser("test2");
        Player player2 = playerService.createPlayer(1L,2L);
        this.playerService.initializePlayer(game.getId(), player2);
        this.gameService.addPlayer(game.getId(), player2);
        this.gameService.updateNrOfPlayers(game.getId());

        User user3 = userService.createUser("test3");
        Player player3 = playerService.createPlayer(1L,3L);
        this.playerService.initializePlayer(game.getId(), player3);
        this.gameService.addPlayer(game.getId(), player3);
        this.gameService.updateNrOfPlayers(game.getId());

        User user4 = userService.createUser("test4");
        Player player4 = playerService.createPlayer(1L,4L);
        this.playerService.initializePlayer(game.getId(), player4);
        this.gameService.addPlayer(game.getId(), player4);
        this.gameService.updateNrOfPlayers(game.getId());

        this.gameService.startGame(game.getId());
    }

    @Test
    public void scoreNow() {
            // Creating Dummy/Test Stones for the pyramid
            List<Stone> stones = new ArrayList<>();

            Stone stone1 = new Stone();
            Stone stone2 = new Stone();
            Stone stone3 = new Stone();
            Stone stone4 = new Stone();

            stone1.setColor(GameConstants.BLACK);
            stone2.setColor(GameConstants.WHITE);
            stone3.setColor(GameConstants.BROWN);
            stone4.setColor(GameConstants.GRAY);

            // Adding 4 Stones (1 of each Color)
            stones.add(stone1);
            stones.add(stone2);
            stones.add(stone3);
            stones.add(stone4);

            game.getBuildingSite(GameConstants.PYRAMID).setStones(stones);

            this.gameRepository.save(game);

            Assert.assertNull(this.pyramidScorer.scoreEndOfRound(game));
            Assert.assertNull(this.pyramidScorer.scoreEndOfGame(game));

            Game newGame = this.pyramidScorer.scoreNow(game);

            Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[0],2);
            Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[0],1);
            Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[0],3);
            Assert.assertEquals(newGame.getPlayerByPlayerNr(4).getPoints()[0],2);
    }

    @Test
    public void testPast14Stones(){
        // Creating Dummy/Test Stones for the pyramid
        List<Stone> stones = new ArrayList<>();

        // Creating the first four unique Stones
        Stone stone1 = new Stone();
        Stone stone2 = new Stone();
        Stone stone3 = new Stone();
        Stone stone4 = new Stone();

        stone1.setColor(GameConstants.BLACK);
        stone2.setColor(GameConstants.WHITE);
        stone3.setColor(GameConstants.BROWN);
        stone4.setColor(GameConstants.GRAY);

        // Adding 4 Stones (1 of each Color)
        stones.add(stone1);
        stones.add(stone2);
        stones.add(stone3);
        stones.add(stone4);

        // Creating another four unique stones
        Stone stone12 = new Stone();
        Stone stone22 = new Stone();
        Stone stone32 = new Stone();
        Stone stone42 = new Stone();

        stone12.setColor(GameConstants.BLACK);
        stone22.setColor(GameConstants.WHITE);
        stone32.setColor(GameConstants.BROWN);
        stone42.setColor(GameConstants.GRAY);

        stones.add(stone12);
        stones.add(stone22);
        stones.add(stone32);
        stones.add(stone42);

        // Creating another four unique stones
        Stone stone13 = new Stone();
        Stone stone23 = new Stone();
        Stone stone33 = new Stone();
        Stone stone43 = new Stone();

        stone13.setColor(GameConstants.BLACK);
        stone23.setColor(GameConstants.WHITE);
        stone33.setColor(GameConstants.BROWN);
        stone43.setColor(GameConstants.GRAY);

        stones.add(stone13);
        stones.add(stone23);
        stones.add(stone33);
        stones.add(stone43);

        // Creating another four unique stones
        Stone stone14 = new Stone();
        Stone stone24 = new Stone();
        Stone stone34 = new Stone();
        Stone stone44 = new Stone();

        stone14.setColor(GameConstants.BLACK);
        stone24.setColor(GameConstants.WHITE);
        stone34.setColor(GameConstants.BROWN);
        stone44.setColor(GameConstants.GRAY);

        stones.add(stone14);
        stones.add(stone24);
        stones.add(stone34);
        stones.add(stone44);

        // Creating another two unique stones
        Stone stone15 = new Stone();
        Stone stone25 = new Stone();

        stone15.setColor(GameConstants.BLACK);
        stone25.setColor(GameConstants.WHITE);

        stones.add(stone15);
        stones.add(stone25);

        game.getBuildingSite(GameConstants.PYRAMID).setStones(stones);

        this.gameRepository.save(game);

        Assert.assertNull(this.pyramidScorer.scoreEndOfRound(game));
        Assert.assertNull(this.pyramidScorer.scoreEndOfGame(game));

        Game newGame = this.pyramidScorer.scoreNow(game);

        for (Player player : game.getPlayers()) {
            System.out.println(player.getPoints()[0]);
        }

        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[0],13);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[0],11);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[0],9);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(4).getPoints()[0],5);
    }

    @Test
    public void scoreEndOfRound() {
            Assert.assertNull(pyramidScorer.scoreEndOfRound(new Game()));
    }
}
