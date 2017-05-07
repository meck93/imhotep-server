package ch.uzh.ifi.seal.soprafs17.service.move.validation.card;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlayCardMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;
import ch.uzh.ifi.seal.soprafs17.service.move.validation.IValidator;

public class SailValidator implements IValidator {


    @Override
    public boolean supports(AMove move) {
        return move.getMoveType().equals(MarketCardType.SAIL.toString());
    }

    @Override
    public void validate(final AMove move, final Game game) throws MoveValidationException {

        // Typecasting to the correct Move Type
        PlayCardMove newMove = (PlayCardMove) move;

        // The players' SupplySled must hold at least one stone
        if (game.getPlayerByPlayerNr(game.getCurrentPlayer()).getSupplySled().getStones().size() < GameConstants.MIN_STONES_TO_PLACE_STONE) {
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Not enough stones in SupplySled.");
        }

        // The site has to be free
        if (game.getSiteById(newMove.getTargetSiteId()).isDocked()){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Site is already docked.");
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

        // A ship must have at least one free space
        if(game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getStones().size() >=
                game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getMAX_STONES()){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. No space left on the ship.");
        }

        // The requested placeOnShip mustn't be occupied
        game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getStones().forEach(stone -> {
            if (stone.getPlaceOnShip() == newMove.getPlaceOnShip()){
                throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. " +
                        "The requested place on the ship is already occupied.");
            }
        });

        // The placeOnShip value must be smaller or equal to the MAX_STONES value && greater than 0
        if(newMove.getPlaceOnShip() > game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getMAX_STONES() || newMove.getPlaceOnShip() < 1){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. " +
                    "The place on the Ship cannot be larger than value: MAX_STONES");
        }

        // A ship must hold the minimum amount of stones for its size - 1
        if (game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getStones().size() <
                game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getMIN_STONES() - 1){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Not enough stones on ship.");
        }
    }
}
