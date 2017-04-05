package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.entity.move.*;
import ch.uzh.ifi.seal.soprafs17.repository.AMoveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static ch.uzh.ifi.seal.soprafs17.GameConstants.*;

/**
 * Service class for managing moves.
 */
@Service
@Transactional
public class MoveService {

    private final Logger log = LoggerFactory.getLogger(MoveService.class);

    private final AMoveRepository aMoveRepository;

    @Autowired
    public MoveService(AMoveRepository aMoveRepository) {
        this.aMoveRepository = aMoveRepository;
    }

    public AMove createMove(String moveType){
        // TODO: check that moveType is actually a correct moveType -> otherwise throw exception

        AMove newMove = null;

        // Creating the move depending on the type
        switch (moveType) {
            case GET_STONES: newMove = new GetStonesMove(moveType); break;
            case PLACE_STONE: newMove = new PlaceStoneMove(moveType); break;
            case SAIL_SHIP: newMove = new SailShipMove(moveType); break;
            case PLAY_CARD: newMove = new PlayCardMove(moveType); break;
        }

        // Saving the newly created Move to the DB
        aMoveRepository.save(newMove);

        if (newMove == null){
            log.error("Failed to create a Move of Type: " + moveType);
            return null;
        } else {
            log.debug("Created Move of Type: " + moveType + " with ID: " + newMove.getId());
            return newMove;
        }
    }

    public AMove addMove(String moveType, int playerNr, Long gameId, Long roundId){
        log.debug("addMove: " + moveType);

        // Creating a newMove
        AMove newMove = createMove(moveType);

        // Setting the correct values
        newMove.setRoundId(roundId);

        aMoveRepository.save(newMove);

        return newMove;
    }

    public List<AMove> getMoves(Long gameId, Long playerId) {
        log.debug("list Moves of player: {}", playerId);

        // TODO find game, then find user, then return the list of all moves
        List<AMove> result = new ArrayList<>();

        // TODO change to find for user in game and then add
        //moveRepository.findAll().forEach(result::add);

        return result;
    }

    public AMove getMove(Long moveId) {
        log.debug("getMove: " + moveId);

        // Find the move in the DB
        /*Move move = moveRepository.findOne(moveId);

        if (move != null) {
            return move;
        }
        */
        // TODO Not return null instead something more meaning full
        return null;
    }
}
