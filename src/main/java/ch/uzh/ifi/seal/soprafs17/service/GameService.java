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

    @Autowired
    public GameService(GameRepository gameRepository, UserService userService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
    }
    /*
     * Implementation of the createGame method:
     * @Param Name - Name of the Game, Owner - Name of the User/Player
     */
    public Game createGame(String name, String owner) {
        // Creating the Game and saving it to the Repository
        Game newGame = new Game();
        newGame.setName(name);
        newGame.setOwner(owner);
        newGame.setStatus(GameStatus.PENDING);
        newGame.setPlayers(new ArrayList<>());
        newGame.setAmountOfPlayers(0);

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

        List<Game> result = new ArrayList<>();
        gameRepository.findAll().forEach(result::add);

        return result;
    }

    public Game findById(Long gameId) {
        // TODO: Excepetion handling if not found
        return gameRepository.findById(gameId);
    }

    // TODO: Determine if this function is still needed
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

    public Game getGameById(Long gameId) {
        log.debug("getGame: " + gameId);
        return gameRepository.findById(gameId);
    }

    // TODO: Change parameter to player specific not user specific
    public void startGame(Long gameId, String userToken) {
        log.debug("startGame: " + gameId);

        // TODO figure out where the check needs to happen (Service or Controller) & implement startGame() here

/*        Game game = gameRepository.findOne(gameId);
        // Same access question as above
        User owner = userService.getUserByToken(userToken);

        if (owner != null && game != null && game.getOwner().equals(owner.getUsername())) {
        }*/
    }

    // TODO: Change parameter to player specific not user specific
    public void stopGame(Long gameId, String userToken) {
        log.debug("stopGame: " + gameId);

        /*// TODO implement stopGame

        Game game = gameRepository.findOne(gameId);
        // Same access question as above
        User owner = userService.getUserByToken(userToken);

        if (owner != null && game != null && game.getOwner().equals(owner.getUsername())) {
            // TODO: Stop game in GameService
        }*/

        //TODO: Delete game after all Game has been stoped & all players have been removed
    }
}