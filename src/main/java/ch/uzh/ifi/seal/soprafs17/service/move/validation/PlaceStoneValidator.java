package ch.uzh.ifi.seal.soprafs17.service.move.validation;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlaceStoneMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;

public class PlaceStoneValidator implements IValidator {

    @Override
    public boolean supports(AMove move) {
        return move instanceof PlaceStoneMove;
    }

    @Override
    public void validate(final AMove move, final Game game) throws MoveValidationException {

        // Casting the abstract Move to a PlaceStoneMove
        PlaceStoneMove newMove = (PlaceStoneMove) move;

        // MoveType of the Move must be of Type: PLACE_STONE
        if( ! newMove.getMoveType().equals(GameConstants.PLACE_STONE)){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Wrong MoveType");
        }
        // The gameId associated with the Move must be the same as the Game's ID
        if ( ! newMove.getGameId().equals(game.getId())) {
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. GameId of Move <> GameId of Game");
        }
        // The Round Nr associated to the Move must be the same as the current Round Nr in the Game
        if (newMove.getRoundNr() != game.getRoundCounter()) {
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. RoundNr of Move <> RoundCounter of Game");
        }
        // The Move's Player Nr must be the same as the Nr of the current Player in the Game (Verifies who is allowed to make a move currently)
        if (newMove.getPlayerNr() != game.getCurrentPlayer()) {
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. PlayerNr of Move <> CurrentPlayer of Game");
        }
        // The ship must exist in the round
        if (game.getRoundByRoundCounter(game.getRoundCounter()).getShipById(newMove.getShipId()) == null){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Ship doesn't exist in Round: " + game.getRoundByRoundCounter(game.getRoundCounter()));
        }
        // The players' supplysled must hold at least one stone
        if (game.getPlayerByPlayerNr(game.getCurrentPlayer()).getSupplySled().getStones().size() < GameConstants.MIN_STONES_TO_PLACE_STONE) {
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Not enough stones in supplysled");
        }
        // The ship must not have sailed already
        if (game.getRoundByRoundCounter(game.getRoundCounter()).getShipById(newMove.getShipId()).isHasSailed()){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Ship already sailed");
        }
        // A ship must have at least one free space
       if(game.getRoundByRoundCounter(game.getRoundCounter()).getShipById(newMove.getShipId()).getMAX_STONES() == game.getRoundByRoundCounter(game.getRoundCounter()).getShipById(newMove.getShipId()).getStones().size()){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. No space left on the ship");
        }
    }
}
