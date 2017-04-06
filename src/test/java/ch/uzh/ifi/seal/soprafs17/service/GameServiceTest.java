package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Test class for the GameResource REST resource.
 *
 * @see GameService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class GameServiceTest {

    @Autowired
    GameService gameService;

    @Autowired
    GameRepository gameRepository;

    @Test
    public void createGame() {
        Assert.assertNull(gameRepository.findById(1L));
        Game testGame = gameService.createGame("testName","testName");
        testGame.setId((1L));
        Assert.assertNotNull(gameRepository.findById(1L));
        Assert.assertEquals(gameRepository.findById(1L),testGame);
    }

    @Test
    public void deleteGame() {
        Assert.assertNull(gameRepository.findById(1L));
        Game testGame = gameService.createGame("testName","testName");
        testGame.setId(1L);
        Assert.assertNotNull(gameRepository.findById(1L));
        gameService.deleteGame(1L);
        Assert.assertNull(gameRepository.findById(1L));
    }

    @Test
    public void setNrOfPlayers() {
        Game testGame = gameService.createGame("testName","testName");
        testGame.setId((1L));
        gameService.setNrOfPlayers(1L,4);
        gameRepository.findNrOfPlayers(1L);
        Assert.assertNotNull(gameRepository.findNrOfPlayers(1L));
    }
}
