package ch.uzh.ifi.seal.soprafs17.service.move;

import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.move.*;
import ch.uzh.ifi.seal.soprafs17.exceptions.ApplyMoveException;
import ch.uzh.ifi.seal.soprafs17.exceptions.BadRequestHttpException;
import ch.uzh.ifi.seal.soprafs17.exceptions.InternalServerException;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;
import ch.uzh.ifi.seal.soprafs17.repository.AMoveRepository;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.service.move.rule.RuleManager;
import ch.uzh.ifi.seal.soprafs17.service.move.validation.ValidationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static ch.uzh.ifi.seal.soprafs17.GameConstants.*;

/**
 * Service class for managing moves.
 */
@Service
@Transactional
public class MoveService {

    private final Logger log = LoggerFactory.getLogger(MoveService.class);

    private final AMoveRepository aMoveRepository;
    private final GameRepository gameRepository;
    private final ValidationManager validationManager;
    private final RuleManager ruleManager;

    @Autowired
    public MoveService(AMoveRepository aMoveRepository, GameRepository gameRepository, ValidationManager validationManager, RuleManager ruleManager) {
        this.aMoveRepository = aMoveRepository;
        this.gameRepository = gameRepository;
        this.validationManager = validationManager;
        this.ruleManager = ruleManager;
    }

    public AMove createMove(AMove move){
        AMove newMove = null;
        // Creating the move depending on the type
        switch (move.getMoveType()) {
            case GET_STONES: newMove = new GetStonesMove(move.getMoveType()); break;
            case PLACE_STONE: newMove = new PlaceStoneMove(move.getMoveType()); break;
            case SAIL_SHIP: newMove = new SailShipMove(move.getMoveType()); break;
            case PLAY_CARD: newMove = new PlayCardMove(move.getMoveType()); break;
        }
        newMove.setGameId(move.getGameId());
        newMove.setRoundNr(move.getRoundNr());
        newMove.setPlayerNr(move.getPlayerNr());

        aMoveRepository.save(newMove);

        log.debug("Created Move of Type: " + newMove.getMoveType() + " with ID: " + newMove.getId());

        return newMove;
    }

    public AMove initializeMove(AMove newMove) {

        // Creating the move depending on the type
        switch (newMove.getMoveType()) {
            case GET_STONES: break;
            case PLACE_STONE: break;
            case SAIL_SHIP:  break;
            case PLAY_CARD: break;
        }

        aMoveRepository.save(newMove);

        return newMove;

    }

    public AMove addMove(AMove move){
        // TODO: check that moveType is actually a correct moveType -> otherwise throw exception
        log.debug("addMove: " + move.getMoveType());

        // Creating a newMove
        AMove newMove = createMove(move);

        // Setting Move specific values
        //initializeMove(newMove);

        aMoveRepository.save(newMove);

        return newMove;
    }

    public AMove getMove(Long moveId) {
        log.debug("getMove: " + moveId);

        // Find the move in the DB
        AMove move = aMoveRepository.findOne(moveId);

        if (move != null) {
            return move;
        }
        else {
            log.error("Couldn't find the Move: {}", moveId);
            return null;
        }
    }

    public synchronized void validateAndApply(AMove move) throws BadRequestHttpException, InternalServerException {
        log.debug("Validates and applies the Move: {}", move);

        Game game = gameRepository.findById(move.getGameId());

        try {
            // Validate the move if it can be applied
            this.validationManager.validate(move, game);
        }
        catch (MoveValidationException moveValException) {
            log.error("Service was not able to validate Move: {0} on Game: {1}", move, game);
            throw new BadRequestHttpException(moveValException);
        }

        try {
            // Applying the Move to the Game
            game = ruleManager.applyRules(move, game);
        }
        catch (ApplyMoveException applyMoveException) {
            log.error("Service was not able to apply Move: {0} on Game: {1}", move, game);
            throw new InternalServerException(applyMoveException);
        }

        // Saving the changed Game state into the DB
        this.gameRepository.save(game);
    }
}
