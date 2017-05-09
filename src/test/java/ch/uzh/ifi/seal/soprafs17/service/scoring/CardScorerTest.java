package ch.uzh.ifi.seal.soprafs17.service.scoring;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
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
import java.util.List;

/**
 * Test class for the CardScorer REST resource.
 *
 * @see CardScorer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class CardScorerTest {

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

    private CardScorer cardScorer;
    private Game game;

    @Before
    public void createEnvironment(){
        // Set Up for supports()
        cardScorer = new CardScorer();
        Assert.assertNotNull(cardScorer);

        // Set Up for validate()
        game = this.gameService.createGame("Test", "test");
        Assert.assertNotNull(game);
        Assert.assertEquals(game, this.gameService.findById(game.getId()));

        User user1 = userService.createUser("test1");
        User user2 = userService.createUser("test2");
        Assert.assertNotNull(user1);
        Assert.assertNotNull(user2);

        /* SETUP OF PLAYER 1 */
        Player player1 = this.playerService.createPlayer(game.getId(), user1.getId());
        Assert.assertNotNull(player1);
        Assert.assertEquals(player1, this.playerService.findPlayerById(player1.getId()));
        this.playerService.initializePlayer(game.getId(), player1);

        // Creating a GREEN DummyMarketCard for the Player1
        MarketCard handCard11 = new MarketCard();
        handCard11.setColor(GameConstants.GREEN);
        handCard11.setMarketCardType(MarketCardType.BURIAL_CHAMBER_DECORATION);

        // Creating a GREEN DummyMarketCard for the Player1
        MarketCard handCard12 = new MarketCard();
        handCard12.setColor(GameConstants.GREEN);
        handCard12.setMarketCardType(MarketCardType.OBELISK_DECORATION);

        // Creating a GREEN DummyMarketCard for the Player1
        MarketCard handCard13 = new MarketCard();
        handCard13.setColor(GameConstants.BLUE);
        handCard13.setMarketCardType(MarketCardType.CHISEL);

        // Adding the DummyCards to Player1
        player1.getHandCards().add(handCard11);
        player1.getHandCards().add(handCard12);
        player1.getHandCards().add(handCard13);

        // Adding the Player 1 to the Game
        this.gameService.addPlayer(game.getId(), player1);
        this.gameService.updateNrOfPlayers(game.getId());

        /* SETUP OF PLAYER 2 */
        Player player2 = this.playerService.createPlayer(game.getId(), user2.getId());
        Assert.assertNotNull(player2);
        Assert.assertEquals(player2, this.playerService.findPlayerById(player2.getId()));
        this.playerService.initializePlayer(game.getId(), player2);

        // Creating a GREEN DummyMarketCard for the Player2
        MarketCard handCard21 = new MarketCard();
        handCard21.setColor(GameConstants.GREEN);
        handCard21.setMarketCardType(MarketCardType.TEMPLE_DECORATION);

        // Creating a GREEN DummyMarketCard for the Player2
        MarketCard handCard22 = new MarketCard();
        handCard22.setColor(GameConstants.GREEN);
        handCard22.setMarketCardType(MarketCardType.PYRAMID_DECORATION);

        // Adding the DummyCards to Player1
        player2.getHandCards().add(handCard21);
        player2.getHandCards().add(handCard22);

        // Adding the Player 1 to the Game
        this.gameService.addPlayer(game.getId(), player2);
        this.gameService.updateNrOfPlayers(game.getId());

        this.gameRepository.save(game);
    }

    public void additionalEnvironment2Players(){
        this.gameService.startGame(game.getId());

        game.setStoneQuarry(this.stoneQuarryRepository.findOne(1L));

        Assert.assertNotNull(game.getStoneQuarry());
        Assert.assertNotNull(game.getStoneQuarry().getBlackStones());
        Assert.assertNotNull(game.getStoneQuarry().getWhiteStones());

        Round round = this.roundRepository.findById(1L);

        game.getRounds().add(round);

        this.gameRepository.save(game);
    }

    public void setUp2Stones(String siteType){
        // Setting up the Pyramid
        List<Stone> stoneList = new ArrayList<>();

        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BLACK);
        stoneList.add(stone2);

        game.getBuildingSite(siteType).setStones(stoneList);

        this.gameRepository.save(game);
    }

    public void setUp3Stones(String siteType){
        // Setting up the Pyramid
        List<Stone> stoneList = new ArrayList<>();

        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BLACK);
        stoneList.add(stone2);
        Stone stone3 = new Stone();
        stone3.setColor(GameConstants.BLACK);
        stoneList.add(stone3);

        game.getBuildingSite(siteType).setStones(stoneList);

        this.gameRepository.save(game);
    }

    public void setUp7Stones(String siteType){
        // Setting up the Pyramid
        List<Stone> stoneList = new ArrayList<>();

        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BLACK);
        stoneList.add(stone2);
        Stone stone3 = new Stone();
        stone3.setColor(GameConstants.BLACK);
        stoneList.add(stone3);
        Stone stone11 = new Stone();
        stone11.setColor(GameConstants.WHITE);
        stoneList.add(stone11);
        Stone stone12 = new Stone();
        stone12.setColor(GameConstants.BLACK);
        stoneList.add(stone12);
        Stone stone13 = new Stone();
        stone13.setColor(GameConstants.BLACK);
        stoneList.add(stone13);
        Stone stone14 = new Stone();
        stone14.setColor(GameConstants.BLACK);
        stoneList.add(stone14);

        game.getBuildingSite(siteType).setStones(stoneList);

        this.gameRepository.save(game);
    }

    @Test
    public void scorePyramidDecoration2Stones(){
        this.additionalEnvironment2Players();
        this.setUp2Stones(GameConstants.PYRAMID);

        Assert.assertNotNull(game);
        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 1);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 0);
    }

    @Test
    public void scorePyramidDecoration3Stones(){
        this.additionalEnvironment2Players();
        this.setUp3Stones(GameConstants.PYRAMID);

        Assert.assertNotNull(game);

        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 1);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 1);
    }

    @Test
    public void scorePyramidDecoration7Stones(){
        this.additionalEnvironment2Players();
        this.setUp7Stones(GameConstants.PYRAMID);

        Assert.assertNotNull(game);
        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 1);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 2);
    }

    @Test
    public void scoreTempleDecoration2Stones(){
        this.additionalEnvironment2Players();
        this.setUp2Stones(GameConstants.TEMPLE);

        Assert.assertNotNull(game);
        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 1);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 0);
    }

    @Test
    public void scoreTempleDecoration3Stones(){
        this.additionalEnvironment2Players();
        this.setUp3Stones(GameConstants.TEMPLE);

        Assert.assertNotNull(game);

        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 1);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 1);
    }

    @Test
    public void scoreTempleDecoration7Stones(){
        this.additionalEnvironment2Players();
        this.setUp7Stones(GameConstants.TEMPLE);

        Assert.assertNotNull(game);
        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 1);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 2);
    }

    @Test
    public void scoreObeliskDecoration2Stones(){
        this.additionalEnvironment2Players();
        this.setUp2Stones(GameConstants.OBELISK);

        Assert.assertNotNull(game);
        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 1);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 0);
    }

    @Test
    public void scoreObeliskDecoration3Stones(){
        this.additionalEnvironment2Players();
        this.setUp3Stones(GameConstants.OBELISK);

        Assert.assertNotNull(game);

        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 2);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 0);
    }

    @Test
    public void scoreObeliskDecoration7Stones(){
        this.additionalEnvironment2Players();
        this.setUp7Stones(GameConstants.OBELISK);

        Assert.assertNotNull(game);
        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 3);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 0);
    }

    @Test
    public void scoreBurialDecoration2Stones(){
        this.additionalEnvironment2Players();
        this.setUp2Stones(GameConstants.BURIAL_CHAMBER);

        Assert.assertNotNull(game);
        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 1);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 0);
    }

    @Test
    public void scoreBurialDecoration3Stones(){
        this.additionalEnvironment2Players();
        this.setUp3Stones(GameConstants.BURIAL_CHAMBER);

        Assert.assertNotNull(game);

        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 2);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 0);
    }

    @Test
    public void scoreBurialDecoration7Stones(){
        this.additionalEnvironment2Players();
        this.setUp7Stones(GameConstants.BURIAL_CHAMBER);

        Assert.assertNotNull(game);
        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 3);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 0);
    }

    public void addStatue(int amount){

        Player player1 = this.playerService.findPlayerById(1L);

        for (int i = 0; i < amount; i++){
            // Creating a VIOLET DummyMarketCard for the Player1
            MarketCard handCard11 = new MarketCard();
            handCard11.setColor(GameConstants.VIOLET);
            handCard11.setMarketCardType(MarketCardType.STATUE);
            // Adding the dummyCard to the player
            player1.getHandCards().add(handCard11);
        }

        // Resetting the changed player
        game.getPlayers().remove(game.getPlayerByPlayerNr(1));
        game.getPlayers().add(player1);

        this.gameRepository.save(game);
    }

    @Test
    public void score6Statues(){
        this.additionalEnvironment2Players();
        this.addStatue(6);

        Assert.assertNotNull(game);
        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 18);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 0);
    }

    @Test
    public void score5Statues(){
        this.additionalEnvironment2Players();
        this.addStatue(5);

        Assert.assertNotNull(game);
        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 16);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 0);
    }

    @Test
    public void score4Statues(){
        this.additionalEnvironment2Players();
        this.addStatue(4);

        Assert.assertNotNull(game);
        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 11);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 0);
    }

    @Test
    public void score3Statues(){
        this.additionalEnvironment2Players();
        this.addStatue(3);

        Assert.assertNotNull(game);
        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 7);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 0);
    }

    @Test
    public void score2Statues(){
        this.additionalEnvironment2Players();
        this.addStatue(2);

        Assert.assertNotNull(game);
        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 4);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 0);
    }

    @Test
    public void score1Statues(){
        this.additionalEnvironment2Players();
        this.addStatue(1);

        Assert.assertNotNull(game);
        // Assert that no points have been scored for the cards
        Assert.assertEquals(game.getPlayerByPlayerNr(1).getPoints()[4], 0);
        Assert.assertEquals(game.getPlayerByPlayerNr(2).getPoints()[4], 0);

        Game newGame = this.cardScorer.scoreEndOfGame(game);

        // Assert that the points have been scored correctly
        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[4], 2);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[4], 0);
    }
}
