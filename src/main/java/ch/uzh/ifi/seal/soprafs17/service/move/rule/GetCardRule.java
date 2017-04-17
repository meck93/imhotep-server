package ch.uzh.ifi.seal.soprafs17.service.move.rule;

import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.GetCardMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.ApplyMoveException;

import java.util.List;

/**
 * Created by Cristian on 15.04.2017.
 */
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

        // List of all current marketCards on marketPlace
        List<MarketCard> marketCards = game.getMarketPlace().getMarketCards();

        // Index of the marketCard to be removed
        int marketCardIndex = 0;

        // Setting the index to the correct element in the list
        for (MarketCard marketCard : marketCards){
            if (marketCard.getId() == newMove.getMarketCardId()){
                marketCardIndex = marketCards.indexOf(marketCard);
            }
        }

        // Removing the current MarketCard from the List of MarketCards on the MarketPlace
        MarketCard marketCard = marketCards.remove(marketCardIndex);

        // Adding the picked marketCard to the players handCards
        List<MarketCard> handCards = game.getPlayerByPlayerNr(game.getCurrentPlayer()).getHandCards(); //TODO: change to currentSubroundPlayer()
        handCards.add(marketCard);
        game.getPlayerByPlayerNr(game.getCurrentPlayer()).setHandCards(handCards); //TODO: change to currentSubroundPlayer()
        return game;
    }
}
