package ch.uzh.ifi.seal.soprafs17.service.move.validation;

import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for validating moves.
 */
@Service
@Transactional
public class ValidationManager {

    private final Logger log = LoggerFactory.getLogger(ValidationManager.class);

    private List<IValidator> validators;

    @PostConstruct
    public void addValidation(){
        this.validators = new ArrayList<>();

        // Adding the rules of the Game
        validators.add(new GetStonesValidator());
    }

    public void validate(final AMove move, final Game game) {
        log.debug("Validating Move: {} in Game: {}", move.getMoveType(), game.getId());

        for (IValidator validator : validators){
            // Check if Validation-Rule supports the MoveType
            if (validator.supports(move)) {
                // Validate the Move -> if allowed to apply
                validator.validate(move, game);
            }
        }
    }

}
