package ch.uzh.ifi.seal.soprafs17.service.game;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
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
        // TODO: test lobbyService.createGame()
    }

    @Test
    public void joinGame() {
        // TODO: test lobbyService.joinGame()
    }

    @Test
    public void startGame() {
        /*
        Game game = gameService.createGame("test","testOwner");
        game.setId(1L);
        User user1 = userService.createUser("testName","testOwner");
        user1.setId(1L);
        Player player1 = playerService.createPlayer(1L,1L);
        player1.setPlayerId(1L);
        lobbyService.joinGame(1L,1L);
        try {
            lobbyService.startGame(1L,1L);
            Assert.assertEquals(GameStatus.RUNNING, game.getStatus());
        } catch(BadRequestHttpException e){}

        User user2 = userService.createUser("testName2","testOwner2");
        user2.setId(2L);
        Player player2 = playerService.createPlayer(1L,2L);
        player2.setPlayerId(2L);
        lobbyService.joinGame(1L,2L);
        try {
            lobbyService.startGame(1L,2L);
            Assert.assertEquals(GameStatus.RUNNING, game.getStatus());
        } catch(BadRequestHttpException e){}

        //lobbyService.startGame(1L,1L);
        //Assert.assertEquals(GameStatus.RUNNING, game.getStatus());
        */
    }
}
