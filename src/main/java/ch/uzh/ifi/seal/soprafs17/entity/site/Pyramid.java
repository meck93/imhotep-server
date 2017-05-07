package ch.uzh.ifi.seal.soprafs17.entity.site;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = GameConstants.PYRAMID)
@DiscriminatorValue(value = GameConstants.PYRAMID)
public class Pyramid extends BuildingSite {

    // Scores for the Pyramid
    @JsonIgnore
    private static final int[] scores = {2, 1, 3, 2, 4, 3, 2, 1, 3, 2, 3, 1, 3, 4};

    public Pyramid() {
        // Purposely left blank because Hibernate needs an empty constructor
    }

    public Pyramid(Long gameId){
        super.setGameId(gameId);
        super.setSiteType(GameConstants.PYRAMID);
    }

    public int[] getScores() {
        return scores.clone();
    }
}
