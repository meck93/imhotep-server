package ch.uzh.ifi.seal.soprafs17.service.game;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
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

import java.util.ArrayList;
import java.util.List;


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

    @Autowired
    public ShipService shipService;

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
    public void goToNextRound() {
        Game testGame = gameService.createGame("testGame", "testOwner");
        Round testRound = roundService.createRound(1L,testGame);

        Ship testShip1 = new Ship();
        Ship testShip2 = new Ship();
        Ship testShip3 = new Ship();
        Ship testShip4 = new Ship();

        testShip1.setHasSailed(true);
        testShip2.setHasSailed(true);
        testShip3.setHasSailed(true);
        testShip4.setHasSailed(true);

        List<Ship> testShips = new ArrayList<>();
        testShips.add(testShip1);
        testShips.add(testShip2);
        testShips.add(testShip3);
        testShips.add(testShip4);

        testRound.setShips(testShips);

        Assert.assertTrue(roundService.goToNextRound(testRound));

        Game testGame2 = gameService.createGame("testGame2", "testOwner2");
        Round testRound2 = roundService.createRound(1L,testGame2);

        Ship testShip5 = new Ship();
        Ship testShip6 = new Ship();
        Ship testShip7 = new Ship();
        Ship testShip8 = new Ship();

        testShip5.setHasSailed(false);
        testShip6.setHasSailed(false);
        testShip7.setHasSailed(false);
        testShip8.setHasSailed(false);

        List<Ship> testShips2 = new ArrayList<>();
        testShips2.add(testShip5);
        testShips2.add(testShip6);
        testShips2.add(testShip7);
        testShips2.add(testShip8);

        testRound2.setShips(testShips2);

        Assert.assertEquals(roundService.goToNextRound(testRound2),false);
    }

    @Test
    public void listRounds() {
        Game testGame = gameService.createGame("testGame", "testOwner");
        testGame.setId(1L);
        Round testRound = roundService.createRound(1L,testGame);
        testRound.setId(1L);
        Assert.assertEquals(roundService.listRounds(1L).get(0),testRound);
    }

    @Test
    public void getRoundByNr() {
        Game testGame = gameService.createGame("testGame", "testOwner");
        testGame.setId(1L);
        Round testRound = roundService.createRound(1L,testGame);
        testRound.setId(1L);
        Assert.assertEquals(roundService.getRoundByNr(1L,1),testRound);
        try {
            roundService.getRoundByNr(1L,2);
        } catch (NotFoundException e) {}
    }

    @Test
    public void getShips() {
        Game testGame = gameService.createGame("testGame", "testOwner");
        testGame.setId(1L);
        Round testRound = roundService.createRound(1L,testGame);

        try {
            List<Ship> testShips = new ArrayList<>();
            testRound.setShips(testShips);
            roundService.getShips(1L,1);
        } catch (NotFoundException e) {}

        List<Ship> testShips2 = testRound.getShips();

        Ship testShip5 = new Ship();
        Ship testShip6 = new Ship();
        Ship testShip7 = new Ship();
        Ship testShip8 = new Ship();

        testShips2.add(testShip5);
        testShips2.add(testShip6);
        testShips2.add(testShip7);
        testShips2.add(testShip8);

        testRound.setShips(testShips2);
        Assert.assertNotNull(roundService.getShips(1L,1));
    }

    @Test
    public void getShip() {
        Game testGame = gameService.createGame("testGame", "testOwner");
        testGame.setId(1L);
        shipService.createShip(2,1,1L);
        Assert.assertNotNull(roundService.getShip(1L));
    }
}
