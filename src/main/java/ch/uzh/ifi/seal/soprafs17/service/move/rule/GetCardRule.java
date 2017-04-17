package ch.uzh.ifi.seal.soprafs17.service.move.rule;

import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.GetCardMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.ApplyMoveException;

public class GetCardRule implements IRule {

    @Override
    public boolean supports(AMove move) {
        return move instanceof GetCardMove;
    }
    /*
     * Apply the changes to the game in regards to the Get_Card Move
     */
    @Override
    public Game apply(AMove move, Game game) throws ApplyMoveException {

        // Typecasting the AbstractMove to a GetCardMove
        GetCardMove newMove = (GetCardMove) move;

        // Retrieve the MarketCard
        MarketCard targetCard = game.getMarketPlace().getMarketCardById(newMove.getMarketCardId());

        // Removing the targetCard from the MarketPlace
        game.getMarketPlace().getMarketCards().remove(targetCard);

        // Adding the targetCard to the currentSubRoundPlayers HandCards
        game.getPlayerByPlayerNr(game.getCurrentSubRoundPlayer()).getHandCards().add(targetCard);

        return game;
    }
}
