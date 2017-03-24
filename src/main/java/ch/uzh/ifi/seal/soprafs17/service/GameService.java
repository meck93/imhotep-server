package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.entity.Player;
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
    /*
     * Adds an existing player to the game
     */
    public String addPlayer(Long gameId, Player player){
        Game game = gameRepository.findById(gameId);
        game.getPlayers().add(player);
        game.setAmountOfPlayers(game.getAmountOfPlayers() + 1);
        gameRepository.save(game);

        log.debug("Added Player with playerId: " + player.getId() + " to the game with gameId: " + gameId);

        return "games" + "/" + gameId + "/players/" + (game.getPlayers().size() - 1);
    }

    public Game findById(Long gameId) {
        // TODO: Excepetion handling if not found
        log.debug("getGame: " + gameId);
        return gameRepository.findById(gameId);
    }

    // TODO: Change parameter to player specific not user specific
    public void startGame(Long gameId, Long playerId) {
        log.debug("startGame: " + gameId);

        // TODO Implement the check & implement startGame() here
        Game game = gameRepository.findOne(gameId);
        /*Player player = playerService.
        // gameService cannot call the playerService -> serializable loop

        // TODO: check that the player which started the game is the owner of the game
        if (owner != null && game != null && game.getOwner().equals()) {

        }
        */
    }

    public void stopGame(Long gameId, Long playerId) {
        log.debug("stopGame: " + gameId);

        // TODO implement stopGame
        Game game = gameRepository.findOne(gameId);
        // Same access question as above
        /*User owner = userService.getUserByToken(userToken);

        if (owner != null && game != null && game.getOwner().equals(owner.getUsername())) {
            // TODO: Stop game in GameService
        }*/

        //TODO: Delete game after all Game has been stopped & all players have been removed
    }
}