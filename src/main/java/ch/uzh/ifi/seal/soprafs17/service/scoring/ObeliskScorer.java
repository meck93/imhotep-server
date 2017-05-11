package ch.uzh.ifi.seal.soprafs17.service.scoring;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.site.Obelisk;
import ch.uzh.ifi.seal.soprafs17.exceptions.InternalServerException;

import java.util.*;

import static ch.uzh.ifi.seal.soprafs17.GameConstants.*;
import static java.util.stream.Collectors.toMap;

public class ObeliskScorer implements IScoreable {
    @Override
    public boolean supports(String siteType) {
        return siteType.equals(GameConstants.OBELISK);
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

        // Retrieving the Stones to be scored from the Obelisk
        Obelisk obelisk = (Obelisk) game.getBuildingSite(GameConstants.OBELISK);
        List<Stone> stones = obelisk.getStones();

        // The Scores used according to the Nr. of Players
        int[] scores = obelisk.getScores(game.getNumberOfPlayers());

        // The Ranks of each Player
        Map<Integer, Integer> testMap  = new HashMap<>();
        Map<Integer, String> playerMap = new HashMap<>();

        // Height of the Obelisk of each Player
        List<Stone> black = new ArrayList<>();
        List<Stone> white = new ArrayList<>();
        List<Stone> brown = new ArrayList<>();
        List<Stone> gray = new ArrayList<>();

        // are used to make sure every color doesn't overwrite a different color and is only assigned once
        boolean blackAssigned = false;
        boolean whiteAssigned = false;
        boolean brownAssigned = false;
        boolean grayAssigned = false;

        // Calculating the height of each Obelisk
        for (Stone stone : stones){
            switch (stone.getColor()){
                case BLACK: black.add(stone); break;
                case WHITE: white.add(stone); break;
                case BROWN: brown.add(stone); break;
                case GRAY:  gray.add(stone); break;
                default: throw new InternalServerException("This Color is not supported: " + stone.getColor());
            }
        }

        // List with all Obelisks to be scored
        List<List> toBeScored = new ArrayList<>();
        // List with the size of each Obelisk
        List<Integer> sizes = new ArrayList<>();

        // Adding the size and the obelisk (list of stones) if they're not null
        if (!black.isEmpty()) {
            toBeScored.add(black);
            sizes.add(black.size());
        }
        if (!white.isEmpty()) {
            toBeScored.add(white);
            sizes.add(white.size());
        }
        if (!brown.isEmpty()) {
            toBeScored.add(brown);
            sizes.add(brown.size());
        }
        if (!gray.isEmpty()) {
            toBeScored.add(gray);
            sizes.add(gray.size());
        }

        // Initial Rank
        int rank = 1;
        // maxValue in List
        Integer max;

        // Determining the ranking of each Player
        while(!sizes.isEmpty()){
            // The largest obelisk remaining in the list
            max = Collections.max(sizes);

            for (int j = 0; j < toBeScored.size(); j++){
                // checking the sizes of the largest obelisk against the one currently looked at in the list
                if (max == (toBeScored.get(j).size())) {
                    if (max == black.size() && !blackAssigned){
                        // Creating a HashMap entry with key: index and value: rank
                        testMap.put(j, rank);
                        // Creating a HashMap entry with key: index and value: color
                        playerMap.put(j, GameConstants.BLACK);
                        // making sure the black color cannot be overwritten <=> no color can have more than one rank
                        blackAssigned = true;
                    }
                    else if (max == white.size() && !whiteAssigned){
                        // Creating a HashMap entry with key: index and value: rank
                        testMap.put(j, rank);
                        // Creating a HashMap entry with key: index and value: color
                        playerMap.put(j, GameConstants.WHITE);
                        // making sure the black color cannot be overwritten <=> no color can have more than one rank
                        whiteAssigned = true;
                    }
                    else if (max == brown.size() && !brownAssigned){
                        // Creating a HashMap entry with key: index and value: rank
                        testMap.put(j, rank);
                        // Creating a HashMap entry with key: index and value: color
                        playerMap.put(j, GameConstants.BROWN);
                        // making sure the black color cannot be overwritten <=> no color can have more than one rank
                        brownAssigned = true;
                    }
                    else if (max == gray.size() && !grayAssigned){
                        // Creating a HashMap entry with key: index and value: rank
                        testMap.put(j, rank);
                        // Creating a HashMap entry with key: index and value: color
                        playerMap.put(j, GameConstants.GRAY);
                        // making sure the black color cannot be overwritten <=> no color can have more than one rank
                        grayAssigned = true;
                    }
                    // Removing the largest obelisk from the list
                    sizes.remove(max);
                }
            }
            // Increasing the Rank after all Obelisks of the same size have been scored
            rank++;
        }

        // Intermediate results
        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        // Counter: How many times a certain rank has occured
        int counter1 = 0;
        int counter2 = 0;
        int counter3 = 0;
        // Counter to iterate through the scores[]
        int nr1 = 0;

        // Sorting the Map according to Value and creating a new sorted one
        Map<Integer, Integer> sortedMap = testMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2) -> e1, LinkedHashMap::new));

        // Calculating the sum of each rank
        for (Integer integer : sortedMap.values()){
            if (integer == 1){
                sum1 += scores[nr1++];
                counter1++;
            }
            if (integer == 2){
                sum2 += scores[nr1++];
                counter2++;
            }
            if (integer == 3){
                sum3 += scores[nr1++];
                counter3++;
            }
        }

        // final resulting scores for each rank
        int res1 = 0, res2 = 0, res3 = 0;

        // making sure the result, sum and counter are not null
        // calculating the resulting value: purposely integer division -> rounding down
        if (sum1 != 0) {
            res1 = sum1/counter1;
        }
        if (sum2 != 0) {
            res2 = sum2/counter2;
        }
        if (sum3 != 0) {
            res3 = sum3/counter3;
        }

        // Iterator for <Key, Color>
        Map.Entry<Integer, String> mapping;
        Iterator<Map.Entry<Integer, String>> iterator = playerMap.entrySet().iterator();
        // Assigning the correct score to each rank
        for (Map.Entry<Integer, Integer> entry : testMap.entrySet()){
            // Using an iterator because entries can be removed
            while (iterator.hasNext()){
                mapping = iterator.next();
                // If entry has rank 1 && key of entry and mapping must be the same
                if (entry.getValue() == 1 && entry.getKey().equals(mapping.getKey())) {
                    // Sett the correct result as points
                    game.getPlayerByColor(mapping.getValue()).getPoints()[2] = res1;
                    // remove the mapping from the iterator to make sure every color <=> player
                    // will have the points only assigned once
                    iterator.remove();
                    // return outside of the while loop once 1 mapping was found
                    break;
                }
                // If entry has rank 2 && key of entry and mapping must be the same
                else if (entry.getValue() == 2 && entry.getKey().equals(mapping.getKey())) {
                    // Sett the correct result as points
                    game.getPlayerByColor(mapping.getValue()).getPoints()[2] = res2;
                    // remove the mapping from the iterator to make sure every color <=> player
                    // will have the points only assigned once
                    iterator.remove();
                    // return outside of the while loop once 1 mapping was found
                    break;
                }
                // If entry has rank 3 && key of entry and mapping must be the same
                else if (entry.getValue() == 3 && entry.getKey().equals(mapping.getKey())) {
                    // Sett the correct result as points
                    game.getPlayerByColor(mapping.getValue()).getPoints()[2] = res3;
                    // remove the mapping from the iterator to make sure every color <=> player
                    // will have the points only assigned once
                    iterator.remove();
                    // return outside of the while loop once 1 mapping was found
                    break;
                }
                // If entry has rank 4 && key of entry and mapping must be the same
                else if (entry.getValue() == 4 && entry.getKey().equals(mapping.getKey())) {
                    // Sett the correct result as points
                    game.getPlayerByColor(mapping.getValue()).getPoints()[2] = scores[3];
                    // remove the mapping from the iterator to make sure every color <=> player
                    // will have the points only assigned once
                    iterator.remove();
                    // return outside of the while loop once 1 mapping was found
                    break;
                }
            }
        }
        return game;
    }
}