package ch.uzh.ifi.seal.soprafs17.service.scoring;


import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;

import java.util.List;

import static ch.uzh.ifi.seal.soprafs17.constant.MarketCardType.*;

/**
 * Created by User on 21.04.2017.
 */
public class CardScorer implements IScoreable {

    @Override
    public boolean supports(String siteType) {
        return (siteType.equals(GameConstants.CARD));
    }

    @Override
    public Game score(Game game) {
        return this.scoreEndOfGame(game);
    }

    @Override
    public Game scoreNow(Game game) {
        return null;
    }

    @Override
    public Game scoreEndOfRound(Game game) {
        return null;
    }

    @Override
    public Game scoreEndOfGame(Game game) {

        for (int i = 1; i<=game.getPlayers().size();i++) {
            goThroughHandCards(game,i);
        }
        return game;
    }

    public void evaluateGreenCard(Game game, int player, MarketCardType cardType){
        if (cardType == PYRAMID_DECORATION){
            scoreGreenCard(game,player,GameConstants.PYRAMID);
        }
        if (cardType == TEMPLE_DECORATION){
            scoreGreenCard(game,player,GameConstants.TEMPLE);
        }
        if (cardType == BURIAL_CHAMBER_DECORATION){
            scoreGreenCard(game,player,GameConstants.BURIAL_CHAMBER);
        }
        if (cardType == OBELISK_DECORATION){
            scoreGreenCard(game,player,GameConstants.OBELISK);
        }
    }

    public void scoreGreenCard(Game game, int player, String siteType) {
        int scoredPoints;
        int[] arr = game.getPlayerByPlayerNr(player).getPoints();
        scoredPoints = game.getBuildingSite(siteType).getStones().size()/3;
        arr[4] = arr[4] + scoredPoints;
        game.getPlayerByPlayerNr(player).setPoints(arr);
    }

    public void scoreBlueCard(Game game, int player){
        int[] arr = game.getPlayerByPlayerNr(player).getPoints();
        arr[4] = arr[4] + 1;
        game.getPlayerByPlayerNr(player).setPoints(arr);
    }

    public void scoreVioletCards(Game game, int player, int amount){
        int[] arr;
        if (amount == 0){}
        else if (amount == 1){
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[4] = arr[4]+1;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }
        else if (amount == 2){
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[4] = arr[4]+3;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }
        else if (amount == 3){
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[4] = arr[4]+6;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }
        else if (amount == 4){
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[4] = arr[4]+10;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }
        else if (amount == 5){
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[4] = arr[4]+15;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }
        else if (amount > 5){
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[4] = arr[4]+2*(amount-5)+15;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }

    }

    public void goThroughHandCards(Game game, int player){
        Player currentPlayer = game.getPlayerByPlayerNr(player);
        List<MarketCard> handCards = currentPlayer.getHandCards();
        int statueCount = 0;
        for (MarketCard card : handCards) {
            if (card.getColor().equals(GameConstants.GREEN)){
                evaluateGreenCard(game,player, card.getMarketCardType());
            }
            if (card.getColor().equals(GameConstants.BLUE)){
                scoreBlueCard(game, player);
            }
            if (card.getColor().equals(GameConstants.VIOLET)){
                statueCount++;
            }
        }
        if (statueCount>0) {
            scoreVioletCards(game, player, statueCount);
        }
    }


}