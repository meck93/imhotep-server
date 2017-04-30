package ch.uzh.ifi.seal.soprafs17.service.move;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.GetStonesMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlaceStoneMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.SailShipMove;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
import ch.uzh.ifi.seal.soprafs17.repository.AMoveRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test class for the GameResource REST resource.
 *
 * @see MoveService
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class MoveServiceTest {

    private AMove move;
    private Game game;

    @Autowired
    private MoveService moveService;
    @Autowired
    private AMoveRepository aMoveRepository;
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
    public void createEnvironment(){
        // Set Up for supports()
        move = new GetStonesMove();
        Assert.assertNotNull(move);
        move.setMoveType(GameConstants.GET_STONES);
        move.setGameId(1L);
        move.setRoundNr(1);
        move.setPlayerNr(1);

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

        // TestMove1 - GET_STONES
        AMove move1 = new GetStonesMove(GameConstants.GET_STONES);
        move1.setGameId(1L);
        move1.setRoundNr(1);
        move1.setPlayerNr(1);
        this.aMoveRepository.save(move1);

        // TestMove2 - PLACE_STONE
        AMove move2 = new PlaceStoneMove(GameConstants.PLACE_STONE);
        move2.setGameId(1L);
        move2.setRoundNr(1);
        move2.setPlayerNr(2);
        this.aMoveRepository.save(move2);

        // TestMove3 - SAIL_SHIP
        AMove move3 = new SailShipMove(GameConstants.SAIL_SHIP);
        move3.setGameId(1L);
        move3.setRoundNr(1);
        move3.setPlayerNr(1);
        this.aMoveRepository.save(move3);

        // TestMove4 - GET_STONES
        AMove move4 = new GetStonesMove(GameConstants.GET_STONES);
        move4.setGameId(1L);
        move4.setRoundNr(1);
        move4.setPlayerNr(2);
        this.aMoveRepository.save(move4);

        // TestMove5 - PLACE_STONE
        AMove move5 = new PlaceStoneMove(GameConstants.PLACE_STONE);
        move5.setGameId(1L);
        move5.setRoundNr(1);
        move5.setPlayerNr(1);
        this.aMoveRepository.save(move5);

        // TestMove6 - PLACE_STONE
        AMove move6 = new PlaceStoneMove(GameConstants.GET_CARD);
        move6.setGameId(1L);
        move6.setRoundNr(1);
        move6.setPlayerNr(1);
        this.aMoveRepository.save(move6);
    }

    @Test
    public void logMove(){
        // Game and Move must exist
        Assert.assertNotNull(move);
        Assert.assertNotNull(game);

        // Making sure there is no description yet
        Assert.assertNull(move.getDescription());

        // Logging the Move
        this.moveService.logMove(move, game);

        // Testing that the Description has been created
        Assert.assertNotNull(move.getDescription());
    }

    @Test
    public void findLastMoves(){
        // Game and Move must exist
        Assert.assertNotNull(move);
        Assert.assertNotNull(game);

        Page<AMove> result = this.moveService.findLastMoves(game.getId(), 5);
        Assert.assertEquals(result.getNumberOfElements(), 5);

        for (AMove move : result){
            System.out.println(move.getDescription());
        }
    }

}