package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.entity.User;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        gameRepository.save(newGame);
        log.debug("Created Information for Game: {}", newGame);
        return newGame;
    }

    public void deleteGame(Long id) {
        Game game = gameRepository.findById(id); //TODO check if game exists
        gameRepository.delete(id);
        log.debug("Deleted Game: {}", game);
    }

    public List<Game> listGames() {
        log.debug("listGames");

        List<Game> result = new ArrayList<>();
        gameRepository.findAll().forEach(result::add);

        return result;
    }

    public String addGame(Game game, String userToken) {
        log.debug("addGame: " + game);

        // How do we check the user? Does the userService need to do this?
        // Or can the gameService directly do this?
        User owner = userRepository.findByToken(userToken);

        if (owner != null) {
            // TODO Mapping into Game
            // Started a little bit
            game.setStatus(GameStatus.PENDING);
            game.setCurrentPlayer(1);

            game = gameRepo.save(game);

            return CONTEXT + "/" + game.getId();
        }

        // We need to change this: should not return NULL
        return null;
    }

    public Game getGame(Long gameId) {
        log.debug("getGame: " + gameId);
        return gameRepository.findOne(gameId);
    }

    public void startGame(Long gameId, String userToken) {
        log.debug("startGame: " + gameId);

        Game game = gameRepository.findOne(gameId);
        // Same access question as above
        User owner = userRepo.findByToken(userToken);

        if (owner != null && game != null && game.getOwner().equals(owner.getUsername())) {
            // TODO: Start game in GameService
        }
    }

    public void stopGame(Long gameId, String userToken) {
        log.debug("stopGame: " + gameId);

        Game game = gameRepository.findOne(gameId);
        // Same access question as above
        User owner = userRepo.findByToken(userToken);

        if (owner != null && game != null && game.getOwner().equals(owner.getUsername())) {
            // TODO: Stop game in GameService
        }
    }

    public List<User> getPlayers(Long gameId) {
        log.debug("listPlayers");

        Game game = gameRepository.findOne(gameId);
        if (game != null) {
            // Maybe as a List
            return game.getPlayers();

            //Or otherwise from the playerRepository
            return playerRepository.getPlayers(gameId);
        }

        return null;
    }
}