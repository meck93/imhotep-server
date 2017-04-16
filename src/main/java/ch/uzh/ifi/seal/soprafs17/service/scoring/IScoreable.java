package ch.uzh.ifi.seal.soprafs17.service.scoring;


import ch.uzh.ifi.seal.soprafs17.entity.game.Game;

public interface IScoreable {

    public boolean supports(String siteType);

    public Game score(Game game);

    public Game scoreNow(Game game);

    public Game scoreEndOfRound(Game game);

    public Game scoreEndOfGame(Game game);

}
