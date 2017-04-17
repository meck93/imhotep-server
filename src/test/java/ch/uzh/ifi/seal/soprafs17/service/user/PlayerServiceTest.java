package ch.uzh.ifi.seal.soprafs17.service.user;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.repository.PlayerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


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
    private PlayerService playerService;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void createPlayer() {
        /*
        Waiting for player repository
        Player testPlayer = new Player();
        Game testGame = new Game();
        testGame.setId(1L);
        User testUser = new User();
        testUser.setPlayer(testPlayer);
        testPlayer.setUsername("testName");
        testPlayer.setId(1L);
        testPlayer.setGame(testGame);
        testPlayer.setPlayerNumber(1);
        List<Player> testPlayers = new ArrayList<>();
        testPlayers.add(testPlayer);
        testGame.setPlayers(testPlayers);
        testGame.setStatus(GameStatus.PENDING);
        Assert.assertEquals(testPlayer,playerRepository.findOne(1L));
        */
    }

    @Test
    public void initializePlayer() {
        // TODO: test playerService.initializePlayer()
    }

    @Test
    public void getPlayer() {
        // TODO: test playerService.getPlayer()
    }

    @Test
    public void getPlayers() {
        // TODO: test playerService.getPlayers()
    }
}
