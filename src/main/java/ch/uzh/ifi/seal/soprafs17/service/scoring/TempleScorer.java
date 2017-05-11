package ch.uzh.ifi.seal.soprafs17.service.scoring;


import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.exceptions.InternalServerException;

import java.util.List;

import static ch.uzh.ifi.seal.soprafs17.GameConstants.*;

public class TempleScorer implements IScoreable {

    @Override
    public boolean supports(String siteType) {
        return siteType.equals(TEMPLE);
    }

    @Override
    public Game score(Game game) {
        return this.scoreEndOfRound(game);
    }

    @Override
    public Game scoreNow(Game game) {
        return null;
    }

    @Override
    public Game scoreEndOfRound(Game game) {

        // Stones to be scored from the Temple
        List<Stone> stones = game.getBuildingSite(GameConstants.TEMPLE).getStones();

        // With no Stones on the Temple it cannot be scored
        if (stones.isEmpty()){
            return game;
        }

        // Points of the Players - Array Size according to the amount of Players in the Game
        int[] points = new int[game.getNumberOfPlayers()];

        // Variables for adding up the Points
        // Upper Limit - Starting Point
        int size;

        // Lower Limit - which Stones to be included
        int limit;

        // With 2 Players only the first 4 places on the Temple will be scored
        if (game.getNumberOfPlayers() == 2){
            size = stones.size() - 1;
            limit = size - 3;
        }

        // With 3/4 Players all 5 places on the Temple will be scored
        else {
            size = stones.size() - 1;
            limit = size - 4;
        }

        // Ensuring that the last element is at least at position 0
        if (limit < 0) {
            limit = 0;
        }

        // Adding up the Points on the Temple (Size = StartPosition, Limit = EndPosition)
        for (int i = size; i >= limit; i--) {
            // Add the points to the correct player according to the color
            switch (stones.get(i).getColor()) {
                case BLACK: points[0] += 1; break;
                case WHITE: points[1] += 1; break;
                case BROWN: points[2] += 1; break;
                case GRAY: points[3] += 1; break;
                default: throw new InternalServerException("Wrong Color - Color doesn't exist");
            }
        }

        // Adding the points to the Players points[] at position 1: Temple Position
        for (int i = 0; i < game.getNumberOfPlayers(); i++){
            // Adding the points for each Player
            game.getPlayerByPlayerNr(i+1).getPoints()[1] += points[i];
        }
        return game;
    }

    @Override
    public Game scoreEndOfGame(Game game) {
        return null;
    }
}
