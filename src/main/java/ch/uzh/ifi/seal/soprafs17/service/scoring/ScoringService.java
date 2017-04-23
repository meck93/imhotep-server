package ch.uzh.ifi.seal.soprafs17.service.scoring;

import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for scoring all the points.
 */
@Service
@Transactional
public class ScoringService {

    private final Logger log = LoggerFactory.getLogger(ScoringService.class);
    private static final int TOTAL = 5;


    private GameRepository gameRepository;
    private List<IScoreable> rateables;

    public ScoringService(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    @PostConstruct
    public void addValidation(){
        this.rateables = new ArrayList<>();

        // Adding the rules of the Game
        this.rateables.add(new PyramidScorer());
        this.rateables.add(new TempleScorer());
        this.rateables.add(new ObeliskScorer());
        this.rateables.add(new BurialChamberScorer());
    }

    public synchronized void score(Game game, String siteType) {
        log.debug("Scoring Game: {}", game.getId());

        for (IScoreable rateable : rateables){
            // Check if Scoring-Rule supports the BuildingType
            if (rateable.supports(siteType)) {
                // Score the BuildingSite
                Game changedGame = rateable.score(game);
                Game scoredGame = this.sumUp(changedGame);
                gameRepository.save(scoredGame);
            }
        }
    }

    public synchronized Game sumUp(Game game){
        log.debug("Summing up all the points for Game: " + game.getId());

        game.getPlayers().forEach(player -> {
            // Resetting the total points
            player.getPoints()[TOTAL] = 0;
            // Adding up all the individual points from each BuildingSite/Remaining MarketCard
            for (int i = 0; i < TOTAL; i++){
                player.getPoints()[TOTAL] += player.getPoints()[i];
            }
        });

        return game;
    }
}
