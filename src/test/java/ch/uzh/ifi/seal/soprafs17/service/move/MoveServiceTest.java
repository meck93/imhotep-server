package ch.uzh.ifi.seal.soprafs17.service.move;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.move.*;
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
    private GetStonesMove move1;
    private GetStonesMove move4;
    private PlaceStoneMove move2;
    private PlaceStoneMove move5;
    private SailShipMove move3;
    private GetCardMove move6;
    private PlaceStoneMove move7;

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

    @Before
    public void addEnvironment(){
        // TestMove1 - GET_STONES
        move1 = new GetStonesMove(GameConstants.GET_STONES);
        move1.setGameId(1L);
        move1.setRoundNr(1);
        move1.setPlayerNr(1);
        this.aMoveRepository.save(move1);

        // TestMove4 - GET_STONES
        move4 = new GetStonesMove(GameConstants.GET_STONES);
        move4.setGameId(1L);
        move4.setRoundNr(1);
        move4.setPlayerNr(2);
        this.aMoveRepository.save(move4);

        // TestMove2 - PLACE_STONE
        move2 = new PlaceStoneMove(GameConstants.PLACE_STONE);
        move2.setGameId(1L);
        move2.setRoundNr(1);
        move2.setPlayerNr(1);
        move2.setShipId(1L);
        move2.setPlaceOnShip(1);
        this.aMoveRepository.save(move2);

        // TestMove5 - PLACE_STONE
        move5 = new PlaceStoneMove(GameConstants.PLACE_STONE);
        move5.setGameId(1L);
        move5.setRoundNr(1);
        move5.setPlayerNr(2);
        move5.setShipId(1L);
        move5.setPlaceOnShip(2);
        this.aMoveRepository.save(move5);

        //TestMove7 - PLACE_STONE
        move7 = new PlaceStoneMove(GameConstants.PLACE_STONE);
        move7.setGameId(1L);
        move7.setRoundNr(1);
        move7.setPlayerNr(1);
        move7.setPlaceOnShip(3);
        move7.setShipId(1L);
        this.aMoveRepository.save(move7);

        // TestMove3 - SAIL_SHIP
        move3 = new SailShipMove(GameConstants.SAIL_SHIP);
        move3.setGameId(1L);
        move3.setRoundNr(1);
        move3.setPlayerNr(2);
        move3.setShipId(1L);
        move3.setTargetSiteId(1L);
        this.aMoveRepository.save(move3);

        // TestMove6 - GET_CARD
        move6 = new GetCardMove(GameConstants.GET_CARD);
        move6.setGameId(1L);
        move6.setRoundNr(1);
        move6.setPlayerNr(1);
        // Set the MarketCardId to a random from the Marketplace
        move6.setMarketCardId(game.getMarketPlace().getMarketCards().get(0).getId());
        this.aMoveRepository.save(move6);
    }

    @Test
    public void validateAndApplyGetStones(){

        this.moveService.validateAndApply(move1);

        // Assert that the Size of TheSupplySled is equal to the maxSize (5)
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getSupplySled().getStones().size(), GameConstants.MAX_STONES_SUPPLY_SLED);

        this.moveService.validateAndApply(move4);

        // Assert that the Size of TheSupplySled is equal to the maxSize (5)
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getSupplySled().getStones().size(), GameConstants.MAX_STONES_SUPPLY_SLED);
    }

    @Test
    public void validateAndApplyPlaceStones(){
        this.addEnvironment();

        //GetStones Move
        this.validateAndApplyGetStones();

        // PlaceStoneMove of Player1 on Ship 1 - Position 1
        this.moveService.validateAndApply(move2);

        //Assert that 1 Stone has been placed on Ship1
        Assert.assertEquals(game.getRoundByRoundCounter().getShipById(1L).getStones().size(), 1);

        // PlaceStoneMove of Player2 on Ship 1 - Position 2
        this.moveService.validateAndApply(move5);

        //Assert that 1 Stone has been placed on Ship1
        Assert.assertEquals(game.getRoundByRoundCounter().getShipById(1L).getStones().size(), 2);

        // PlaceStoneMove of Player1 on Ship 1 - Position 3
        this.moveService.validateAndApply(move7);

        //Assert that 1 Stone has been placed on Ship1
        Assert.assertEquals(game.getRoundByRoundCounter().getShipById(1L).getStones().size(), 3);
    }

    @Test
    public void validateAndApplySailShip(){
        //Place the Stones on the Ship
        this.validateAndApplyPlaceStones();

        //SailShip by Player 2 to MarketPlace (ID = 1)
        this.moveService.validateAndApply(move3);

        game = this.gameService.findById(game.getId());

        // Assert the ship has sailed
        Assert.assertEquals(game.getRoundByRoundCounter().getShipById(1L).isHasSailed(), true);
        // Assert the Site has been docked
        Assert.assertEquals(game.getMarketPlace().isDocked(), true);
        Assert.assertEquals(game.getMarketPlace().getDockedShipId(), move3.getShipId());
    }

    @Test
    // Validating a non-red GetCardMove
    public void validateAndApplyGetCard(){
        // Set Up of the Environment for the Test
        this.validateAndApplySailShip();

        // Changing the marketCard of the designated move to the required type for the test
        game.getMarketPlace().getMarketCardById(move6.getMarketCardId()).setColor(GameConstants.GREEN);
        game.getMarketPlace().getMarketCardById(move6.getMarketCardId()).setMarketCardType(MarketCardType.BURIAL_CHAMBER_DECORATION);

        this.moveService.validateAndApply(move6);

        // Assert the MarketCard ID of the HandCard and the move are the same
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getHandCards().get(0).getId(), move6.getMarketCardId());
    }

    /* VALIDATION OF THE RED MARKET CARDS */

    @Test
    // Validating the ENTRANCE MarketCard
    public void validateAndApplyEntrance(){
        // Set Up of the Environment for the Test
        this.validateAndApplySailShip();

        // Changing the marketCard of the designated move to the required type for the test
        game.getMarketPlace().getMarketCardById(move6.getMarketCardId()).setColor(GameConstants.RED);
        game.getMarketPlace().getMarketCardById(move6.getMarketCardId()).setMarketCardType(MarketCardType.ENTRANCE);

        this.moveService.validateAndApply(move6);

        Assert.assertEquals(game.getBuildingSite(GameConstants.PYRAMID).getStones().size(), 1);
    }

    @Test
    // Validating the PAVED_PATH MarketCard
    public void validateAndApplyPavedPath(){
        // Set Up of the Environment for the Test
        this.validateAndApplySailShip();

        // Changing the marketCard of the designated move to the required type for the test
        game.getMarketPlace().getMarketCardById(move6.getMarketCardId()).setColor(GameConstants.RED);
        game.getMarketPlace().getMarketCardById(move6.getMarketCardId()).setMarketCardType(MarketCardType.PAVED_PATH);

        this.moveService.validateAndApply(move6);

        Assert.assertEquals(game.getBuildingSite(GameConstants.OBELISK).getStones().size(), 1);
    }


    @Test
    // Validating the SARCOPHAGUS MarketCard
    public void validateAndApplySarcophagus(){
        // Set Up of the Environment for the Test
        this.validateAndApplySailShip();

        // Changing the marketCard of the designated move to the required type for the test
        game.getMarketPlace().getMarketCardById(move6.getMarketCardId()).setColor(GameConstants.RED);
        game.getMarketPlace().getMarketCardById(move6.getMarketCardId()).setMarketCardType(MarketCardType.SARCOPHAGUS);

        this.moveService.validateAndApply(move6);

        Assert.assertEquals(game.getBuildingSite(GameConstants.BURIAL_CHAMBER).getStones().size(), 1);
    }

    @Test
    public void logMove(){
        // Create additional environment
        this.addEnvironment();

        // Game and Move must exist
        Assert.assertNotNull(move);
        Assert.assertNotNull(game);

        // Making sure there is no userName assigned with the move yet
        Assert.assertNull(move.getUserName());

        // Logging the Move
        this.moveService.logMove(move, game);

        // Testing that the userName has been logged
        Assert.assertNotNull(move.getUserName());
    }

    @Test
    public void findLastMoves(){
        // Create additional environment
        this.addEnvironment();
        this.validateAndApplyGetCard();

        // Game must exist
        Assert.assertNotNull(game);

        Page<AMove> result = this.moveService.findLastMoves(game.getId(), 6);
        Assert.assertEquals(result.getNumberOfElements(), 6);

        for (AMove move : result){
            System.out.println("UserName: " + move.getUserName() + " MoveType: " + move.getMoveType() + " MoveID: " + move.getId());

            // Asserts which must hold for every move disregarding its type
            Assert.assertNotNull(move.getUserName());
            Assert.assertNotNull(move.getGameId());
            Assert.assertNotNull(move.getRoundNr());
            Assert.assertNotNull(move.getPlayerNr());

            // Assert that must hold for every SAIL_SHIP Move
            if (move.getMoveType().equals(GameConstants.SAIL_SHIP)) {
                // Assert that the TargetSiteType is added by the Logging Function
                Assert.assertNotNull(((SailShipMove) move).getTargetSiteType());
            }
            // Assert that must hold for every GET_CARD Move
            else if (move.getMoveType().equals(GameConstants.GET_CARD)) {
                // Assert getMarketCardType is added by the Logging Function
                Assert.assertNotNull(((GetCardMove) move).getMarketCardType());
            }
            // Assert that must hold for every LEVER & SAIL Move
            else if (move.getMoveType().equals(MarketCardType.LEVER.toString()) || move.getMoveType().equals(MarketCardType.SAIL.toString())) {
                // Assert that the TargetSiteType is added by the Logging Function
                Assert.assertNotNull(((PlayCardMove) move).getTargetSiteType());
                // Assert getMarketCardType is added by the Logging Function
                Assert.assertNotNull(((PlayCardMove) move).getMarketCardType());
            }
            // Assert that must hold for every HAMMER & CHISEL Move
            else if (move.getMoveType().equals(MarketCardType.HAMMER.toString()) || move.getMoveType().equals(MarketCardType.CHISEL.toString())) {
                    // Assert getMarketCardType is added by the Logging Function
                    Assert.assertNotNull(((PlayCardMove) move).getMarketCardType());
            }
        }
    }
}