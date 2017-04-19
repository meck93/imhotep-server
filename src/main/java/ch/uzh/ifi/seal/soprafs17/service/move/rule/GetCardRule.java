package ch.uzh.ifi.seal.soprafs17.service.move.rule;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
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

        // Red Card directly adds a Stone to the specific Site
        if (targetCard.getColor().equals(GameConstants.RED)) {
            // Red Card directly adds a Stone to the Burial Chamber
            if (targetCard.getMarketCardType().equals(MarketCardType.SARCOPHAGUS)) {
                // Put 1 Stone directly from Quarry onto the Burial Chamber
                game.getBuildingSite(GameConstants.BURIAL_CHAMBER).getStones()
                        .add(game.getStoneQuarry()
                                .getStonesByPlayerNr(game.getCurrentSubRoundPlayer())
                                .remove(0));
            }
            // Red Card directly adds a Stone to the Pyramid
            if (targetCard.getMarketCardType().equals(MarketCardType.ENTRANCE)){
                // Put 1 Stone directly from Quarry onto the Pyramid
                game.getBuildingSite(GameConstants.PYRAMID).getStones()
                        .add(game.getStoneQuarry()
                                .getStonesByPlayerNr(game.getCurrentSubRoundPlayer())
                                .remove(0));
            }
            // Red Card directly adds a Stone to the Obelisk
            if (targetCard.getMarketCardType().equals(MarketCardType.PAVED_PATH)){
                // Put 1 Stone directly from Quarry onto the Obelisk
                game.getBuildingSite(GameConstants.OBELISK).getStones()
                        .add(game.getStoneQuarry()
                                .getStonesByPlayerNr(game.getCurrentSubRoundPlayer())
                                .remove(0));
            }
        }
        // The targetCard Color is not RED
        else {
            // Adding the targetCard to the currentSubRoundPlayers HandCards
            game.getPlayerByPlayerNr(game.getCurrentSubRoundPlayer()).getHandCards().add(targetCard);
        }
        return game;
    }
}
