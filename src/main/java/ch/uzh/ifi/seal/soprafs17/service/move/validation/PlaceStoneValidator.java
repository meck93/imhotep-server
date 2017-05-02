package ch.uzh.ifi.seal.soprafs17.service.move.validation;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
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

        // Game must be running to make this move
        if(game.getStatus() != GameStatus.RUNNING){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. GameStatus is not Running - Currently: " + game.getStatus());
        }
        // MoveType of the Move must be of Type: PLACE_STONE
        if( ! newMove.getMoveType().equals(GameConstants.PLACE_STONE)){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Wrong MoveType!");
        }
        // The Move's Player Nr must be the same as the Nr of the current Player in the Game (Verifies who is allowed to make a move currently)
        if (move.getPlayerNr() != game.getCurrentPlayer()) {
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. PlayerNr of Move != CurrentPlayer of Game");
        }

        // The ship must exist in the round
        boolean shipExists = false;

        for (Ship ship : game.getRoundByRoundCounter().getShips()){
            if (ship.getId().equals(newMove.getShipId())){
                shipExists = true;
            }
        }

        if (!shipExists){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. " +
                    "Ship doesn't exist in Round: " + game.getRoundByRoundCounter());
        }

        // The players' SupplySled must hold at least one stone
        if (game.getPlayerByPlayerNr(game.getCurrentPlayer()).getSupplySled().getStones().size() < GameConstants.MIN_STONES_TO_PLACE_STONE) {
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Not enough stones in SupplySled.");
        }
        // The ship must not have sailed already
        if (game.getRoundByRoundCounter().getShipById(newMove.getShipId()).isHasSailed()){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Ship already sailed.");
        }
        // A ship must have at least one free space
        if(game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getStones().size() ==
                game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getMAX_STONES()){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. No space left on the ship.");
        }
        // The requested placeOnShip mustn't be occupied
        game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getStones().forEach(stone -> {
            if (stone.getPlaceOnShip() == newMove.getPlaceOnShip()){
                throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. The requested place on the ship is already occupied.");}});
        
        // The placeOnShip value must be smaller or equal to the MAX_STONES value && greater than 0
        if(newMove.getPlaceOnShip() > game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getMAX_STONES() || newMove.getPlaceOnShip() < 1){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. " +
                    "The place on the Ship cannot be larger than value: MAX_STONES");
        }
    }
}
