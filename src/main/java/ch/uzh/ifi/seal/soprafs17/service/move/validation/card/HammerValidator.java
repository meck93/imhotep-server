package ch.uzh.ifi.seal.soprafs17.service.move.validation.card;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlayCardMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;
import ch.uzh.ifi.seal.soprafs17.service.move.validation.IValidator;

public class HammerValidator implements IValidator {

    @Override
    public boolean supports(AMove move) {
        return move.getMoveType().equals(MarketCardType.HAMMER.toString());
    }

    @Override
    public void validate(final AMove move, final Game game) throws MoveValidationException {
        // Typecasting to the correct Move Type
        PlayCardMove newMove = (PlayCardMove) move;

        // The SupplySled cannot be full
        if (game.getPlayerByPlayerNr(game.getCurrentPlayer()).getSupplySled().getStones().size() == GameConstants.MAX_STONES_SUPPLY_SLED){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. SupplySled is already full! - Move cannot be applied");
        }
        // Stones taken from StoneQuarry must be less than the maximal allowed amount (30)
        if (game.getStoneQuarry().getStonesByPlayerNr(game.getCurrentPlayer()).size() == 0){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. The Player: " + game.getCurrentPlayer() + " has already used the maximal amount of stones allowed!");
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
        // The requested placeOnShip mustn't be occupied
        game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getStones().forEach(stone -> {
            if (stone.getPlaceOnShip() == newMove.getPlaceOnShip()){
                throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. The requested place on the ship is already occupied.");
            }
        });
        // A ship must have at least one free space
        if(game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getStones().size() == game.getRoundByRoundCounter().getShipById(newMove.getShipId()).getMAX_STONES()){
            throw new MoveValidationException("Validation for Move: " + move.getMoveType() + " failed. No space left on the ship.");
        }
    }
}
