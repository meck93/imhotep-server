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

        // The number of players in this game
        int nrOfPlayers = game.getNumberOfPlayers();

        // The Scores used according to the Nr. of Players
        int[] scores = obelisk.getScores(game.getNumberOfPlayers());

        // The Ranks of each Player
        Map<Integer, Integer> testMap  = new HashMap<>();

        // Height of the Obelisk of each Player
        List<Stone> black = new ArrayList<>();
        List<Stone> white = new ArrayList<>();
        List<Stone> brown = new ArrayList<>();
        List<Stone> gray = new ArrayList<>();

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
        if (!black.isEmpty()){ toBeScored.add(black); sizes.add(black.size()); }
        if (!white.isEmpty()){ toBeScored.add(white); sizes.add(white.size()); }
        if (!brown.isEmpty()){ toBeScored.add(brown); sizes.add(brown.size()); }
        if (!gray.isEmpty()) { toBeScored.add(gray);  sizes.add(gray.size()); }

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
                    // Creating a HashMap entry with key: index and value: rank
                    testMap.put(j, rank);
                    // Removing the largest obelisk from the list
                    sizes.remove(max);
                }
            }
            // Increasing the Rank after all Obelisks of the same size have been scored
            rank++;
        }

        // Intermediate results
        int sum1 = 0, sum2 = 0, sum3 = 0;
        // Counter: How many times a certain rank has occured
        int counter1 = 0, counter2 = 0, counter3 = 0;
        // Counter to iterate through the scores[]
        int nr1 = 0;

        // Sorting the Map according to Value
        Map<Integer, Integer> sortedMap = testMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1,e2) -> e1, LinkedHashMap::new));

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

        // making sure the result, sum and counter are not null, calculating the resulting value
        if (sum1 != 0) res1 = sum1/counter1;
        if (sum2 != 0) res2 = sum2/counter2;
        if (sum3 != 0) res3 = sum3/counter2;

        // Assigning the correct score to each rank
        for (Map.Entry<Integer, Integer> entry : testMap.entrySet()){
            if (entry.getValue() == 1){
                game.getPlayerByPlayerNr(entry.getKey() + 1).getPoints()[2] = res1;
            }
            if (entry.getValue() == 2){
                game.getPlayerByPlayerNr(entry.getKey() + 1).getPoints()[2] = res2;
            }
            if (entry.getValue() == 3){
                game.getPlayerByPlayerNr(entry.getKey() + 1).getPoints()[2] = res3;
            }
            if (entry.getValue() == 4) {
                game.getPlayerByPlayerNr(entry.getKey() + 1).getPoints()[2] = scores[3];
            }
        }

        return game;
    }
}
