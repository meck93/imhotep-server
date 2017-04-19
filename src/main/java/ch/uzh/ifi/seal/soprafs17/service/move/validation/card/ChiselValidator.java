package ch.uzh.ifi.seal.soprafs17.service.move.validation.card;

import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlayCardMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;
import ch.uzh.ifi.seal.soprafs17.service.move.validation.IValidator;

public class ChiselValidator implements IValidator {

    @Override
    public boolean supports(AMove move) {
        return move.getMoveType().equals(MarketCardType.CHISEL.toString());
    }

    @Override
    public void validate(final AMove move, final Game game) throws MoveValidationException {

        // Typecasting to the correct Move Type
        PlayCardMove newMove = (PlayCardMove) move;

        // The SupplySled must hold at least two stones
        if (game.getPlayerByPlayerNr(game.getCurrentPlayer()).getSupplySled().getStones().size() < 2){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. " +
                    "SupplySled must hold at least two stones! - Move cannot be applied");
        }

        /* PART ONE: VALIDATE THE FIRST SHIP*/

        // The   first ship must exist in the round
        boolean ship1Exists = false;

        for (Ship ship : game.getRoundByRoundCounter().getShips()){
            if (ship.getId().equals(newMove.getShipId())){
                ship1Exists = true;
            }
        }

        if (!ship1Exists){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. " +
                    "Ship doesn't exist in Round: " + game.getRoundByRoundCounter());
        }

        // The first ship must not have sailed already
        if (game.getRoundByRoundCounter().getShipById(newMove.getShipId()).isHasSailed()){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType()
                    + " failed. Ship already sailed.");
        }
        // The requested placeOnShip mustn't be occupied on the first ship
        game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getStones().forEach(stone -> {
            if (stone.getPlaceOnShip() == newMove.getPlaceOnShip()){
                throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. " +
                        "The requested place on the ship is already occupied.");
            }
        });

        /* PART TWO: VALIDATE THE SECOND SHIP*/

        // Check if its the same ship
        if (newMove.getShipId() == newMove.getShipId2()) {
            // The requested placeOShip mustn't be occupied
            if (newMove.getPlaceOnShip() == newMove.getPlaceOnShip2()) {
                throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. " +
                        "The requested place on the ship is already occupied.");
            }
            // The ship must have at leas two free slots
            if (game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getStones().size() > game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getMAX_STONES() - 2) {
                throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. No space left on the ship.");
            }
        }
        else{
            // The first ship must exist in the round
            boolean ship2Exists = false;

            for (Ship ship : game.getRoundByRoundCounter().getShips()){
                if (ship.getId().equals(newMove.getShipId2())){
                    ship2Exists = true;
                }
            }

            if (!ship2Exists){
                throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. " +
                        "Ship doesn't exist in Round: " + game.getRoundByRoundCounter());
            }

            // The second ship must not have sailed already
            if (game.getRoundByRoundCounter().getShipById(newMove.getShipId2()).isHasSailed()){
                throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. Ship already sailed.");
            }
            // The requested placeOnShip mustn't be occupied on the second ship
            game.getRoundByRoundCounter().getShipById(newMove.getShipId2()).getStones().forEach(stone -> {
                if (stone.getPlaceOnShip() == newMove.getPlaceOnShip2()){
                    throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. " +
                            "The requested place on the ship is already occupied.");
                }
            });
        }
    }
}
