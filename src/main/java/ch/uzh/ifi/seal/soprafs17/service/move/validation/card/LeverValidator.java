package ch.uzh.ifi.seal.soprafs17.service.move.validation.card;

import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlayCardMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;
import ch.uzh.ifi.seal.soprafs17.service.move.validation.IValidator;

public class LeverValidator implements IValidator{

    @Override
    public boolean supports(AMove move) {
        return move.getMoveType().equals(MarketCardType.LEVER.toString());
    }

    @Override
    public void validate(final AMove move, final Game game) throws MoveValidationException {

        // Casting the abstract Move to a PlayCardMove
        PlayCardMove newMove = (PlayCardMove) move;

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
        if (game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getStones().size() <
                game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getMIN_STONES()){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Not enough stones on ship.");
        }
        // The unloading order must have the same amount of values than the number of stones on the ship
        if (game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getStones().size() != newMove.getUnloadingOrder().size()){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Site is already docked.");
        }
    }
}
