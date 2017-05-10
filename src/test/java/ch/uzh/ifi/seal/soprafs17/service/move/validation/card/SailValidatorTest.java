package ch.uzh.ifi.seal.soprafs17.service.move.validation.card;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlayCardMove;
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
 * @see SailValidator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class SailValidatorTest {

    private SailValidator sailValidator;
    private AMove move;
    private PlayCardMove pMove;
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
        move = new PlayCardMove();
        Assert.assertNotNull(move);
        move.setMoveType(MarketCardType.SAIL.toString());
        move.setGameId(1L);
        move.setRoundNr(1);
        move.setPlayerNr(1);

        pMove = (PlayCardMove) move;
        pMove.setPlaceOnShip(1);
        pMove.setShipId(1L);
        pMove.setTargetSiteId(1L);

        sailValidator = new SailValidator();
        Assert.assertNotNull(sailValidator);

        // Set Up for validate()
        game = this.gameService.createGame("Test", "test");
        Assert.assertNotNull(game);
        Assert.assertEquals(game, this.gameService.findById(game.getId()));

        User user1 = userService.createUser("test1");
        User user2 = userService.createUser("test2");
        Assert.assertNotNull(user1);
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

        for (int i = round.getShipById(1L).getMAX_STONES(); i > 1; i--){
            Stone stone = new Stone();
            stone.setColor(GameConstants.BLACK);
            stone.setPlaceOnShip(i);

            round.getShipById(1L).getStones().add(stone);
        }
        this.roundRepository.save(round);

        game.getRounds().add(round);

        this.gameRepository.save(game);
    }

    @Test
    public void supports() {
        // Check that the move is supported
        Assert.assertEquals(sailValidator.supports(pMove), true);

        // Checking that the move is not supported
        pMove.setMoveType(GameConstants.PLACE_STONE);
        Assert.assertEquals(sailValidator.supports(pMove), false);
    }

    @Test
    public void validateAllTrue(){
        // Satisfying all requirements
        Game testGame = this.gameService.findById(game.getId());
        sailValidator.validate(pMove, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validateShipId(){
        // Dissatisfying the requirements: Ship_ID
        Assert.assertNotNull(pMove);
        pMove.setShipId(5L);

        // Throws the exception
        sailValidator.validate(pMove, game);
    }

    @Test(expected = MoveValidationException.class)
    public void validateShipHasSailed(){
        // Dissatisfying the requirements: Ship_ID
        Assert.assertNotNull(pMove);

        // Set Ship 1 to sailed
        game.getRoundByRoundCounter().getShipById(1L).setHasSailed(true);

        // Throws the exception
        sailValidator.validate(pMove, game);
    }

    @Test(expected = MoveValidationException.class)
    public void validateSiteFree(){
        // Dissatisfying the requirements: Ship_ID
        Assert.assertNotNull(pMove);
        pMove.setTargetSiteId(2L);

        // Changing the site with ID: 2 to occupied
        Assert.assertNotNull(game.getBuildingSite(GameConstants.OBELISK));
        game.getBuildingSite(GameConstants.OBELISK).setDocked(true);

        // Throws the exception
        sailValidator.validate(pMove, game);
    }

    @Test(expected = MoveValidationException.class)
    public void validateSupplySled(){
        // Dissatisfying the requirements: Ship_ID
        Assert.assertNotNull(pMove);

        // Set SupplySled empty
        game.getPlayerByPlayerNr(1).getSupplySled().setStones(new ArrayList<>());

        // Throws the exception
        sailValidator.validate(pMove, game);
    }

    @Test(expected = MoveValidationException.class)
    public void validateShipHasFreeSpace(){
        // Dissatisfying the requirements: Ship_ID
        Assert.assertNotNull(pMove);
        pMove.setShipId(1L);
        pMove.setPlaceOnShip(1);

        // Filling the Stones to the Ship
        for (int i = 1; i <= game.getRoundByRoundCounter().getShipById(1L).getMAX_STONES(); i++){
            game.getRoundByRoundCounter().getShipById(1L).getStones().add(new Stone());
        }

        // Throws the exception
        sailValidator.validate(pMove, game);
    }

    @Test(expected = MoveValidationException.class)
    public void validatePlaceOnShipValid(){
        // Dissatisfying the requirements: Ship_ID - negative value
        Assert.assertNotNull(pMove);
        pMove.setPlaceOnShip(-2);

        // Throws the exception
        sailValidator.validate(pMove, game);

        // Dissatisfying the requirements: Ship_ID - too large value
        Assert.assertNotNull(pMove);
        pMove.setPlaceOnShip(5);

        // Throws the exception
        sailValidator.validate(pMove, game);
    }

    @Test(expected = MoveValidationException.class)
    public void validatePlaceOnShipOccupied(){
        // Dissatisfying the requirements: Ship_ID
        Assert.assertNotNull(pMove);

        // Set SupplySled empty
        game.getRoundByRoundCounter().getShipById(1L).getStones().add(new Stone());
        game.getRoundByRoundCounter().getShipById(1L).getStones().get(0).setPlaceOnShip(1);

        // Throws the exception
        sailValidator.validate(pMove, game);
    }
}
