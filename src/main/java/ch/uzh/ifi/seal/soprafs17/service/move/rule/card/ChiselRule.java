package ch.uzh.ifi.seal.soprafs17.service.move.rule.card;

import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlayCardMove;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.SupplySled;
import ch.uzh.ifi.seal.soprafs17.exceptions.ApplyMoveException;
import ch.uzh.ifi.seal.soprafs17.service.move.rule.IRule;

import java.util.List;

/**
 * Created by User on 19.04.2017.
 */
public class ChiselRule implements IRule {
    @Override
    public boolean supports(AMove move) {
        return move.getMoveType().equals(MarketCardType.CHISEL.toString());
    }

    @Override
    public Game apply(AMove move, Game game) throws ApplyMoveException {

        // Typecasting the AbstractMove to a PlaceStoneMove
        PlayCardMove newMove = (PlayCardMove) move;

        // Retrieving the Player
        Player player = game.getPlayerByPlayerNr(game.getCurrentPlayer());

        // Removing the marketCard from the players deck of cards
        player.getHandCards().remove(player.getMarketCardById(newMove.getCardId()));

        // removing the current Player from the List of all Players
        game.getPlayers().remove(player);

        // Retrieving the SupplySled of the current Player
        SupplySled supplySled = player.getSupplySled();

        /* PART ONE: PLACING THE FIRST STONE ON THE FIRST SHIP*/

        // removing one stone from players' sled
        List<Stone> updatedStones = supplySled.getStones();
        Stone stone = updatedStones.remove(0);

        // Adding the position to selected stone
        stone.setPlaceOnShip(newMove.getPlaceOnShip());

        // List of all current ships
        List<Ship> ships = game.getRoundByRoundCounter().getShips();

        // Find the assigned ship
        Ship assignedShip = game.getRoundByRoundCounter().getShipById(newMove.getShipId());

        // Remove the assigned ship
        ships.remove(assignedShip);

        // Adding stone on ship
        assignedShip.getStones().add(stone);

        // Adding the updated ship back to the game
        game.getRoundByRoundCounter().getShips().add(assignedShip);

        /* PART TWO: PLACING THE SECOND STONE ON THE SECOND SHIP*/

        // removing one stone from players' sled
        Stone stone2 = updatedStones.remove(0);

        // Adding the position to selected stone
        stone2.setPlaceOnShip(newMove.getPlaceOnShip2());

        // Find the second assigned ship
        Ship assignedShip2 = game.getRoundByRoundCounter().getShipById(newMove.getShipId2());

        // Remove the assigned ship
        ships.remove(assignedShip2);

        // Adding stone on ship
        assignedShip2.getStones().add(stone2);

        // Adding the updated ship back to the game
        game.getRoundByRoundCounter().getShips().add(assignedShip2);

        // Adding the updated sled to the game
        supplySled.setStones(updatedStones);

        // Adding the updated SupplySled to the Player
        player.setSupplySled(supplySled);

        // Adding the updated Player back to the Game
        game.getPlayers().add(player);

        return game;
    }
}
