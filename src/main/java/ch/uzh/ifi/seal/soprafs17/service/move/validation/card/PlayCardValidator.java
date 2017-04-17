package ch.uzh.ifi.seal.soprafs17.service.move.validation.card;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlayCardMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;
import ch.uzh.ifi.seal.soprafs17.service.move.validation.IValidator;

public class PlayCardValidator implements IValidator {

    @Override
    public boolean supports(AMove move) {
        return move instanceof PlayCardMove;
    }

    @Override
    public void validate(final AMove move, final Game game) throws MoveValidationException {
        // Typecasting to the correct Move Type
        PlayCardMove newMove = (PlayCardMove) move;

        // Game must be running to make this move
        if(game.getStatus() != GameStatus.RUNNING){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. GameStatus is not Running - Currently: " + game.getStatus());
        }
        // The Card must exist in the Player's deck of cards
        if (game.getPlayerByPlayerNr(game.getCurrentPlayer()).getMarketCardById(newMove.getCardId()) == null) {
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Player doesn't have a MarketCard with Id: " + newMove.getCardId());
        }
    }
}
