package ch.uzh.ifi.seal.soprafs17.entity.site;

import ch.uzh.ifi.seal.soprafs17.GameConstants;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = GameConstants.PYRAMID)
@DiscriminatorValue(value = GameConstants.PYRAMID)
public class Pyramid extends BuildingSite {

    public Pyramid() {
        // Purposely left blank because Hibernate needs an empty constructor
    }

    public Pyramid(Long gameId){
        super.setGameId(gameId);
        super.setSiteType(GameConstants.PYRAMID);
    }


}
