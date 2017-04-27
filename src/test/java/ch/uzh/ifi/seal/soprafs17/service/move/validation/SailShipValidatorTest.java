package ch.uzh.ifi.seal.soprafs17.service.move.validation;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.GetStonesMove;
<<<<<<< HEAD
=======
import ch.uzh.ifi.seal.soprafs17.entity.move.PlaceStoneMove;
>>>>>>> refs/remotes/origin/development
import ch.uzh.ifi.seal.soprafs17.entity.move.SailShipMove;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.repository.RoundRepository;
import ch.uzh.ifi.seal.soprafs17.repository.StoneQuarryRepository;
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

/**
 * Test class for the GameResource REST resource.
 *
 * @see SailShipValidator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class SailShipValidatorTest {

    private SailShipValidator sailShipValidator;
    private AMove move;
    private SailShipMove sMove;
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

    @Before
    public void create(){
        // Set Up for supports()
        move = new SailShipMove();
        Assert.assertNotNull(move);
        move.setMoveType(GameConstants.SAIL_SHIP);
        move.setGameId(1L);
        move.setRoundNr(1);
        move.setPlayerNr(1);

        sMove = (SailShipMove) move;
        sMove.setShipId(1L);
        sMove.setTargetSiteId(2L);

        sailShipValidator = new SailShipValidator();
        Assert.assertNotNull(sailShipValidator);

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

        this.gameService.startGame(game.getId());

        game = this.gameService.findById(game.getId());
        game.setStoneQuarry(this.stoneQuarryRepository.findOne(1L));

        Assert.assertNotNull(game.getStoneQuarry());
        Assert.assertNotNull(game.getStoneQuarry().getBlackStones());
        Assert.assertNotNull(game.getStoneQuarry().getWhiteStones());

        Round round = this.roundRepository.findById(1L);
        for (int i = 1; i <= round.getShipById(1L).getMAX_STONES(); i++){
            round.getShipById(1L).getStones().add(new Stone());
            round.getShipById(1L).getStones().get(i - 1).setPlaceOnShip(i);
            round.getShipById(1L).getStones().get(i - 1).setColor(GameConstants.BLACK);
        }
        game.getRounds().add(round);

        this.gameRepository.save(game);
    }

    @Test
    public void supports() {
        // Check that the move is supported
        Assert.assertEquals(sailShipValidator.supports(move), true);

        // Checking that the move is not supported
        move = new GetStonesMove();
        Assert.assertEquals(sailShipValidator.supports(move), false);
    }

    @Test
    public void validateAllTrue(){
        // Satisfying all requirements
        Game testGame = this.gameService.findById(game.getId());
        sailShipValidator.validate(move, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validateStatus(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: STATUS
        Assert.assertNotNull(move);
        testGame.setStatus(GameStatus.PENDING);
        // Throws the exception
        sailShipValidator.validate(move, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validateMoveType(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: MOVE_TYPE
        move.setMoveType(GameConstants.GET_STONES);
        Assert.assertNotNull(move);
        // Throws the exception
        sailShipValidator.validate(move, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validatePlayerNumber(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Game_ID
        Assert.assertNotNull(move);
        move.setPlayerNr(2);
        // Throws the exception
        sailShipValidator.validate(move, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validateShipId(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Ship_ID
        Assert.assertNotNull(sMove);
        sMove.setShipId(5L);
        // Throws the exception
        sailShipValidator.validate(sMove, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validateShipHasSailed(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Ship_ID
        Assert.assertNotNull(sMove);

        // Set SupplySled empty
        game.getRoundByRoundCounter().getShipById(1L).setHasSailed(true);

        // Throws the exception
        sailShipValidator.validate(sMove, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validateSiteFree(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Ship_ID
        Assert.assertNotNull(sMove);
        // Changing the site with ID: 2 to occupied
        Assert.assertNotNull(game.getBuildingSite(GameConstants.OBELISK));
        game.getBuildingSite(GameConstants.OBELISK).setDocked(true);

        // Throws the exception
        sailShipValidator.validate(sMove, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validateShipHasEnoughStones(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Ship Has Enough Stones to be sailed
        Assert.assertNotNull(sMove);

        // Set SupplySled empty
        game.getRoundByRoundCounter().getShipById(1L).setStones(new ArrayList<>());

        // Throws the exception
        sailShipValidator.validate(sMove, testGame);
    }
}
