package ch.uzh.ifi.seal.soprafs17.service.move.validation;

import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;


public interface IValidator {

    public boolean supports(AMove move);

    public void validate(final AMove move, final Game game) throws MoveValidationException;
}
