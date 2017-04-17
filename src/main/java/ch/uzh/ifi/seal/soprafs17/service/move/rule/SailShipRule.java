package ch.uzh.ifi.seal.soprafs17.service.move.rule;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.SailShipMove;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.entity.site.MarketPlace;
import ch.uzh.ifi.seal.soprafs17.exceptions.ApplyMoveException;

public class SailShipRule implements IRule {
    @Override
    public boolean supports(AMove move) {
        return move instanceof SailShipMove;
    }

    /*
     * Apply the changes to the game in regards to the Place_Stone move
     */
    @Override
    public Game apply(AMove move, Game game) throws ApplyMoveException {

        // Typecasting the AbstractMove to a PlaceStoneMove
        SailShipMove newMove = (SailShipMove) move;

        // Retrieving the Ship from the Game
        Ship ship = game.getRoundByRoundCounter(game.getRoundCounter()).getShipById(newMove.getShipId());

        // Updating the ship
        ship.setHasSailed(true);

        //Updating the status of the game
        if(game.getSiteById(newMove.getTargetSiteId()).getClass().equals(MarketPlace.class)){
            // Setting the Status to SUBROUND & docking the ship
            game.setStatus(GameStatus.SUBROUND);
            game.getMarketPlace().setDocked(true);
        }
        else {
            // Retrieving the correct BuildingSite
            BuildingSite buildingSite = (BuildingSite) game.getSiteById(newMove.getTargetSiteId());

            // Setting the site docked
            buildingSite.setDocked(true);

            // Unloading the Stones from the Ship onto the BuildingSite
            for (int i = 1; i <= ship.getMAX_STONES(); i++){
                // Retrieving the correct Stone in order of the placement
                Stone stone = ship.getStoneByPlace(i);
                // When Stone == Null, there was no stone on this position on the ship
                if (stone == null){
                    continue;
                }
                // Adding the Stone to the Site
                buildingSite.getStones().add(stone);
                // Removing the Stone from the Ship
                ship.getStones().remove(stone);
            }
        }
        return game;
    }
}
