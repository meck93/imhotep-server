package ch.uzh.ifi.seal.soprafs17.entity.site;

import ch.uzh.ifi.seal.soprafs17.GameConstants;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = GameConstants.BURIAL_CHAMBER)
@DiscriminatorValue(value = GameConstants.BURIAL_CHAMBER)
public class BurialChamber extends BuildingSite {

    public BurialChamber(){
        // Purposely left blank because Hibernate needs an empty constructor
    }

    public BurialChamber(Long gameId) {
        super.setGameId(gameId);
        super.setSiteType(GameConstants.BURIAL_CHAMBER);
    }
}
