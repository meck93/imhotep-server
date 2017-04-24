package ch.uzh.ifi.seal.soprafs17.service.game;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.ShipSize;
import ch.uzh.ifi.seal.soprafs17.entity.card.RoundCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.repository.ShipRepository;
import org.junit.Assert;
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
 * Test class for the ShipResource REST resource.
 *
 * @see ShipService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class ShipServiceTest {

    @Autowired
    private ShipService shipService;

    @Autowired
    private ShipRepository shipRepository;

    @Test
    public void createShips() {
        Game testGame = new Game();
        testGame.setId(1L);
        RoundCard testCard = new RoundCard();
        List<ShipSize> testShipSizes = new ArrayList<>();
        testShipSizes.add(ShipSize.XL);
        testShipSizes.add(ShipSize.L);
        testShipSizes.add(ShipSize.M);
        testShipSizes.add(ShipSize.S);
        testCard.setShipSizes(testShipSizes);
        List<Ship> testShips = shipService.createShips(testCard);
        Assert.assertNotNull(testShips);
    }

    @Test
    public void createShip() {
        Game testGame = new Game();
        testGame.setId(1L);
        shipService.createShip(4,2, 1L);
        shipService.createShip(3,2, 1L);
        shipService.createShip(2,1, 1L);
        shipService.createShip(1,1, 1L);
        Assert.assertNotNull(shipRepository.findOne(1L));
        Assert.assertNotNull(shipRepository.findOne(2L));
        Assert.assertNotNull(shipRepository.findOne(3L));
        Assert.assertNotNull(shipRepository.findOne(4L));
    }

    @Test
    public void findShip() {
        Game testGame = new Game();
        testGame.setId(1L);
        shipService.createShip(4,2, 1L);
        Assert.assertNotNull(shipService.findShip(1L));

    }
}