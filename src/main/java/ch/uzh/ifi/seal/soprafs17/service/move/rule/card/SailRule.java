package ch.uzh.ifi.seal.soprafs17.service.move.rule.card;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlayCardMove;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.entity.site.MarketPlace;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.exceptions.ApplyMoveException;
import ch.uzh.ifi.seal.soprafs17.service.move.rule.IRule;

import java.util.List;

public class SailRule implements IRule{

    @Override
    public boolean supports(AMove move) {
        return move.getMoveType().equals(MarketCardType.SAIL.toString());
    }

    @Override
    public Game apply(AMove move, Game game) throws ApplyMoveException {

        // Typecasting the AbstractMove to a PlaceStoneMove
        PlayCardMove newMove = (PlayCardMove) move;

        // Retrieving the Player
        Player player = game.getPlayerByPlayerNr(game.getCurrentPlayer());
        game.getPlayers().remove(player);

        // Removing the marketCard from the players deck of cards
        player.getHandCards().remove(player.getMarketCardById(newMove.getCardId()));

        /* PART ONE: PLACING STONE ON SHIP*/

        // removing one stone from players' SupplySled
        Stone stone = player.getSupplySled().getStones().remove(0);

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

        // Adding the updated Player back to the Game
        game.getPlayers().add(player);

        /*PART TWO: SAILING SHIP TO A TARGET SITE*/

        // Updating the ship
        assignedShip.setHasSailed(true);

        //Updating the status of the game
        if(game.getSiteById(newMove.getTargetSiteId()).getClass().equals(MarketPlace.class)){
            // Setting the Status to SUBROUND
            game.setStatus(GameStatus.SUBROUND);
            // Docking the Ship with correct ID to the MarketPlace
            game.getMarketPlace().setDockedShipId(newMove.getShipId());
            game.getMarketPlace().setDocked(true);
        }
        else {
            // Retrieving the correct BuildingSite
            BuildingSite buildingSite = (BuildingSite) game.getSiteById(newMove.getTargetSiteId());
            game.getBuildingSites().remove(buildingSite);

            // Setting the site docked
            buildingSite.setDocked(true);

            // Unloading the Stones from the Ship onto the BuildingSite
            for (int i = 1; i <= assignedShip.getMAX_STONES(); i++){
                // Retrieving the correct Stone in order of the placement
                Stone stone2 = assignedShip.getStoneByPlace(i);
                // When Stone == Null, there was no stone on this position on the ship
                if (stone2 == null){
                    continue;
                }
                // Adding the Stone to the Site
                buildingSite.getStones().add(stone2);
                // Removing the Stone from the Ship
                assignedShip.getStones().remove(stone2);
            }
            // Adding the updated BuildingSite back to the game
            game.getBuildingSites().add(buildingSite);

        }
        // Adding the updated ship back to the game
        game.getRoundByRoundCounter().getShips().add(assignedShip);

        return game;
    }
}
