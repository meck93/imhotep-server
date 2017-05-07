package ch.uzh.ifi.seal.soprafs17.service.move.validation.card;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.GetStonesMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlaceStoneMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlayCardMove;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.repository.MarketCardRepository;
import ch.uzh.ifi.seal.soprafs17.repository.RoundRepository;
import ch.uzh.ifi.seal.soprafs17.repository.StoneQuarryRepository;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.move.validation.MoveValidator;
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
 * @see PlayCardValidator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class PlayCardValidatorTest {

    private PlayCardValidator playCardValidator;
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
    @Autowired
    private MarketCardRepository marketCardRepository;

    @Before
    public void create(){
        // Set Up for supports()
        move = new PlayCardMove();
        Assert.assertNotNull(move);
        move.setGameId(1L);
        move.setRoundNr(1);
        move.setPlayerNr(1);
        move.setMoveType(GameConstants.PLAY_CARD);

        pMove = (PlayCardMove) move;

        playCardValidator = new PlayCardValidator();
        Assert.assertNotNull(playCardValidator);

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

        this.gameRepository.save(game);

        // Setting up a dummy MarketCard for the Test
        MarketCard marketCard = new MarketCard();
        marketCard.setMarketCardType(MarketCardType.CHISEL);
        marketCard.setColor(GameConstants.BLUE);
        marketCard.setGameId(1L);

        this.marketCardRepository.save(marketCard);

        // Adding the card to the players handCards
        game.getPlayerByPlayerNr(game.getCurrentPlayer()).getHandCards().add(marketCard);

        this.gameRepository.save(game);

        // Set the Id of the DummyCard to the Move's CardId
        pMove.setCardId(marketCard.getId());
    }

    @Test
    public void supports() {
        // Check that the move is supported
        Assert.assertEquals(playCardValidator.supports(move), true);

        // Checking that the move is not supported
        move = new PlaceStoneMove();
        Assert.assertEquals(playCardValidator.supports(move), false);
    }

    @Test
    public void validateAllTrue(){
        // Satisfying all requirements
        Assert.assertNotNull(pMove);

        playCardValidator.validate(pMove, game);
    }

    @Test(expected = MoveValidationException.class)
    public void validateStatus(){
        // Dissatisfying the requirements: STATUS
        Assert.assertNotNull(pMove);
        game.setStatus(GameStatus.PENDING);

        // Throws the exception
        playCardValidator.validate(pMove, game);
    }

    @Test(expected = MoveValidationException.class)
    public void validatePlayerNumber(){
        // Dissatisfying the requirements: Game_ID
        Assert.assertNotNull(pMove);
        pMove.setPlayerNr(2);

        // Throws the exception
        playCardValidator.validate(pMove, game);
    }
}
