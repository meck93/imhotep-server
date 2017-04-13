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
    }

    public synchronized void score(Game game) {
        log.debug("Scoring Game: {}", game.getId());

        for (IScoreable rateable : rateables){
            // Check if Scoring-Rule supports the BuildingType
            if (rateable.supports(game)) {
                // Score the BuildingSite
                Game changedGame = rateable.score(game);
                gameRepository.save(changedGame);
            }
        }
    }

}
