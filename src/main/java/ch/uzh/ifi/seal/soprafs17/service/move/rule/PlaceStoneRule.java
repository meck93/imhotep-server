package ch.uzh.ifi.seal.soprafs17.service.move.rule;

import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlaceStoneMove;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.SupplySled;
import ch.uzh.ifi.seal.soprafs17.exceptions.ApplyMoveException;

import java.util.List;

public class PlaceStoneRule implements IRule {

    @Override
    public boolean supports(AMove move) {
        return move instanceof PlaceStoneMove;
    }

    /*
     * Apply the changes to the game in regards to the Place_Stone move
     */
    @Override
    public Game apply(AMove move, Game game) throws ApplyMoveException {

        // Typecasting the AbstractMove to a PlaceStoneMove
        PlaceStoneMove newMove = (PlaceStoneMove) move;

        // List of all current players
        List<Player> players = game.getPlayers();

        // Index of the Player to be removed
        int playerIndex = 0;
        // Setting the index to the correct element in the list
        for (Player player : players){
            if (player.getPlayerNumber() == move.getPlayerNr()){
                playerIndex = players.indexOf(player);
            }
        }
        // removing the current Player from the List of all Players
        Player player = players.remove(playerIndex);

        // Retrieving the SupplySled of the current Player
        SupplySled supplySled = player.getSupplySled();

        // removing one stone from players' sled
        List<Stone> updatedStones = supplySled.getStones();
        Stone stone = updatedStones.remove(0);
        supplySled.setStones(updatedStones);

        // List of all current ships
        List<Ship> ships = game.getRoundByRoundCounter(game.getRoundCounter()).getShips();

        // Find the assigned ship
        // TODO: Implement a new Getter in class Ship -> so you can directly access the ship, so you don't need the whole part below
        //Ship assignedShip = ships.getShipById()

        // Remove the current ship

        /*
        for (Ship ship : ships){
            if (ship.getId() == move.getShipId())   // doesnt work
        }
        */

        // Copy the list of stones from assigned ship
        /*
        List<Stones> updatedShipStones =  assignedShip.getStones()
         */

        // Add the stone to the list on the specified place
        /*
        updatedShipStones.add(index,move.getStone())
         */

        // Replace the old list of stones with the new list
        /*
        assignedShip.setStones(updatedShipStones)
         */

        // Adding the updated Player back to the Game
        players.add(player);
        game.setPlayers(players);

        // Adding the updated ship back to the game
        /*
        ships.add(assignedShip);
        game.getRoundByRoundCounter(game.getRoundCounter()).setShips(ships);
         */

        return game;
    }
}

