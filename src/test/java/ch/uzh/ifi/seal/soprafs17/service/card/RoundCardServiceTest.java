package ch.uzh.ifi.seal.soprafs17.service.card;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.RoundCardType;
import ch.uzh.ifi.seal.soprafs17.constant.ShipSize;
import ch.uzh.ifi.seal.soprafs17.entity.card.RoundCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.repository.RoundCardRepository;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Test class for the RoundCardResource REST resource.
 *
 * @see RoundCardService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class RoundCardServiceTest {

    @Autowired
    private RoundCardService roundCardService;

    @Autowired
    private RoundCardRepository roundCardRepository;

    @Autowired
    private GameService gameService;

    @Test
    public void createRoundCards() {
        Game game1 = gameService.createGame("test1","testOwner1");
        game1.setId(1L);
        Game game2 = gameService.createGame("test2","testOwner2");
        game2.setId(2L);
        Game game3 = gameService.createGame("test3","testOwner3");
        game3.setId(3L);
        roundCardService.createRoundCards(2,1L);
        Assert.assertEquals(roundCardRepository.findOne(1L).getHeads(),RoundCardType.TWO_HEADS);
        roundCardService.createRoundCards(3,2L);
        Assert.assertEquals(roundCardRepository.findAllRoundCards(2L).get(0).getHeads(),RoundCardType.THREE_HEADS);
        roundCardService.createRoundCards(4,3L);
        Assert.assertEquals(roundCardRepository.findOne(16L).getHeads(),RoundCardType.FOUR_HEADS);
    }

    @Test
    public void createTwoHeadRoundCardSet() {
        Game game = gameService.createGame("test","testOwner");
        game.setId(1L);
        Assert.assertNull(roundCardRepository.findOne(1L));
        roundCardService.createTwoHeadRoundCardSet(1L, RoundCardType.TWO_HEADS);
        Assert.assertNotNull(roundCardRepository.findAllRoundCards(1L));
    }

    @Test
    public void createFourThreeRoundCardSet() {
        Game game = gameService.createGame("test","testOwner");
        game.setId(1L);
        Assert.assertNull(roundCardRepository.findOne(1L));
        roundCardService.createThreeHeadRoundCardSet(1L, RoundCardType.TWO_HEADS);
        Assert.assertNotNull(roundCardRepository.findAllRoundCards(1L));
    }

    @Test
    public void createFourHeadRoundCardSet() {
        Game game = gameService.createGame("test","testOwner");
        game.setId(1L);
        Assert.assertNull(roundCardRepository.findOne(1L));
        roundCardService.createFourHeadRoundCardSet(1L, RoundCardType.TWO_HEADS);
        Assert.assertNotNull(roundCardRepository.findAllRoundCards(1L));
    }

    @Test
    public void createRoundCard() {
        Game game = gameService.createGame("test","testOwner");
        game.setId(1L);
        Assert.assertNull(roundCardRepository.findOne(1L));
        roundCardService.createRoundCard(1L, RoundCardType.TWO_HEADS, ShipSize.XL, ShipSize.L, ShipSize.M, ShipSize.M);
        Assert.assertNotNull(roundCardRepository.findOne(1L));
    }

    @Test
    public void getRoundCard() {
        Game game = gameService.createGame("test","testOwner");
        game.setId(1L);
        Assert.assertNull(roundCardRepository.findOne(1L));
        roundCardService.createRoundCard(1L, RoundCardType.TWO_HEADS, ShipSize.XL, ShipSize.XL, ShipSize.M, ShipSize.M);
        roundCardService.createRoundCard(1L, RoundCardType.TWO_HEADS, ShipSize.XL, ShipSize.XL, ShipSize.M, ShipSize.M);
        roundCardService.createRoundCard(1L, RoundCardType.TWO_HEADS, ShipSize.XL, ShipSize.XL, ShipSize.M, ShipSize.M);
        roundCardService.createRoundCard(1L, RoundCardType.TWO_HEADS, ShipSize.XL, ShipSize.XL, ShipSize.M, ShipSize.M);
        roundCardService.createRoundCard(1L, RoundCardType.TWO_HEADS, ShipSize.XL, ShipSize.XL, ShipSize.M, ShipSize.M);
        roundCardService.createRoundCard(1L, RoundCardType.TWO_HEADS, ShipSize.XL, ShipSize.XL, ShipSize.M, ShipSize.M);
        roundCardService.createRoundCard(1L, RoundCardType.TWO_HEADS, ShipSize.XL, ShipSize.XL, ShipSize.M, ShipSize.M);
        RoundCard testCard1 = roundCardService.getRoundCard(1L);
        Assert.assertNotNull(testCard1);
    }

    @Test
    public void deleteCard() {
        Game game = gameService.createGame("test","testOwner");
        game.setId(1L);
        roundCardService.createRoundCard(1L, RoundCardType.TWO_HEADS, ShipSize.XL, ShipSize.L, ShipSize.M, ShipSize.M);
        RoundCard testCard = roundCardRepository.findOne(1L);
        Assert.assertNotNull(roundCardRepository.findOne(1L));
        roundCardRepository.delete(testCard);
        Assert.assertNull(roundCardRepository.findOne(1L));
    }
}