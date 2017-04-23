package ch.uzh.ifi.seal.soprafs17.service.game;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.repository.RoundRepository;
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


/**
 * Test class for the RoundResource REST resource.
 *
 * @see RoundService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class RoundServiceTest {

    @Autowired
    public RoundService roundService;

    @Autowired
    public RoundRepository roundRepository;

    @Autowired
    public GameService gameService;

    @Autowired
    public LobbyService lobbyService;

    @Autowired
    public UserService userService;

    @Autowired
    public PlayerService playerService;

    @Test
    public void createRound() {
        Game testGame = gameService.createGame("testGame", "testOwner");
        testGame.setId(1L);
        Round testRound = roundService.createRound(1L,testGame);
        testRound.setId(1L);
        Assert.assertEquals(testRound, roundRepository.findOne(1L));
    }

    @Test
    public void initializeRound() {
        /*
        Game testGame = gameService.createGame("testGame", "testOwner");
        testGame.setId(1L);
        Round testRound = roundService.createRound(1L,testGame);
        testRound.setId(1L);
        User user1 = userService.createUser("testName","testOwner");
        user1.setId(1L);
        Player player1 = playerService.createPlayer(1L,1L);
        lobbyService.joinGame(1L,1L);
        roundService.initializeRound(1L,1L);
        Assert.assertNotNull(testRound.getShips());
        //Assert.assertNotNull(testRound.getCard());
        */
    }

    @Test
    public void listRounds() {
        // TODO: test roundService.listRounds()
    }

    @Test
    public void getRoundByNr() {
        // TODO: test roundService.getRoundByNr()
    }


}
