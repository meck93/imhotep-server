package ch.uzh.ifi.seal.soprafs17.service.move.validation;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.GetStonesMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlaceStoneMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.SailShipMove;
import ch.uzh.ifi.seal.soprafs17.entity.site.ASite;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.entity.site.MarketPlace;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;

/**
 * Created by User on 14.04.2017.
 */
public class SailShipValidation implements IValidator {



    @Override
    public boolean supports(AMove move) {
        return move instanceof SailShipMove;
    }

    @Override
    public void validate(final AMove move, final Game game) throws MoveValidationException {

        // Casting the abstract Move to a PlaceStoneMove
        SailShipMove newMove = (SailShipMove) move;

        // MoveType of the Move must be of Type: SAIL_SHIP
        if( ! newMove.getMoveType().equals(GameConstants.SAIL_SHIP)){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Wrong MoveType!");
        }
        // The ship must exist in the round
        if (game.getRoundByRoundCounter(game.getRoundCounter()).getShipById(newMove.getShipId()) == null){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Ship doesn't exist in Round: " + game.getRoundByRoundCounter(game.getRoundCounter()));
        }
        // A ship must hold the minimum amount of stones for its size
        if (game.getRoundByRoundCounter(game.getRoundCounter()).getShipById(newMove.getShipId()).getStones().size() < game.getRoundByRoundCounter(game.getRoundCounter()).getShipById(newMove.getShipId()).getMIN_STONES()){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Not enough stones on ship.");
        }
        // The ship must not have sailed already
        if (game.getRoundByRoundCounter(game.getRoundCounter()).getShipById(newMove.getShipId()).isHasSailed()){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Ship already sailed.");
        }
        // The site has to be free
        if (game.getSiteById(newMove.getTargetSiteId()).isDocked()){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Site is already docked.");
        }

    }
}
