package ch.uzh.ifi.seal.soprafs17.entity.site;

import ch.uzh.ifi.seal.soprafs17.GameConstants;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = GameConstants.TEMPLE)
@DiscriminatorValue(value = GameConstants.TEMPLE)
public class Temple extends BuildingSite {

    public Temple() {
        // Purposely left blank because Hibernate needs an empty constructor
    }

    public Temple(Long gameId) {
        super.setGameId(gameId);
        super.setSiteType(GameConstants.TEMPLE);
    }
}
