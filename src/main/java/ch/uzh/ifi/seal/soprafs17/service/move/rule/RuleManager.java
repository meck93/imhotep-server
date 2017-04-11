package ch.uzh.ifi.seal.soprafs17.service.move.rule;

import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for applying the move.
 */
@Service
@Transactional
public class RuleManager {

    private final Logger log = LoggerFactory.getLogger(RuleManager.class);

    private final GameRepository gameRepository;

    private List<IRule> rules;

    @Autowired
    public RuleManager(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @PostConstruct
    public void addRule(){
        this.rules = new ArrayList<>();

        // Add new Application-Rules to the Service
        rules.add(new GetStonesRule());
        rules.add(new PlaceStoneRule());
    }

    public synchronized Game applyRules(AMove move, Game game) {
        log.debug("Applying Move: {} in Game: {}", move.getMoveType(), game.getId());

        for (IRule rule : rules){
            // Check if Validation-Rule supports the MoveType
            if (rule.supports(move)) {
                // Validate the Move -> if allowed to apply
                game = rule.apply(move, game);
                gameRepository.save(game);
            }
        }

        return game;
    }

}
