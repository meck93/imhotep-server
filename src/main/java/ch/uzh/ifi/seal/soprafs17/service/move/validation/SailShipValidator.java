package ch.uzh.ifi.seal.soprafs17.service.move.validation;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.SailShipMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;

public class SailShipValidator implements IValidator {

    @Override
    public boolean supports(AMove move) {
        return move instanceof SailShipMove;
    }

    @Override
    public void validate(final AMove move, final Game game) throws MoveValidationException {

        // Casting the abstract Move to a PlaceStoneMove
        SailShipMove newMove = (SailShipMove) move;

        // Game must be running to make this move
        if(game.getStatus() != GameStatus.RUNNING){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. GameStatus is not Running - Currently: " + game.getStatus());
        }
        // MoveType of the Move must be of Type: SAIL_SHIP
        if( ! newMove.getMoveType().equals(GameConstants.SAIL_SHIP)){
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

        // The ship must not have sailed already
        if (game.getRoundByRoundCounter().getShipById(newMove.getShipId()).isHasSailed()){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Ship already sailed.");
        }
        // The site has to be free
        if (game.getSiteById(newMove.getTargetSiteId()).isDocked()){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Site is already docked.");
        }
        // A ship must hold the minimum amount of stones for its size
        if (game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getStones().size() < game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getMIN_STONES()){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Not enough stones on ship.");
        }
    }
}
