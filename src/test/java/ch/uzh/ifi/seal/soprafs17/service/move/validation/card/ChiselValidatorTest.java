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
 * @see ChiselValidator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class ChiselValidatorTest {

    private ChiselValidator chiselValidator;
    private PlayCardMove pMove;
    private AMove move;
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
        move.setMoveType(MarketCardType.CHISEL.toString());
        move.setGameId(1L);
        move.setRoundNr(1);
        move.setPlayerNr(1);

        pMove = (PlayCardMove) move;
        pMove.setShipId(1L);
        pMove.setShipId2(2L);
        pMove.setPlaceOnShip(1);
        pMove.setPlaceOnShip2(1);

        chiselValidator = new ChiselValidator();
        Assert.assertNotNull(chiselValidator);

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
        game.getRounds().add(round);

        this.gameRepository.save(game);
    }

    @Test
    public void supports() {
        // Check that the move is supported
        Assert.assertEquals(chiselValidator.supports(move), true);

        // Checking that the move is not supported
        move.setMoveType(MarketCardType.SAIL.toString());
        Assert.assertEquals(chiselValidator.supports(move), false);
    }

    @Test
    public void validateAllTrue(){
        // Satisfying all requirements
        Game testGame = this.gameService.findById(game.getId());
        chiselValidator.validate(move, testGame);
    }

    @Test
    public void validateSameShipId(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Ship_ID
        Assert.assertNotNull(pMove);
        pMove.setShipId2(1L);
        pMove.setPlaceOnShip2(2);

        // Throws the exception
        chiselValidator.validate(pMove, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void notEnoughStonesOnSupplySled(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: SupplySledStones
        Assert.assertNotNull(move);
        game.getPlayerByPlayerNr(game.getCurrentPlayer()).getSupplySled().setStones(new ArrayList<>());

        // Throws the exception
        chiselValidator.validate(move, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validateShipIdFirstShip(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Ship_ID
        Assert.assertNotNull(pMove);
        pMove.setShipId(5L);
        // Throws the exception
        chiselValidator.validate(pMove, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validateShipIdSecondShip(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Ship_ID
        Assert.assertNotNull(pMove);
        pMove.setShipId2(5L);
        // Throws the exception
        chiselValidator.validate(pMove, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validateShip1HasSailed(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Ship 1 has sailed
        Assert.assertNotNull(pMove);

        // Set Ship 1 to sailed
        game.getRoundByRoundCounter().getShipById(1L).setHasSailed(true);

        // Throws the exception
        chiselValidator.validate(pMove, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validateShip2HasSailed(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Ship 2 has sailed
        Assert.assertNotNull(pMove);

        // Set Ship 2 sailed
        game.getRoundByRoundCounter().getShipById(2L).setHasSailed(true);

        // Throws the exception
        chiselValidator.validate(pMove, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validatePlaceOnShip1Occupied(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Place On Ship 1
        Assert.assertNotNull(pMove);

        // Set SupplySled empty
        game.getRoundByRoundCounter().getShipById(1L).getStones().add(new Stone());
        game.getRoundByRoundCounter().getShipById(1L).getStones().get(0).setPlaceOnShip(1);

        // Throws the exception
        chiselValidator.validate(pMove, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validatePlaceOnShip2Occupied(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Place On Ship 2
        Assert.assertNotNull(pMove);

        // Set SupplySled empty
        game.getRoundByRoundCounter().getShipById(2L).getStones().add(new Stone());
        game.getRoundByRoundCounter().getShipById(2L).getStones().get(0).setPlaceOnShip(1);

        // Throws the exception
        chiselValidator.validate(pMove, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validateSamePlaceOnSameShip(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Different Place on the Ship - false
        Assert.assertNotNull(pMove);
        pMove.setShipId2(1L);

        // Throws the exception
        chiselValidator.validate(pMove, testGame);

        // Dissatisfying the requirements: Different Place on the Ship - false
        Assert.assertNotNull(pMove);
        pMove.setShipId2(1L);

        // Throws the exception
        chiselValidator.validate(pMove, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validateSameShipNotEnoughSpace() {
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Different Place on the Ship - false
        Assert.assertNotNull(pMove);
        pMove.setShipId2(1L);
        pMove.setPlaceOnShip2(2);

        // Filling all Places on the Ship - apart from 1
        for (int i = 0; i < game.getRoundByRoundCounter().getShipById(1L).getMAX_STONES() - 1; i++) {
            Stone stone = new Stone();
            stone.setColor(GameConstants.BLACK);
            stone.setPlaceOnShip(i + 1);
            game.getRoundByRoundCounter().getShipById(1L).getStones().add(stone);
        }

        // Throws the exception
        chiselValidator.validate(pMove, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validatePlaceOnShipValid(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Ship_ID - negative value
        Assert.assertNotNull(pMove);
        pMove.setPlaceOnShip(-2);

        // Throws the exception
        chiselValidator.validate(pMove, testGame);

        // Dissatisfying the requirements: Ship_ID - too large value
        Assert.assertNotNull(pMove);
        pMove.setPlaceOnShip(5);

        // Throws the exception
        chiselValidator.validate(pMove, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validatePlaceOnShip2Valid(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Ship_ID - negative value
        Assert.assertNotNull(pMove);
        pMove.setPlaceOnShip2(-2);

        // Throws the exception
        chiselValidator.validate(pMove, testGame);

        // Dissatisfying the requirements: Ship_ID - too large value
        Assert.assertNotNull(pMove);
        pMove.setPlaceOnShip2(5);

        // Throws the exception
        chiselValidator.validate(pMove, testGame);
    }
}
