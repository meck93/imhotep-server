package ch.uzh.ifi.seal.soprafs17.service.scoring;


import ch.uzh.ifi.seal.soprafs17.constant.BuildingSiteType;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;

public interface IRateable {

    public boolean supports(BuildingSiteType buildingSiteType);

    public void score(Game game);

    public void scoreNow(Game game);

    public void scoreEndOfRound(Game game);

    public void scoreEndOfGame(Game game);

}
