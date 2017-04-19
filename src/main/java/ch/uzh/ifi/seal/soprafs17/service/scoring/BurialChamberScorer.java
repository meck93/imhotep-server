package ch.uzh.ifi.seal.soprafs17.service.scoring;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;

import java.util.List;

public class BurialChamberScorer implements IScoreable {
    @Override
    public boolean supports(String siteType) {
        return siteType.equals(GameConstants.BURIAL_CHAMBER);
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

    public int[] convertToArray(List<Stone> aList){
        int[] array = new int[30];
        for (int i = 0; i < aList.size(); i++){
            if (aList.get(i).getColor() == GameConstants.BLACK){
                array[i] = 1;
            }
            if (aList.get(i).getColor() == GameConstants.WHITE){
                array[i] = 2;
            }
            if (aList.get(i).getColor() == GameConstants.BROWN){
                array[i] = 3;
            }
            if (aList.get(i).getColor() == GameConstants.GRAY){
                array[i] = 4;
            }
        }
        return array;
    }

    public void addPoints(Game game, int player, int figure){
        int[] arr;
        if (figure == 0){}
        else if (figure == 1){
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[3] = arr[3]+1;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }
        else if (figure == 2){
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[3] = arr[3]+3;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }
        else if (figure == 3){
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[3] = arr[3]+6;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }
        else if (figure == 4){
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[3] = arr[3]+10;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }
        else if (figure == 5){
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[3] = arr[3]+15;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }
        else if (figure > 5){
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[3] = arr[3]+2*(figure-5)+15;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }
    }


    public boolean isValid(int position) {
        if (position >= 0 && position <= 29) {
            return true;
        } else return false;
    }

    public int lookDown(int[] arr, int position) {
        if (position >= 20 || position <0) return -1;
        int pos;
        pos = (position + 10) % arr.length;
        return pos;
    }

    public int lookUp(int[] arr, int position) {
        if (position <=9 || position <0 || position >= 30 ) return -1;
        int pos;
        pos = (position - 10 ) % arr.length;
        return pos;
    }

    public int lookRight(int[] arr, int position) {
        if (position >= 29 || position<0 || position == 9 || position == 19 ) return -1;
        int pos;
        pos = (position + 1) % arr.length;
        return pos;
    }

    public int incrementCount(int counter, int[] arr) {
        if (counter >= 29 || counter < 0) return -1;
        if (counter >= 20) return (counter + 11) % arr.length;
        else return counter = (counter + 10) % arr.length;
    }

    public int findLookUpIndex(int[] arr){
        for (int i = 0; i<arr.length; i++){
            if(arr[i] == -2) return i;
        }
        return -1;
    }

    public void scoreChamber(int arr[], int player, Game game) {
        int count = 0;
        int figure = 0;
        while (isValid(count)) {

            // Find an entry point

            if (arr[count] == player) {
                figure++;  
                if (isValid(lookUp(arr, count)) && arr[lookUp(arr, count)] == player) {
                    arr[lookUp(arr,count)] = -2;
                    figure++;
                }
                if (isValid(lookDown(arr, count)) && arr[lookDown(arr, count)] == player) {
                    arr[lookDown(arr,count)] = -2;
                    figure++;
                }
                if (isValid(lookRight(arr, count)) && arr[lookRight(arr, count)] == player) {
                    arr[lookRight(arr,count)] = -2;
                    figure++;
                }
                arr[count] = -1;

                // Init lookup index
                int lookUpIndex;
                lookUpIndex = findLookUpIndex(arr);

                while(lookUpIndex>=0){
                    if (isValid(lookUp(arr, lookUpIndex)) && arr[lookUp(arr, lookUpIndex)] == player) {
                        arr[lookUp(arr,lookUpIndex)] = -2;
                        figure++;
                    }
                    if (isValid(lookDown(arr, lookUpIndex)) && arr[lookDown(arr, lookUpIndex)] == player) {
                        arr[lookDown(arr,lookUpIndex)] = -2;
                        figure++;
                    }
                    if (isValid(lookRight(arr, lookUpIndex)) && arr[lookRight(arr, lookUpIndex)] == player) {
                        arr[lookRight(arr,lookUpIndex)] = -2;
                        figure++;
                    }
                    arr[lookUpIndex] = -1;
                    lookUpIndex = findLookUpIndex(arr);
                }
                addPoints(game,player,figure);
                figure = 0;

            } else {
                count = incrementCount(count, arr);
            }

        }
    }

    @Override
    public Game scoreEndOfGame(Game game) {
        int[] arr = convertToArray(game.getBuildingSite(GameConstants.BURIAL_CHAMBER).getStones());

        for (int i = 1; i<=game.getPlayers().size();i++) {
            scoreChamber(arr, i, game);
        }

        return game;
    }
}
