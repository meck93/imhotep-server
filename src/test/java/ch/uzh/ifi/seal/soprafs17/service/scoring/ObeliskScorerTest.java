package ch.uzh.ifi.seal.soprafs17.service.scoring;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
import ch.uzh.ifi.seal.soprafs17.exceptions.InternalServerException;
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
 * Test class for the ObeliskScorer REST resource.
 *
 * @see ObeliskScorer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class ObeliskScorerTest {

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

    private ObeliskScorer obeliskScorer;
    private Game game;

    @Before
    public void createEnvironment(){
        // Set Up for supports()
        obeliskScorer = new ObeliskScorer();

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

        this.gameRepository.save(game);
    }

    public void additionalEnvironment2Players(){
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

    public void additionalEnvironment3Players(){
        User user3 = this.userService.createUser("test3");
        Assert.assertNotNull(user3);

        Player player3 = this.playerService.createPlayer(game.getId(), user3.getId());
        Assert.assertNotNull(player3);
        Assert.assertEquals(player3, this.playerService.findPlayerById(player3.getId()));
        this.playerService.initializePlayer(game.getId(), player3);
        this.gameService.addPlayer(game.getId(), player3);
        this.gameService.updateNrOfPlayers(game.getId());

        this.gameService.startGame(game.getId());

        game = this.gameService.findById(game.getId());
        game.setStoneQuarry(this.stoneQuarryRepository.findOne(1L));

        Assert.assertNotNull(game.getStoneQuarry());
        Assert.assertNotNull(game.getStoneQuarry().getBlackStones());
        Assert.assertNotNull(game.getStoneQuarry().getWhiteStones());
        Assert.assertNotNull(game.getStoneQuarry().getBrownStones());

        Round round = this.roundRepository.findById(1L);

        game.getRounds().add(round);

        this.gameRepository.save(game);
    }

    public void additionalEnvironment4Players(){
        User user3 = this.userService.createUser("test3");
        Assert.assertNotNull(user3);
        User user4 = this.userService.createUser("test4");
        Assert.assertNotNull(user4);

        Player player3 = this.playerService.createPlayer(game.getId(), user3.getId());
        Assert.assertNotNull(player3);
        Assert.assertEquals(player3, this.playerService.findPlayerById(player3.getId()));
        this.playerService.initializePlayer(game.getId(), player3);
        this.gameService.addPlayer(game.getId(), player3);
        this.gameService.updateNrOfPlayers(game.getId());

        Player player4 = this.playerService.createPlayer(game.getId(), user4.getId());
        Assert.assertNotNull(player4);
        Assert.assertEquals(player4, this.playerService.findPlayerById(player4.getId()));
        this.playerService.initializePlayer(game.getId(), player4);
        this.gameService.addPlayer(game.getId(), player4);
        this.gameService.updateNrOfPlayers(game.getId());

        this.gameService.startGame(game.getId());

        game = this.gameService.findById(game.getId());
        game.setStoneQuarry(this.stoneQuarryRepository.findOne(1L));

        Assert.assertNotNull(game.getStoneQuarry());
        Assert.assertNotNull(game.getStoneQuarry().getBlackStones());
        Assert.assertNotNull(game.getStoneQuarry().getWhiteStones());
        Assert.assertNotNull(game.getStoneQuarry().getBrownStones());
        Assert.assertNotNull(game.getStoneQuarry().getGrayStones());

        Round round = this.roundRepository.findById(1L);

        game.getRounds().add(round);

        this.gameRepository.save(game);
    }

    @Test
    public void supports() {
        this.additionalEnvironment2Players();

        Assert.assertEquals(obeliskScorer.supports(game.getBuildingSite(GameConstants.OBELISK).getSiteType()),true);
        Assert.assertEquals(obeliskScorer.supports(game.getBuildingSite(GameConstants.BURIAL_CHAMBER).getSiteType()),false);
    }

    @Test(expected = InternalServerException.class)
    public void testWrongColorException(){
        this.additionalEnvironment2Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        Stone stone1 = new Stone();
        // Wrong Color for the Exception Test
        stone1.setColor("WRONG COLOR");
        stoneList.add(stone1);
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BLACK);
        stoneList.add(stone2);

        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        this.gameRepository.save(game);

        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 10);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 0);
    }

    @Test
    public void testTwoPlayersWhiteMore(){
        this.additionalEnvironment2Players();

        // Creating dummy Stones for the Obelisk
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
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.WHITE);
        stoneList.add(stone4);
        Stone stone5 = new Stone();
        stone5.setColor(GameConstants.WHITE);
        stoneList.add(stone5);

        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        this.gameRepository.save(game);

        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 1);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 10);
    }

    @Test
    public void testTwoPlayersBlackMore(){
        this.additionalEnvironment2Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BLACK);
        stoneList.add(stone2);
        Stone stone3 = new Stone();
        stone3.setColor(GameConstants.BLACK);
        stoneList.add(stone3);
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.WHITE);
        stoneList.add(stone4);

        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        this.gameRepository.save(game);

        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 10);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 1);
    }

    @Test
    public void testTwoPlayersSame(){
        this.additionalEnvironment2Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BLACK);
        stoneList.add(stone2);
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.WHITE);
        stoneList.add(stone4);

        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        this.gameRepository.save(game);

        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 5);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 5);
    }

    @Test
    public void testThreePlayerOnly2Score(){
        this.additionalEnvironment3Players();

        // Creating DummyStones for the Obelisk
        List<Stone> stoneList = game.getBuildingSite(GameConstants.OBELISK).getStones();

        // Creating 2 BROWN Dummy Stones
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);

        // Creating 3 WHITE Dummy Stones
        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.WHITE);
        stoneList.add(stone4);

        // Adding the Dummy Stones to the Obelisk
        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 0);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 12);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 6);
    }

    @Test
    public void testThreePlayerAllDifferent(){
        this.additionalEnvironment3Players();

        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Creating DummyStones for the Obelisk
        List<Stone> stoneList = game.getBuildingSite(GameConstants.OBELISK).getStones();

        // Creating 1 BLACK Dummy Stone
        Stone stone11 = new Stone();
        stone11.setColor(GameConstants.BLACK);
        stoneList.add(stone11);

        // Creating 2 BROWN Dummy Stones
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);
        Stone stone3 = new Stone();
        stone3.setColor(GameConstants.BROWN);
        stoneList.add(stone3);

        // Creating 3 WHITE Dummy Stones
        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.WHITE);
        stoneList.add(stone4);
        Stone stone5 = new Stone();
        stone5.setColor(GameConstants.WHITE);
        stoneList.add(stone5);

        // Adding the Dummy Stones to the Obelisk
        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 1);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 12);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 6);
    }

    @Test
    public void testThreePlayerTwoFirstOneLast(){
        this.additionalEnvironment3Players();

        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Creating DummyStones for the Obelisk
        List<Stone> stoneList = game.getBuildingSite(GameConstants.OBELISK).getStones();

        // Creating 1 BLACK Dummy Stone
        Stone stone11 = new Stone();
        stone11.setColor(GameConstants.BLACK);
        stoneList.add(stone11);

        // Creating 2 BROWN Dummy Stones
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);
        Stone stone3 = new Stone();
        stone3.setColor(GameConstants.BROWN);
        stoneList.add(stone3);

        // Creating 3 WHITE Dummy Stones
        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.WHITE);
        stoneList.add(stone4);

        // Adding the Dummy Stones to the Obelisk
        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 1);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 9);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 9);
    }

    @Test
    public void testThreePlayerOneFirstTwoLast(){
        this.additionalEnvironment3Players();

        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Creating DummyStones for the Obelisk
        List<Stone> stoneList = game.getBuildingSite(GameConstants.OBELISK).getStones();

        // Creating 1 BLACK Dummy Stone
        Stone stone11 = new Stone();
        stone11.setColor(GameConstants.BLACK);
        stoneList.add(stone11);

        // Creating 2 BROWN Dummy Stones
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);

        // Creating 3 WHITE Dummy Stones
        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.WHITE);
        stoneList.add(stone4);

        // Adding the Dummy Stones to the Obelisk
        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 3);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 12);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 3);
    }

    @Test
    public void testThreePlayerAllSameScore(){
        this.additionalEnvironment3Players();

        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        // Creating DummyStones for the Obelisk
        List<Stone> stoneList = game.getBuildingSite(GameConstants.OBELISK).getStones();

        // Creating 1 BLACK Dummy Stone
        Stone stone11 = new Stone();
        stone11.setColor(GameConstants.BLACK);
        stoneList.add(stone11);

        // Creating 2 BROWN Dummy Stones
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);

        // Creating 3 WHITE Dummy Stones
        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);

        // Adding the Dummy Stones to the Obelisk
        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 6);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 6);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 6);
    }

    @Test
    public void testFourPlayers2LastPlayersHaveSameScore(){
        this.additionalEnvironment4Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);
        Stone stone3 = new Stone();
        stone3.setColor(GameConstants.BROWN);
        stoneList.add(stone3);
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.BLACK);
        stoneList.add(stone4);
        Stone stone5 = new Stone();
        stone5.setColor(GameConstants.BLACK);
        stoneList.add(stone5);
        Stone stone6 = new Stone();
        stone6.setColor(GameConstants.BLACK);
        stoneList.add(stone6);
        Stone stone7 = new Stone();
        stone7.setColor(GameConstants.GRAY);
        stoneList.add(stone7);

        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        this.gameRepository.save(game);

        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 15);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 3);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 10);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(4).getPoints()[2], 3);
    }

    @Test
    public void testFourPlayersOnly3HaveScore(){
        this.additionalEnvironment4Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);
        Stone stone3 = new Stone();
        stone3.setColor(GameConstants.BROWN);
        stoneList.add(stone3);
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.BLACK);
        stoneList.add(stone4);
        Stone stone5 = new Stone();
        stone5.setColor(GameConstants.BLACK);
        stoneList.add(stone5);
        Stone stone6 = new Stone();
        stone6.setColor(GameConstants.BLACK);
        stoneList.add(stone6);

        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        this.gameRepository.save(game);


        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 15);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 5);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 10);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(4).getPoints()[2], 0);
    }

    @Test
    public void testFourPlayersNoSameScore(){
        this.additionalEnvironment4Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        // Creating 1 GRAY DummyStones
        Stone stone13 = new Stone();
        stone13.setColor(GameConstants.GRAY);
        stoneList.add(stone13);

        // Creating 2 WHITE DummyStones
        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        Stone stone12 = new Stone();
        stone12.setColor(GameConstants.WHITE);
        stoneList.add(stone12);

        // Creating 3 BROWN DummyStones
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);
        Stone stone3 = new Stone();
        stone3.setColor(GameConstants.BROWN);
        stoneList.add(stone3);
        Stone stone11 = new Stone();
        stone11.setColor(GameConstants.BROWN);
        stoneList.add(stone11);

        // Creating 4 BLACK DummyStones
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.BLACK);
        stoneList.add(stone4);
        Stone stone5 = new Stone();
        stone5.setColor(GameConstants.BLACK);
        stoneList.add(stone5);
        Stone stone6 = new Stone();
        stone6.setColor(GameConstants.BLACK);
        stoneList.add(stone6);
        Stone stone7 = new Stone();
        stone7.setColor(GameConstants.BLACK);
        stoneList.add(stone7);

        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        this.gameRepository.save(game);


        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 15);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 5);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 10);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(4).getPoints()[2], 1);
    }

    @Test
    public void testFourPlayersAllSameScore(){
        this.additionalEnvironment4Players();

        // Creating dummy Stones for the Obelisk
        List<Stone> stoneList = new ArrayList<>();

        Stone stone1 = new Stone();
        stone1.setColor(GameConstants.WHITE);
        stoneList.add(stone1);
        Stone stone2 = new Stone();
        stone2.setColor(GameConstants.BROWN);
        stoneList.add(stone2);
        Stone stone4 = new Stone();
        stone4.setColor(GameConstants.BLACK);
        stoneList.add(stone4);
        Stone stone7 = new Stone();
        stone7.setColor(GameConstants.GRAY);
        stoneList.add(stone7);

        game.getBuildingSite(GameConstants.OBELISK).setStones(stoneList);

        this.gameRepository.save(game);


        Assert.assertEquals(this.obeliskScorer.scoreNow(game), null);
        Assert.assertEquals(this.obeliskScorer.scoreEndOfRound(game), null);

        Game newGame = this.obeliskScorer.scoreEndOfGame(game);

        Assert.assertEquals(newGame.getPlayerByPlayerNr(1).getPoints()[2], 7);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(2).getPoints()[2], 7);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(3).getPoints()[2], 7);
        Assert.assertEquals(newGame.getPlayerByPlayerNr(4).getPoints()[2], 7);
    }
}
