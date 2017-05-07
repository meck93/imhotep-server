package ch.uzh.ifi.seal.soprafs17.entity.site;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.exceptions.InternalServerException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = GameConstants.OBELISK)
@DiscriminatorValue(value = GameConstants.OBELISK)
public class Obelisk extends BuildingSite {

    // Scores for the Obelisk - 2 Player
    @JsonIgnore
    private static final int[] scoresTwoPlayer = {10, 1};

    // Scores for the Obelisk - 3 Player
    @JsonIgnore
    private static final int[] scoresThreePlayer = {12, 6, 1};

    // Scores for the Obelisk - 4 Player
    @JsonIgnore
    private static final int[] scoresFourPlayer = {15, 10, 5, 1};

    public Obelisk(){
        // Purposely left blank because Hibernate needs an empty constructor
    }

    public Obelisk(Long gameId) {
        super.setGameId(gameId);
        super.setSiteType(GameConstants.OBELISK);
    }

    public int[] getScoresTwoPlayer() {
        return scoresTwoPlayer.clone();
    }

    public int[] getScoresThreePlayer() {
        return scoresThreePlayer.clone();
    }

    public int[] getScoresFourPlayer() {
        return scoresFourPlayer.clone();
    }

    public int[] getScores(int numberOfPlayers){
        switch (numberOfPlayers){
            case 2: return scoresTwoPlayer.clone();
            case 3: return scoresThreePlayer.clone();
            case 4: return scoresFourPlayer.clone();
            default: throw new InternalServerException("Wrong Nr of Players - Not Possible to have: " + numberOfPlayers + " Players");
        }
    }
}
