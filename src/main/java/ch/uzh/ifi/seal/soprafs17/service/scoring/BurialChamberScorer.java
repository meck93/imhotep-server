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

    // method to convert a List to an array
    public int[] convertToArray(List<Stone> aList){
        // max 30 stones on burial chamber to be placed
        int[] array = new int[30];
        int i = 0;
        int aListCounter = 0;

        // go trough list to identify a players stone
        while(i >= 0 && aListCounter<aList.size()){
            if (aList.get(aListCounter).getColor().equals(GameConstants.BLACK)){
                // set value to 1 for player 1
                array[i] = 1;
            }
            if (aList.get(aListCounter).getColor().equals(GameConstants.WHITE)){
                // set value to 1 for player 1
                array[i] = 2;
            }
            if (aList.get(aListCounter).getColor().equals(GameConstants.BROWN)){
                // set value to 1 for player 1
                array[i] = 3;
            }
            if (aList.get(aListCounter).getColor().equals(GameConstants.GRAY)){
                // set value to 1 for player 1
                array[i] = 4;
            }
            i = incrementCount(i,array);
            aListCounter++;
        }

        return array;
    }

    // method to add points to each player
    public void addPoints(Game game, int player, int figure){
        int[] arr;
        if (figure == 1){
            // add one point for a figure of size 1
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[3] = arr[3]+1;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }
        else if (figure == 2){
            // add 3 points for a figure of size 2
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[3] = arr[3]+3;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }
        else if (figure == 3){
            // add 6 points for a figure of size 3
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[3] = arr[3]+6;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }
        else if (figure == 4){
            // add 10 points for a figure of size 4
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[3] = arr[3]+10;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }
        else if (figure == 5){
            // add 15 points for a figure of size 5
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[3] = arr[3]+15;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }
        else if (figure > 5){
            // add 15 points + for every additional stone you get two points
            arr = game.getPlayerByPlayerNr(player).getPoints();
            arr[3] = arr[3]+2*(figure-5)+15;
            game.getPlayerByPlayerNr(player).setPoints(arr);
        }
    }

    // lookup method: Checks if the index exists (to prevent out of bound exception)
    public boolean isValid(int position) {
        if (position >= 0 && position <= 29) {
            return true;
        } else {
            return false;
        }
    }
    // looks down, returns the index that is below the chosen index
    public int lookDown(int[] arr, int position) {

        // An index of the last row (position 20-29) is not allowed to look down
        if (position >= 20 || position <0) return -1;
        int pos;
        pos = (position + 10) % arr.length;
        return pos;
    }
    // looks up, returns the index that is above the chosen index
    public int lookUp(int[] arr, int position) {

        // An index of the first row (position 0-9) is not allowed to look up
        if (position <=9 || position <0 || position >= 30 ) return -1;
        int pos;
        pos = (position - 10 ) % arr.length;
        return pos;
    }
    // looks right, returns the index after the one chosen (next position)
    public int lookRight(int[] arr, int position) {

        // The last index of each row is not allowed to look right
        if (position >= 29 || position<0 || position == 9 || position == 19 ) return -1;
        int pos;
        pos = (position + 1) % arr.length;
        return pos;
    }

    // looks right, returns the index before the chosen index
    public int lookLeft(int[] arr, int position) {

        // The first index of each row is not allowed to look left
        if (position > 29 || position<=0 || position == 10 || position == 20 ) return -1;
        int pos;
        pos = (position + -1) % arr.length;
        return pos;
    }

    // Increments the lookup counter
    public int incrementCount(int counter, int[] arr) {

        // Return -1 for an invalid index
        if (counter >= 29 || counter < 0) return -1;
        // To match the correct filling order, an index of the first row follows the index of the last row
        if (counter >= 20) return (counter + 11) % arr.length;
        // To match the correct filling order, after the first row comes the second row, after the second row comes the third
        else return (counter + 10) % arr.length;
    }

    // finds the correct lookup index
    public int findLookUpIndex(int[] arr){
        for (int i = 0; i<arr.length; i++){
            // Set to -2 (Left to be computed)
            if(arr[i] == -2) return i;
        }
        return -1;
    }

    // Method that goes trough the array and computes all figures
    public void scoreChamber(int arr[], int player, Game game) {
        int count = 0;
        int figure = 0;
        while (isValid(count)) {

            // Found an entry point
            if (arr[count] == player) {
                // Increase figure size
                figure++;
                // Look up and search for the same stone
                if (isValid(lookUp(arr, count)) && arr[lookUp(arr, count)] == player) {
                    arr[lookUp(arr,count)] = -2;
                    figure++;
                }
                // Look down and search for the same stone
                if (isValid(lookDown(arr, count)) && arr[lookDown(arr, count)] == player) {
                    arr[lookDown(arr,count)] = -2;
                    figure++;
                }
                // Look right and search for the same stone
                if (isValid(lookRight(arr, count)) && arr[lookRight(arr, count)] == player) {
                    arr[lookRight(arr,count)] = -2;
                    figure++;
                }
                // Look left and search for the same stone
                if (isValid(lookLeft(arr, count)) && arr[lookLeft(arr, count)] == player) {
                    arr[lookLeft(arr,count)] = -2;
                    figure++;
                }
                // Set value to -1 (looked trough)
                arr[count] = -1;

                // Init lookup index
                int lookUpIndex;
                lookUpIndex = findLookUpIndex(arr);

                // While stone is from a player
                while(lookUpIndex>=0){
                    // If look up is valid, increase figure size
                    if (isValid(lookUp(arr, lookUpIndex)) && arr[lookUp(arr, lookUpIndex)] == player) {
                        arr[lookUp(arr,lookUpIndex)] = -2;
                        figure++;
                    }
                    // If look down is valid, increase figure size
                    if (isValid(lookDown(arr, lookUpIndex)) && arr[lookDown(arr, lookUpIndex)] == player) {
                        arr[lookDown(arr,lookUpIndex)] = -2;
                        figure++;
                    }
                    // If look right is valid, increase figure size
                    if (isValid(lookRight(arr, lookUpIndex)) && arr[lookRight(arr, lookUpIndex)] == player) {
                        arr[lookRight(arr,lookUpIndex)] = -2;
                        figure++;
                    }
                    // If look left is valid, increase figure size
                    if (isValid(lookLeft(arr, lookUpIndex)) && arr[lookLeft(arr, lookUpIndex)] == player) {
                        arr[lookLeft(arr,lookUpIndex)] = -2;
                        figure++;
                    }
                    arr[lookUpIndex] = -1;
                    lookUpIndex = findLookUpIndex(arr);
                }

                // Add points according to the players figure
                addPoints(game, player, figure);
                figure = 0;

            } else {
                // increment count
                count = incrementCount(count, arr);
            }

        }
    }

    // The burialChamber gets scored at the end of the game
    @Override
    public Game scoreEndOfGame(Game game) {
        int[] arr = convertToArray(game.getBuildingSite(GameConstants.BURIAL_CHAMBER).getStones());

        for (int i = 1; i<=game.getPlayers().size();i++) {
            scoreChamber(arr, i, game);
        }

        return game;
    }
}