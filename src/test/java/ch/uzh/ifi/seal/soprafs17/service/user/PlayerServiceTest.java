package ch.uzh.ifi.seal.soprafs17.service.user;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.SupplySled;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.BadRequestHttpException;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.repository.PlayerRepository;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
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
 * Test class for the PlayerResource REST resource.
 *
 * @see PlayerService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class PlayerServiceTest {

    @Autowired
    public PlayerService playerService;

    @Autowired
    public UserService userService;

    @Autowired
    public PlayerRepository playerRepository;

    @Autowired
    public GameService gameService;

    @Autowired
    public GameRepository gameRepository;

    @Test
    public void createPlayer() {
        Game testGame = gameService.createGame("testName","testOwner");
        testGame.setId(1L);
        User user = userService.createUser("testName","testOwner");
        Player testPlayer = playerService.createPlayer(1L,1L);
        Assert.assertEquals(testPlayer,playerRepository.findById(1L));

        try {
            Player player2 = playerService.createPlayer(1L,1L);
            user.setPlayer(player2);
            Player testPlayer2 = new Player();
            Assert.assertNull(playerService.createPlayer(1L,1L));
        } catch (BadRequestHttpException e) {}

        try {
            User user2 = userService.createUser("testName","testOwner2");
            Player testPlayer2 = playerService.createPlayer(1L,2L);
            User user3 = userService.createUser("testName","testOwner3");
            Player testPlayer3 = playerService.createPlayer(1L,3L);
            User user4 = userService.createUser("testName","testOwner4");
            Player testPlayer4 = playerService.createPlayer(1L,1L);
            User user5 = userService.createUser("testName","testOwner5");
            Player testPlayer5 = playerService.createPlayer(1L,1L);
        } catch (BadRequestHttpException e) {}

        try {
            testGame.setStatus(GameStatus.RUNNING);
        } catch (BadRequestHttpException e) {}
    }

    @Test
    public void initializePlayer() {
        Game testGame = gameService.createGame("testName","testOwner");
        testGame.setId(1L);
        Player testPlayer1 = new Player();
        testPlayer1.setPlayerNumber(1);
        Player testPlayer2 = new Player();
        testPlayer2.setPlayerNumber(2);
        Player testPlayer3 = new Player();
        testPlayer3.setPlayerNumber(3);
        Player testPlayer4 = new Player();
        testPlayer4.setPlayerNumber(4);

        playerService.initializePlayer(1L,testPlayer1);
        playerService.initializePlayer(1L,testPlayer2);
        playerService.initializePlayer(1L,testPlayer3);
        playerService.initializePlayer(1L,testPlayer4);

        Assert.assertEquals(testPlayer1.getPoints()[0], 0);
        Assert.assertEquals(testPlayer1.getColor(), GameConstants.BLACK);
        Assert.assertNotNull(testPlayer1.getSupplySled());

        Assert.assertEquals(testPlayer2.getPoints()[0], 0);
        Assert.assertEquals(testPlayer2.getColor(), GameConstants.WHITE);
        Assert.assertNotNull(testPlayer2.getSupplySled());

        Assert.assertEquals(testPlayer3.getPoints()[0], 0);
        Assert.assertEquals(testPlayer3.getColor(), GameConstants.BROWN);
        Assert.assertNotNull(testPlayer3.getSupplySled());

        Assert.assertEquals(testPlayer4.getPoints()[0], 0);
        Assert.assertEquals(testPlayer4.getColor(), GameConstants.GRAY);
        Assert.assertNotNull(testPlayer4.getSupplySled());
    }

    @Test
    public void getPlayer() {
        Game game = gameService.createGame("test","testOwner");
        game.setId(1L);
        User user1 = userService.createUser("testName","testOwner");
        user1.setId(1L);
        Player player1 = playerService.createPlayer(1L,1L);
        Assert.assertEquals(playerService.getPlayer(1L,1), playerRepository.findById(1L));
        try {
            user1.setPlayer(player1);
            Assert.assertEquals(playerService.getPlayer(1L,1),playerRepository.findById(1L));
        } catch (BadRequestHttpException e) {}
        try {
            List<Player> players = game.getPlayers();
            players.add(new Player());
            players.add(new Player());
            players.add(new Player());
            players.add(new Player());
            game.setPlayers(players);
            Assert.assertEquals(playerService.getPlayer(1L,1),playerRepository.findById(1L));
        } catch (BadRequestHttpException e) {}
        try {
            game.setStatus(GameStatus.RUNNING);
            Assert.assertEquals(playerService.getPlayer(1L,1),playerRepository.findById(1L));
        } catch (BadRequestHttpException e) {}
    }

    @Test
    public void getPlayers() {
        Game game = gameService.createGame("test","testOwner");
        game.setId(1L);
        User user1 = userService.createUser("testName","testOwner");
        user1.setId(1L);
        Player player1 = playerService.createPlayer(1L,1L);
        List<Player> players = new ArrayList<>();
        players.add(player1);
        game.setPlayers(players);
        Assert.assertEquals(game.getPlayers().get(0),player1);
    }

    @Test
    public void getPlayerSupplySled() {
        Game game = gameService.createGame("test","testOwner");
        game.setId(1L);
        User user1 = userService.createUser("testName","testOwner");
        user1.setId(1L);
        Player player1 = playerService.createPlayer(1L,1L);
        player1.setPlayerNumber(1);
        try {
            Assert.assertNull(playerService.getPlayerSupplySled(1L, 1));
        } catch (NotFoundException e) {}
        SupplySled testSupplySled = new SupplySled();
        player1.setSupplySled(testSupplySled);
        Assert.assertEquals(playerService.getPlayerSupplySled(1L,1),testSupplySled);
    }

    @Test
    public void findPlayerById() {
        Game game = gameService.createGame("test","testOwner");
        game.setId(1L);
        User user1 = userService.createUser("testName","testOwner");
        user1.setId(1L);
        Player player1 = playerService.createPlayer(1L,1L);
        player1.setPlayerId(1L);
        Assert.assertEquals(playerService.findPlayerById(1L),player1);
        try {
            Player player2 = new Player();
            Assert.assertEquals(playerService.findPlayerById(2L),player2);
        } catch(NotFoundException e) {}

    }

    @Test
    public void deletePlayer() {
        Game game = gameService.createGame("test","testOwner");
        game.setId(1L);
        User user1 = userService.createUser("testName","testOwner");
        user1.setId(1L);
        Player player1 = playerService.createPlayer(1L,1L);
        player1.setPlayerId(1L);

        Assert.assertNotNull(playerService.findPlayerById(1L));
        playerService.deletePlayer(1L);
        Assert.assertNull(playerRepository.findById(1L));
    }
}
