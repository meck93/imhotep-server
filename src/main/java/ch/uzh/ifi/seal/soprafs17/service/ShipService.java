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
    public ArrayList<Ship> createShips(RoundCard roundCard){

        // copying the list of size enumerators from the current card
        ArrayList<ShipSize> shipSizes = roundCard.getShipSizes();
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
        return ships;
    }

    public Ship createShip(int maxSize, int minSize, Long gameId) {
        Ship ship = new Ship();
        ship.setMaxStone(maxSize);
        ship.setMinStone(minSize);
        ship.setGameId(gameId);
        ship.setStones(new ArrayList<>());

        shipRepository.save(ship);

        return ship;
    }
}