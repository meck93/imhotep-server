package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.constant.ShipSize;
import ch.uzh.ifi.seal.soprafs17.entity.RoundCard;
import ch.uzh.ifi.seal.soprafs17.entity.Ship;
import ch.uzh.ifi.seal.soprafs17.repository.ShipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Cristian on 26.03.2017.
 */

@Service
@Transactional
public class ShipService {
    private final Logger log = LoggerFactory.getLogger(ShipService.class);
    private final ShipRepository shipRepository;

    @Autowired
    public ShipService(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    /**
     * Creates for ships of the corresponding size value from a roundCard
     * @param roundCard
     * @return
     */
    public List<Ship> createShips(RoundCard roundCard){
        log.debug("Creating all the ships described by the roundCardId: " + roundCard.getId());

        // copying the list of size enumerators from the current card
        List<ShipSize> shipSizes = roundCard.getShipSizes();
        // Retrive the gameId from the roundCard
        Long gameId = roundCard.getGameId();
        // Initialize return List
        ArrayList<Ship> ships = new ArrayList<>(4);

        // create the required ships according to the shipSizes of the roundCard
        for (ShipSize size : shipSizes){
            switch (size){
                case XL: ships.add(createShip(4, 3, gameId)); break;
                case L: ships.add(createShip(3, 2, gameId)); break;
                case M: ships.add(createShip(2, 1, gameId)); break;
                case S: ships.add(createShip(1, 1, gameId)); break;
            }
        }
        if (ships.size() == 4) {
            return ships;
        }
        else {
            log.error("Unable to add the ships of the roundCardId: " + roundCard.getId());
            return null;
        }

    }

    public Ship createShip(int maxSize, int minSize, Long gameId) {
        log.debug("Creating a ship for gameId: " + gameId);

        Ship ship = new Ship();
        ship.setMaxStone(maxSize);
        ship.setMinStone(minSize);
        ship.setGameId(gameId);
        ship.setStones(new ArrayList<>());

        shipRepository.save(ship);

        return ship;
    }
}