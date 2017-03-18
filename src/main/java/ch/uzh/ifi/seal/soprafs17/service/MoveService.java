package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.entity.Move;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.repository.MoveRepository;
import ch.uzh.ifi.seal.soprafs17.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing moves.
 */
@Service
@Transactional
public class MoveService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final MoveRepository moveRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    @Autowired
    public MoveService(MoveRepository moveRepository, GameRepository gameRepository, UserRepository userRepository) {
        this.moveRepository = moveRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public Move createMove(){
        // TODO add everything that contains to a move
        Move newMove = null;

        log.debug("Created Information for User: {}", newMove);
        return newMove;
    }

    public List<Move> getMoves(Long gameId, Long playerId) {
        log.debug("list Moves of player: {}", playerId);

        // TODO find game, then find user, then return the list of all moves
        List<Move> result = new ArrayList<>();

        // TODO change to find for user in game and then add
        moveRepository.findAll().forEach(result::add);

        return result;
    }

    public Move addMove(Move move){
        log.debug("addMove: " + move);

        // TODO implementation of addMove()

        return null;
    }

    public Move getMove(Long moveId) {
        log.debug("getMove: " + moveId);

        // Find the move in the DB
        Move move = moveRepository.findOne(moveId);

        if (move != null) {
            return move;
        }

        // TODO Not return null instead something more meaning full
        return null;
    }
}
