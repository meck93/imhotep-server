package ch.uzh.ifi.seal.soprafs17.service.scoring;

import ch.uzh.ifi.seal.soprafs17.constant.BuildingSiteType;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;

import java.util.List;


public class PyramidScorer implements IRateable {

    @Override
    public boolean supports(BuildingSiteType buildingSiteType) {
        return (buildingSiteType.equals(BuildingSiteType.PYRAMID));
    }

    @Override
    public void score(Game game) {
        this.scoreNow(game);
        this.scoreEndOfRound(game);
        this.scoreEndOfGame(game);
    }

    @Override
    public void scoreNow(Game game) {
        // Retrieve the players
        List<Player> players = game.getPlayers();

        // Retrieve the Pyramid
        BuildingSite pyramid = game.getPyramid();

        // Retrieve the Stones to be scored
        List<Stone> stones = pyramid.getStones();

        // Scores of the players
        // Black
        int scoreP1 = 0;
        // White
        int scoreP2 = 0;
        // Brown
        int scoreP3 = 0;
        // Gray
        int scoreP4 = 0;
    }

    @Override
    public void scoreEndOfRound(Game game) {
        // Pyramid is not scored at the End of the Round
    }

    @Override
    public void scoreEndOfGame(Game game) {
        // Pyramid is not scored at the End of the Game
    }
}
