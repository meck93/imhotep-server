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
    private final UserService userService;

    private boolean hasBeenCreated = false; // Just for the dummy data

    @Autowired
    public GameService(GameRepository gameRepository, UserService userService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
    }

    public Game createGame(String name, String owner, GameStatus status) {
        // Creating the Game and saving it to the Repository
        Game newGame = new Game();
        newGame.setName(name);
        newGame.setOwner(owner);
        newGame.setStatus(status);
        gameRepository.save(newGame);

        log.debug("Created Information for Game: {}", newGame);

        return newGame;
    }

    public void deleteGame(Long id) {
        //TODO check if game exists and then delete it
        Game game = gameRepository.findById(id);
        gameRepository.delete(id);

        log.debug("Deleted Game: {}", game);
    }

    public List<Game> listGames() {
        log.debug("listGames");

        /*
        * This block sends just dummy data to the client.
        * We will delete it as soon as the other functions
        * are implemented.
        */
        if (!hasBeenCreated) {
            Game game1 = createGame("dave","sepp",GameStatus.RUNNING);
            Game game2 = createGame("hans","paul",GameStatus.PENDING);
            Game game3 = createGame("fritz","peter",GameStatus.RUNNING);

            game1.setAmountOfPlayers(2);
            game2.setAmountOfPlayers(3);
            game3.setAmountOfPlayers(4);

            hasBeenCreated = true;
        }

        List<Game> result = new ArrayList<>();
        gameRepository.findAll().forEach(result::add);

        return result;
    }

    public String addGame(Game game, String userToken) {
        // TODO Implement the function which adds a Game
        log.debug("addGame: " + game);

        User owner = userService.getUserByToken(userToken);

        if (owner != null) {
            // Started a little bit
            game.setStatus(GameStatus.PENDING);
            game.setCurrentPlayer(1);

            game = gameRepository.save(game);

            return "/games" + "/" + game.getId();
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

        // TODO figure out where the check needs to happen (Service or Controller) & implement startGame() here

        Game game = gameRepository.findOne(gameId);
        // Same access question as above
        User owner = userService.getUserByToken(userToken);

        if (owner != null && game != null && game.getOwner().equals(owner.getUsername())) {
        }
    }

    public void stopGame(Long gameId, String userToken) {
        log.debug("stopGame: " + gameId);

        // TODO implement stopGame

        Game game = gameRepository.findOne(gameId);
        // Same access question as above
        User owner = userService.getUserByToken(userToken);

        if (owner != null && game != null && game.getOwner().equals(owner.getUsername())) {
            // TODO: Stop game in GameService
        }
    }

    /*public List<User> getPlayers(Long gameId) {
        log.debug("listPlayers");

        // TODO implement getPlayers in either userService or playerService

        Game game = gameRepository.findOne(gameId);
        if (game != null) {
            // Maybe as a List
            return game.getPlayers();
        }

        return null;
    }*/
}