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

public class LeverRule implements IRule{

    @Override
    public boolean supports(AMove move) {
        return move.getMoveType().equals(MarketCardType.LEVER.toString());
    }

    @Override
    public Game apply(AMove move, Game game) throws ApplyMoveException {

        /* LEVER MOVE: let's the Player decide the Unloading order of the Stones on the specified Ship */

        // Typecasting the AbstractMove to a PlaceStoneMove
        PlayCardMove newMove = (PlayCardMove) move;

        // Retrieving the Player
        Player player = game.getPlayerByPlayerNr(game.getCurrentPlayer());
        // removing the current Player from the List of all Players
        game.getPlayers().remove(player);

        // Removing the marketCard from the players deck of cards
        player.getHandCards().remove(player.getMarketCardById(newMove.getCardId()));

        // Find the assigned ship
        Ship assignedShip = game.getRoundByRoundCounter().getShipById(newMove.getShipId());
        game.getRoundByRoundCounter().getShips().remove(assignedShip);

        // Updating the ship
        assignedShip.setHasSailed(true);

        //Updating the status of the game
        if(game.getSiteById(newMove.getTargetSiteId()).getClass().equals(MarketPlace.class)){
            // Setting the Status to SUBROUND
            game.setStatus(GameStatus.SUBROUND);
            // Docking the Ship with correct ID to the MarketPlace
            game.getMarketPlace().setDockedShipId(newMove.getShipId());
            game.getMarketPlace().setDocked(true);

            // Reordering the stones on the Ship according to the unload order
            for (int i = 0; i < newMove.getUnloadingOrder().size(); i++) {
                // Retrieve the Stones on the Ship according to the unloadOrder and set the new placeOnShip
                assignedShip.getStoneById(newMove.getUnloadingOrder().get(i)).setPlaceOnShip(i + 1);
            }
        }

        else {
            // Retrieving the correct BuildingSite
            BuildingSite buildingSite = (BuildingSite) game.getSiteById(newMove.getTargetSiteId());
            game.getBuildingSites().remove(buildingSite);

            // Setting the site docked
            buildingSite.setDocked(true);

            // Unloading the Stones from the Ship onto the BuildingSite
            for (int i = 0; i < newMove.getUnloadingOrder().size(); i++){
                // Retrieving the correct Stone in order of the placement
                Stone stone2 = assignedShip.getStoneById(newMove.getUnloadingOrder().get(i));
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

        // Adding the updated Player back to the Game
        game.getPlayers().add(player);

        return game;
    }
}
