package ch.uzh.ifi.seal.soprafs17.service.scoring;

import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;

import java.util.List;

import static ch.uzh.ifi.seal.soprafs17.GameConstants.*;


public class PyramidScorer implements IScoreable {

    @Override
    public boolean supports(Game game) {
        return true;
    }

    public Game score(Game game){
        return this.scoreNow(game);
    }

    @Override
    public Game scoreNow(Game game) {

        // Scores for the Pyramid
        int[] scores = {2, 1, 3, 2, 4, 3, 2, 1, 3, 2, 3, 1, 3, 4};

        // Retrieve the Stones from the Pyramid
        List<Stone> stones = game.getBuildingSite("PYRAMID").getStones();

        // Scores of the players
        // Black
        int scoreP1 = 0;
        // White
        int scoreP2 = 0;
        // Brown
        int scoreP3 = 0;
        // Gray
        int scoreP4 = 0;

        // Adding up the scores
        for (Stone stone : stones){
            if (stones.indexOf(stone) < 14){
                switch (stone.getColor()){
                    case BLACK: scoreP1 = scoreP1 + scores[stones.indexOf(stone)]; break;
                    case WHITE: scoreP2 = scoreP2 + scores[stones.indexOf(stone)]; break;
                    case BROWN: scoreP3 = scoreP3 + scores[stones.indexOf(stone)]; break;
                    case GRAY: scoreP4 = scoreP4 + scores[stones.indexOf(stone)]; break;
                }
            }
            else {
                switch (stone.getColor()){
                    case BLACK: scoreP1 = scoreP1 + 1; break;
                    case WHITE: scoreP2 = scoreP2 + 1; break;
                    case BROWN: scoreP3 = scoreP3 + 1; break;
                    case GRAY: scoreP4 = scoreP4 + 1; break;

                }
            }
        }

        int finalScoreP1 = scoreP1;
        int finalScoreP2 = scoreP2;
        int finalScoreP3 = scoreP3;
        int finalScoreP4 = scoreP4;

        // Adding the points to the player
        game.getPlayers().forEach(player -> {
            if (player.getPlayerNumber() == 1) {
                player.setPoints(finalScoreP1);
            }
            if (player.getPlayerNumber() == 2) {
                player.setPoints(finalScoreP2);
            }
            if (player.getPlayerNumber() == 3) {
                player.setPoints(finalScoreP3);
            }
            if (player.getPlayerNumber() == 4) {
                player.setPoints(finalScoreP4);
            }
        });

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