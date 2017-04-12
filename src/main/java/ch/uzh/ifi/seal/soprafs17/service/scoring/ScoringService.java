package ch.uzh.ifi.seal.soprafs17.service.scoring;

import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
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

    private List<IRateable> rateables;

    @PostConstruct
    public void addValidation(){
        this.rateables = new ArrayList<>();

        // Adding the rules of the Game
        // this.rateables.add(new PyramidScorer());

    }

    public synchronized void score(Game game, BuildingSite buildingSite) {
        log.debug("Scoring Game: {}", game.getId());

        for (IRateable rateable : rateables){
            // Check if Scoring-Rule supports the BuildingType
            if (rateable.supports(buildingSite.getBuildingSiteType())) {
                // Score the BuildingSite
                rateable.score(game);
            }
        }
    }

}
