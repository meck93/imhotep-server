package ch.uzh.ifi.seal.soprafs17.service.move;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.ApplyMoveException;
import ch.uzh.ifi.seal.soprafs17.exceptions.InternalServerException;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.BadRequestHttpException;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.service.move.rule.RuleManager;
import ch.uzh.ifi.seal.soprafs17.service.move.validation.ValidationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing moves.
 */
@Service
@Transactional
public class MoveService {

    private final Logger log = LoggerFactory.getLogger(MoveService.class);

    private final GameRepository gameRepository;
    private final ValidationManager validationManager;
    private final RuleManager ruleManager;

    @Autowired
    public MoveService(GameRepository gameRepository, ValidationManager validationManager, RuleManager ruleManager) {
        this.gameRepository = gameRepository;
        this.validationManager = validationManager;
        this.ruleManager = ruleManager;
    }

    public synchronized void validateAndApply(AMove move) throws BadRequestHttpException, InternalServerException {
        log.debug("Validates and applies the Move: {}", move);

        Game game = gameRepository.findById(move.getGameId());

        try {
            // Validate the move if it can be applied
            this.validationManager.validate(move, game);
        }
        catch (MoveValidationException moveValException) {
            throw new BadRequestHttpException(moveValException);
        }

        try {
            // Applying the Move to the Game
            game = ruleManager.applyRules(move, game);
        }
        catch (ApplyMoveException applyMoveException) {
            throw new InternalServerException(applyMoveException);
        }

        // Advancing the Game to the next Player, only if the Game is in Status: RUNNING
        if ((game.getStatus() == GameStatus.RUNNING)) {
            game.setCurrentPlayer((game.getCurrentPlayer()) % (game.getPlayers().size()) + 1);
        }
        // Advancing the Sub-Round when the Game is in Status: SUBROUND
        if (game.getStatus() == GameStatus.SUBROUND){
            // Advancing the currentSubRoundPlayer to the next Player according to the Stone on the Ship
            // game.setCurrentSubRoundPlayer();
        }

        // Saving the changed Game state into the DB
        this.gameRepository.save(game);
    }
}
