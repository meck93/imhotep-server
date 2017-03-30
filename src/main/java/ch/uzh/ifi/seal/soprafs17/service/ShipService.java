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
    private final Logger log = LoggerFactory.getLogger(UserService.class);
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
    public Ship[] createShips(RoundCard roundCard){

        ArrayList<ShipSize> ships = roundCard.getShipSizes();                   // copying the list of size enumerators from the current card
        Ship[] shipArray = new Ship[4];

        //create the ships
        for(int i = 0; i<3; i++){
            if(ships.get(i)== ShipSize.XL){
                shipArray[i]= new Ship(3,4);
                shipRepository.save(shipArray[i]);
            }
            if(ships.get(i)== ShipSize.L){
                shipArray[i]= new Ship(2,3);
                shipRepository.save(shipArray[i]);
            }
            if(ships.get(i)== ShipSize.M){
                shipArray[i]= new Ship(1,2);
                shipRepository.save(shipArray[i]);
            }
            if(ships.get(i)== ShipSize.S){
                shipArray[i]= new Ship(1,1);
                shipRepository.save(shipArray[i]);
            }
        }
        return shipArray;
    }
}
