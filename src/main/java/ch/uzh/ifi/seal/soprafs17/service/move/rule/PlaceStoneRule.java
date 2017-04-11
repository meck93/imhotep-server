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


        // Adding the position to selected stone
        stone.setPlaceOnShip(newMove.getPlaceOnShip());

        // List of all current ships
        List<Ship> ships = game.getRoundByRoundCounter(game.getRoundCounter()).getShips();

        // Find the assigned ship
        Ship assignedShip = game.getRoundByRoundCounter(game.getRoundCounter()).getShipById(newMove.getShipId());

        // Remove the assigned ship
        ships.remove(assignedShip);

        // Adding stone on ship
        assignedShip.getStones().add(stone);

        // Adding the updated Player back to the Game
        players.add(player);
        game.setPlayers(players);

        // Adding the updated sled to the game
        supplySled.setStones(updatedStones);

        // Adding the updated ship back to the game
        ships.add(assignedShip);
        game.getRoundByRoundCounter(game.getRoundCounter()).setShips(ships);
        /*
        ships.add(assignedShip);
        game.getRoundByRoundCounter(game.getRoundCounter()).setShips(ships);
         */

        return game;
    }
}


