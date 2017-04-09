package ch.uzh.ifi.seal.soprafs17.service.move.validation;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlaceStoneMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;

/**
 * Created by User on 09.04.2017.
 */
public class PlaceStoneValidator implements IValidator {

    @Override
    public boolean supports(AMove move) {
        return move instanceof PlaceStoneMove;
    }

    @Override
    public void validate(final AMove move, final Game game) throws MoveValidationException {

        // MoveType of the Move must be of Type: PLACE_STONE
        if( ! move.getMoveType().equals(GameConstants.PLACE_STONE)){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Wrong MoveType");
        }
        // The gameId associated with the Move must be the same as the Game's ID
        if ( ! move.getGameId().equals(game.getId())) {
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. GameId of Move <> GameId of Game");
        }
        // The Round Nr associated to the Move must be the same as the current Round Nr in the Game
        if (move.getRoundNr() != game.getRoundCounter()) {
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. RoundNr of Move <> RoundCounter of Game");
        }
        // The Move's Player Nr must be the same as the Nr of the current Player in the Game (Verifies who is allowed to make a move currently)
        if (move.getPlayerNr() != game.getCurrentPlayer()) {
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. PlayerNr of Move <> CurrentPlayer of Game");
        }
        // The players' supplysled must hold at least one stone
        /*
        Function Body
        throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Not enough stones in supplysled");
         */

        // A ship must not be sailed
        /*
        Function Body
        throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Ship already sailed");
         */

        // A ship must have at least one free space
        /*
        Function Body
        throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. No space left on the ship");
         */

        // Check if the place assigned by the player is free
        /*
        Function Body
        throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Place of the ship is already in use");
         */

    }
}
