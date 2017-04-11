package ch.uzh.ifi.seal.soprafs17.service.move.validation;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.GetStonesMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;

public class GetStonesValidator implements IValidator {

    @Override
    public boolean supports(AMove move) {
        return move instanceof GetStonesMove;
    }

    @Override
    public void validate(final AMove move, final Game game) throws MoveValidationException {
        // MoveType of the Move must be of Type: GET_STONES
        if( ! move.getMoveType().equals(GameConstants.GET_STONES)){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Wrong MoveType");
        }
        // Stones on the SupplySled must be less than the Maximum (5)
        if (game.getPlayerByPlayerNr(game.getCurrentPlayer()).getSupplySled().getStones().size() == GameConstants.MAX_STONES_SUPPLY_SLED){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. SupplySled is already full!");
        }
        // Stones taken from StoneQuarry must be less than the maximal allowed amount (30)
        if (game.getStoneQuarry().getStonesByPlayerNr(game.getCurrentPlayer()).size() == 0){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. The Player: " + game.getCurrentPlayer() + " has already used the maximal amount of stones allowed!");
        }
    }
}