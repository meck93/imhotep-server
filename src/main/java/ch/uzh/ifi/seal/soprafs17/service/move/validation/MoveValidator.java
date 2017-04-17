package ch.uzh.ifi.seal.soprafs17.service.move.validation;


import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;

public class MoveValidator implements IValidator {

    @Override
    public boolean supports(AMove move) {
        // This Validation shall be made by every Move disregarding of its type
        return (move != null);
    }

    @Override
    public void validate(final AMove move, final Game game) throws MoveValidationException {
        // The gameId associated with the Move must be the same as the Game's ID
        if ( ! move.getGameId().equals(game.getId())) {
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. GameId of Move != GameId of Game");
        }
        // The Round Nr associated to the Move must be the same as the current Round Nr in the Game
        if (move.getRoundNr() != game.getRoundCounter()) {
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. RoundNr of Move != RoundCounter of Game");
        }
        // The Move's Player Nr must be the same as the Nr of the current Player in the Game (Verifies who is allowed to make a move currently)
        if (move.getPlayerNr() != game.getCurrentPlayer()) {
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. PlayerNr of Move != CurrentPlayer of Game");
        }
    }
}
