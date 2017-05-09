package ch.uzh.ifi.seal.soprafs17.service.user;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.SupplySled;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.repository.RoundRepository;
import ch.uzh.ifi.seal.soprafs17.repository.StoneQuarryRepository;
import ch.uzh.ifi.seal.soprafs17.repository.SupplySledRepository;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Test class for the SupplySledResource REST resource.
 *
 * @see SupplySledService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class SupplySledServiceTest {

    private Game game;

    @Autowired
    private GameService gameService;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private StoneQuarryRepository stoneQuarryRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    public SupplySledService supplySledService;
    @Autowired
    public SupplySledRepository supplySledRepository;

    @Before
    public void create(){
        // Set Up for validate()
        game = this.gameService.createGame("Test", "test");
        Assert.assertNotNull(game);
        Assert.assertEquals(game, this.gameService.findById(game.getId()));

        User user1 = this.userService.createUser("test", "test");
        Assert.assertNotNull(user1);
        User user2 = this.userService.createUser("test2", "test2");
        Assert.assertNotNull(user2);

        Player player1 = this.playerService.createPlayer(game.getId(), user1.getId());
        Assert.assertNotNull(player1);
        Assert.assertEquals(player1, this.playerService.findPlayerById(player1.getId()));
        this.playerService.initializePlayer(game.getId(), player1);
        this.gameService.addPlayer(game.getId(), player1);
        this.gameService.updateNrOfPlayers(game.getId());

        Player player2 = this.playerService.createPlayer(game.getId(), user2.getId());
        Assert.assertNotNull(player2);
        Assert.assertEquals(player2, this.playerService.findPlayerById(player2.getId()));
        this.playerService.initializePlayer(game.getId(), player2);
        this.gameService.addPlayer(game.getId(), player2);
        this.gameService.updateNrOfPlayers(game.getId());

        this.gameRepository.save(game);
    }

    public void addPlayerThree(){
        User user3 = this.userService.createUser("test3", "test3");
        Assert.assertNotNull(user3);

        Player player3 = this.playerService.createPlayer(game.getId(), user3.getId());
        Assert.assertNotNull(player3);
        Assert.assertEquals(player3, this.playerService.findPlayerById(player3.getId()));
        this.playerService.initializePlayer(game.getId(), player3);
        this.gameService.addPlayer(game.getId(), player3);
        this.gameService.updateNrOfPlayers(game.getId());
    }

    public void addPlayerFour(){
        User user4 = this.userService.createUser("test4", "test4");
        Assert.assertNotNull(user4);

        Player player4 = this.playerService.createPlayer(game.getId(), user4.getId());
        Assert.assertNotNull(player4);
        Assert.assertEquals(player4, this.playerService.findPlayerById(player4.getId()));
        this.playerService.initializePlayer(game.getId(), player4);
        this.gameService.addPlayer(game.getId(), player4);
        this.gameService.updateNrOfPlayers(game.getId());
    }

    public void startGame(){
        this.gameService.startGame(game.getId());

        game = this.gameService.findById(game.getId());
        game.setStoneQuarry(this.stoneQuarryRepository.findOne(1L));

        Assert.assertNotNull(game.getStoneQuarry());
        Assert.assertNotNull(game.getStoneQuarry().getBlackStones());
        Assert.assertNotNull(game.getStoneQuarry().getWhiteStones());

        Round round = this.roundRepository.findById(1L);
        game.getRounds().add(round);

        this.gameRepository.save(game);
    }

    @Test
    public void createSupplySled() {
        SupplySled testSupplySled = supplySledService.createSupplySled(3L);

        Assert.assertNotNull(testSupplySled);
        Assert.assertEquals(testSupplySled, supplySledRepository.findOne(3L));

        SupplySled supplySled2 = this.supplySledService.createSupplySled(3L);
        Assert.assertNotNull(supplySled2);
        Assert.assertEquals(testSupplySled.getId(), supplySled2.getId());
    }

    @Test
    public void getSupplySledByPlayerId() {
        SupplySled testSupplySled = supplySledService.createSupplySled(3L);

        Assert.assertNotNull(testSupplySled);
        Assert.assertEquals(supplySledService.getSupplySledByPlayerId(3L).getId(), testSupplySled.getId());
    }

    @Test(expected = NotFoundException.class)
    public void validateSupplySledByPlayerId(){
        // Trying to retrieve a SupplySled which doesn't exist
        this.supplySledService.getSupplySledByPlayerId(5L);
    }

    @Test
    public void fillSupplySleds2Player() {
        // Pre-Conditions
        Assert.assertNotNull(game);
        Assert.assertNotNull(game.getPlayers());

        // No Stones must be on the SupplySled
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getSupplySled().getStones().size(), 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getSupplySled().getStones().size(), 0);

        this.startGame();

        // Stones must be on the SupplySled
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getSupplySled().getStones().size(), 2);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getSupplySled().getStones().size(), 3);
    }

    @Test
    public void fillSupplySleds3Player() {
        this.addPlayerThree();

        // Pre-Conditions
        Assert.assertNotNull(game);
        Assert.assertNotNull(game.getPlayers());

        // No Stones must be on the SupplySled
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getSupplySled().getStones().size(), 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getSupplySled().getStones().size(), 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(3).getSupplySled().getStones().size(), 0);

        this.startGame();

        // Stones must be on the SupplySled
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getSupplySled().getStones().size(), 2);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getSupplySled().getStones().size(), 3);
        Assert.assertEquals(game.getPlayerByPlayerNr(3).getSupplySled().getStones().size(), 4);
    }

    @Test
    public void fillSupplySleds4Player(){
        this.addPlayerThree();
        this.addPlayerFour();

        // Pre-Conditions
        Assert.assertNotNull(game);
        Assert.assertNotNull(game.getPlayers());

        // No Stones must be on the SupplySled
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getSupplySled().getStones().size(), 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getSupplySled().getStones().size(), 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(3).getSupplySled().getStones().size(), 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(4).getSupplySled().getStones().size(), 0);

        this.startGame();

        // Stones must be on the SupplySled
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getSupplySled().getStones().size(), 2);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getSupplySled().getStones().size(), 3);
        Assert.assertEquals(game.getPlayerByPlayerNr(3).getSupplySled().getStones().size(), 4);
        Assert.assertEquals(game.getPlayerByPlayerNr(4).getSupplySled().getStones().size(), 5);
    }
}

