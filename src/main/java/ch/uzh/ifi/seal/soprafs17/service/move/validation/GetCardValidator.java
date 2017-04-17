package ch.uzh.ifi.seal.soprafs17.service.move.validation;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.GetCardMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;

/**
 * Created by Cristian on 15.04.2017.
 */
public class GetCardValidator implements IValidator {

    @Override
    public boolean supports(AMove move) {
        return move instanceof GetCardMove;
    }

    @Override
    public void validate(final AMove move, final Game game) throws MoveValidationException {

        // Casting the abstract Move to a GetCardMove
        GetCardMove newMove = (GetCardMove) move;

        // The GameStatus has to be in the SUBROUND in order to take a card
        if (!game.getStatus().equals(GameStatus.SUBROUND)) {
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Wrong GameStatus!");
        }
        // MoveType of the Move must be of Type: GET_CARD
        if (!newMove.getMoveType().equals(GameConstants.GET_CARD)) {
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Wrong MoveType!");
        }
        // The card must exist in the round
        if (game.getMarketPlace().getMarketCardById(newMove.getMarketCardId())== null){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Card doesn't exist in Round: " + game.getRoundByRoundCounter(game.getRoundCounter()));
        }
    }
}
