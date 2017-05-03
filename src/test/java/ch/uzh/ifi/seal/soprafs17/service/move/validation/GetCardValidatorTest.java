package ch.uzh.ifi.seal.soprafs17.service.move.validation;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.GetCardMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.GetStonesMove;
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

/**
 * Test class for the GameResource REST resource.
 *
 * @see GetCardValidator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class GetCardValidatorTest {

    private GetCardValidator getCardValidator;
    private AMove move;
    private GetCardMove gMove;
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
        move = new GetCardMove();
        Assert.assertNotNull(move);
        move.setMoveType(GameConstants.GET_CARD);
        move.setGameId(1L);
        move.setRoundNr(1);
        move.setPlayerNr(1);

        gMove = (GetCardMove) move;

        getCardValidator = new GetCardValidator();
        Assert.assertNotNull(getCardValidator);

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
        game.getRounds().add(round);
        game.setCurrentSubRoundPlayer(1);
        game.setStatus(GameStatus.SUBROUND);

        // Setting the GetCardMove's marketCardId to an existing MarketCard's ID
        gMove.setMarketCardId(game.getMarketPlace().getMarketCards().get(0).getId());

        this.gameRepository.save(game);
    }

    @Test
    public void supports() {
        // Check that the move is supported
        Assert.assertEquals(getCardValidator.supports(move), true);

        // Checking that the move is not supported
        move = new GetStonesMove();
        Assert.assertEquals(getCardValidator.supports(move), false);
    }

    @Test
    public void validateAllTrue(){
        // Satisfying all requirements
        Game testGame = this.gameService.findById(game.getId());

        // Get the first marketCard on the MarketPlace
        gMove.setMarketCardId(testGame.getMarketPlace().getMarketCards().get(0).getId());
        getCardValidator.validate(gMove, testGame);

        // Get the first marketCard on the MarketPlace
        gMove.setMarketCardId(testGame.getMarketPlace().getMarketCards().get(1).getId());
        getCardValidator.validate(gMove, testGame);

        // Get the first marketCard on the MarketPlace
        gMove.setMarketCardId(testGame.getMarketPlace().getMarketCards().get(2).getId());
        getCardValidator.validate(gMove, testGame);

        // Get the first marketCard on the MarketPlace
        gMove.setMarketCardId(testGame.getMarketPlace().getMarketCards().get(3).getId());
        getCardValidator.validate(gMove, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validateStatus(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: STATUS
        Assert.assertNotNull(move);
        testGame.setStatus(GameStatus.PENDING);
        // Throws the exception
        getCardValidator.validate(move, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validateMoveType(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: MOVE_TYPE
        move.setMoveType(GameConstants.GET_STONES);
        Assert.assertNotNull(move);
        // Throws the exception
        getCardValidator.validate(move, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validatePlayerNumber(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Game_ID
        Assert.assertNotNull(move);
        move.setPlayerNr(2);
        // Throws the exception
        getCardValidator.validate(move, testGame);
    }

    @Test(expected = MoveValidationException.class)
    public void validateMarketCardExists(){
        // Finding the Game
        Game testGame = this.gameService.findById(game.getId());

        // Dissatisfying the requirements: Game_ID
        Assert.assertNotNull(move);
        gMove.setMarketCardId(100L);
        // Throws the exception
        getCardValidator.validate(gMove, testGame);
    }
}
