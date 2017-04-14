package ch.uzh.ifi.seal.soprafs17.entity.site;

import ch.uzh.ifi.seal.soprafs17.GameConstants;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = GameConstants.OBELISK)
@DiscriminatorValue(value = GameConstants.OBELISK)
public class Obelisk extends BuildingSite {

    public Obelisk(){
        // Purposely left blank because Hibernate needs an empty constructor
    }

    public Obelisk(Long gameId) {
        super.setGameId(gameId);
        super.setSiteType(GameConstants.OBELISK);
    }
}
