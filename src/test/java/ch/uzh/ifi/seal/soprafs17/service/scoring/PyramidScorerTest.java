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

    private PyramidScorer pyramidScorer = new PyramidScorer();

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

    @Test
    public void scoreNow() throws Exception{
        try {
            Game game = gameService.createGame("testName", "testOwner");
            List<Player> players = new ArrayList<>();

            User user1 = userService.createUser("test","testOwner");
            Player player1 = playerService.createPlayer(1L,1L);
            playerService.initializePlayer(game.getId(), player1);

            User user2 = userService.createUser("test2","test2");
            Player player2 = playerService.createPlayer(1L,2L);
            playerService.initializePlayer(game.getId(), player2);

            User user3 = userService.createUser("test3","test3");
            Player player3 = playerService.createPlayer(1L,3L);
            playerService.initializePlayer(game.getId(), player3);

            User user4 = userService.createUser("test4","test4");
            Player player4 = playerService.createPlayer(1L,4L);
            playerService.initializePlayer(game.getId(), player4);

            players.add(player1);
            players.add(player2);
            players.add(player3);
            players.add(player4);

            game.setPlayers(players);

            gameRepository.save(game);

            this.gameService.updateNrOfPlayers(game.getId());

            gameService.startGame(game.getId());

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

            stones.add(stone1);
            stones.add(stone2);
            stones.add(stone3);
            stones.add(stone4);

            game.getBuildingSite(GameConstants.PYRAMID).setStones(stones);

            gameRepository.save(game);

            Game newGame = pyramidScorer.scoreNow(game);

            Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[0],2);
            Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[0],1);
            Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[0],3);
            Assert.assertEquals(newGame.getPlayerByPlayerNr(4).getPoints()[0],2);

        } catch (NullPointerException e) {}
    }

    @Test
    public void scoreEndOfRound() throws Exception{
        try {
            Assert.assertNull(pyramidScorer.scoreEndOfRound(new Game()));
        } catch (NullPointerException e) {}
    }

}
