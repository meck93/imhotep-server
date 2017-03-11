package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implements the logic of the game.
 *
 * Created by Moritz Eck on 11.03.2017.
 */
@Service
@Transactional
public class GameService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame(String name, String owner, GameStatus status) {

        Game newGame = new Game();
        newGame.setName(name);
        newGame.setOwner(owner);
        newGame.setStatus(status);
        log.debug("Created Information for Game: {}", newGame);
        return newGame;
    }

    public void deleteGame(Long id) {
        Game game = gameRepository.findById(id); //TODO check if game exists
        gameRepository.delete(id);
        log.debug("Deleted Game: {}", game);
    }

}