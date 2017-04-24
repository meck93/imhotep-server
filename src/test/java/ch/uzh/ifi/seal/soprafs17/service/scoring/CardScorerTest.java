package ch.uzh.ifi.seal.soprafs17.service.scoring;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.user.PlayerService;
import ch.uzh.ifi.seal.soprafs17.service.user.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


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
    private PlayerService playerService;
    @Autowired
    private UserService userService;

    CardScorer cardScorer = new CardScorer();

  /*  @Test
    public void evaluateGreenCard(){
        Game game = gameService.createGame("testName", "testOwner");
        User user1 = userService.createUser("testName","testUser");
        Player player1 = playerService.createPlayer(1L,1L);
        user1.setPlayer(player1);
        List<Player> players = new ArrayList<>();
        players.add(player1);
        game.setPlayers(players);
        int[] points = {0,0,0,0};
        player1.setPoints(points);
        List<BuildingSite> buildingSites = new ArrayList<>();
        buildingSites.add(new Obelisk());
        buildingSites.add(new BurialChamber());
        buildingSites.add(new Temple());
        buildingSites.add(new Pyramid());
        game.setBuildingSites(buildingSites);
        for (int i = 0; i<4; i++){
            Stone s1 = new Stone();
            game.getBuildingSite(GameConstants.BURIAL_CHAMBER).getStones().add(s1);
            Stone s2 = new Stone();
            game.getBuildingSite(GameConstants.TEMPLE).getStones().add(s2);
            Stone s3 = new Stone();
            game.getBuildingSite(GameConstants.PYRAMID).getStones().add(s3);
            Stone s4 = new Stone();
            game.getBuildingSite(GameConstants.OBELISK).getStones().add(s4);
        }
        MarketCard marketCard1 = new MarketCard();
        MarketCard marketCard2 = new MarketCard();
        MarketCard marketCard3 = new MarketCard();
        MarketCard marketCard4 = new MarketCard();
        marketCard1.setMarketCardType(MarketCardType.TEMPLE_DECORATION);
        marketCard2.setMarketCardType(MarketCardType.BURIAL_CHAMBER_DECORATION);
        marketCard3.setMarketCardType(MarketCardType.OBELISK_DECORATION);
        marketCard4.setMarketCardType(MarketCardType.PYRAMID_DECORATION);
        cardScorer.scoreGreenCard(game,1,GameConstants.BURIAL_CHAMBER);
        Assert.assertEquals(player1.getPoints()[4],1);
        cardScorer.scoreGreenCard(game,1,GameConstants.TEMPLE);
        Assert.assertEquals(player1.getPoints()[4],2);
        cardScorer.scoreGreenCard(game,1,GameConstants.PYRAMID);
        Assert.assertEquals(player1.getPoints()[4],3);
        cardScorer.scoreGreenCard(game,1,GameConstants.OBELISK);
        Assert.assertEquals(player1.getPoints()[4],4);
    }*/

}