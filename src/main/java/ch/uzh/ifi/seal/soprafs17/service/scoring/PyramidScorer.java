package ch.uzh.ifi.seal.soprafs17.service.scoring;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.site.Pyramid;
import ch.uzh.ifi.seal.soprafs17.exceptions.InternalServerException;

import java.util.List;

import static ch.uzh.ifi.seal.soprafs17.GameConstants.*;


public class PyramidScorer implements IScoreable {

    @Override
    public boolean supports(String siteType) {
        return (siteType.equals(PYRAMID));
    }

    public Game score(Game game){
        return this.scoreNow(game);
    }

    @Override
    public Game scoreNow(Game game) {

        // Scores for the Pyramid
        Pyramid pyramid = (Pyramid) game.getBuildingSite(GameConstants.PYRAMID);
        int scores[] = pyramid.getScores();

        // Retrieve the Stones from the Pyramid
        List<Stone> stones = game.getBuildingSite(GameConstants.PYRAMID).getStones();

        // Scores of the players
        // BLACK = Player1, WHITE = Player2, BROWN = Player3, GRAY = Player4
        int points[] = new int[game.getNumberOfPlayers()];

        // Adding up the scores
        for (Stone stone : stones){
            // If the stones is one of the first 14 stones then assign a fixed value from the scores array
            if (stones.indexOf(stone) < 14){
                // Add the points to the correct player according to the color
                switch (stone.getColor()){
                    case BLACK: points[0] += scores[stones.indexOf(stone)]; break;
                    case WHITE: points[1] += scores[stones.indexOf(stone)]; break;
                    case BROWN: points[2] += scores[stones.indexOf(stone)]; break;
                    case GRAY:  points[3] += scores[stones.indexOf(stone)]; break;
                }
            }
            // Just add 1 if all 14 places on the pyramid are already occupied
            else {
                // Add the points to the correct player according to the color
                switch (stone.getColor()){
                    case BLACK: points[0] += 1; break;
                    case WHITE: points[1] += 1; break;
                    case BROWN: points[2] += 1; break;
                    case GRAY: points[3] += 1; break;
                    default: throw new InternalServerException("Wrong Color - Color doesn't exist");
                }
            }
        }

        // Adding the points to the Players points[] at position 0: Pyramid Position
        for (int i = 0; i < game.getNumberOfPlayers(); i++){
            game.getPlayerByPlayerNr(i+1).getPoints()[0] = points[i];
        }

        return game;
    }

    @Override
    public Game scoreEndOfRound(Game game) {
        // Pyramid is not scored at the End of the Round
        return null;
    }

    @Override
    public Game scoreEndOfGame(Game game) {
        // Pyramid is not scored at the End of the Game
        return null;
    }
}