package ch.uzh.ifi.seal.soprafs17.service.move.rule;


import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.ApplyMoveException;

public interface IRule {

    public boolean supports(AMove move);

    public Game apply(AMove move, Game game) throws ApplyMoveException;
}
