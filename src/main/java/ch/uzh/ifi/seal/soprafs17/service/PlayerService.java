package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.entity.Player;
import ch.uzh.ifi.seal.soprafs17.entity.User;
import ch.uzh.ifi.seal.soprafs17.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Service class for managing players.
 */
@Service
@Transactional
public class PlayerService {

    private final Logger log = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;

    private final GameService gameService;
    private final UserService userService;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, GameService gameService, UserService userService) {
        this.playerRepository = playerRepository;
        this.gameService = gameService;
        this.userService = userService;
    }
    /*
     * Creates a new player in the database
     * @Param gameId - to identify the correct game, userToken - so the player can be associated to a specific user
     */
    public Player createPlayer(Long gameId, Long userId) {
        log.debug("creating Player from User with userId: " + userId);

        Game game = gameService.findById(gameId);
        User user = userService.getUser(userId);

        if ((game != null) && (user != null) && (game.getPlayers().size() < GameConstants.MAX_PLAYERS)) {
            Player newPlayer = new Player();
            newPlayer.setUser(user);
            newPlayer.setId(user.getId());
            newPlayer.setGame(game);
            // Color destroys the JSON response
            //newPlayer.setColor(Color.BLACK);
            newPlayer.setMoves(new ArrayList<>());
            newPlayer.setPoints(0);

            playerRepository.save(newPlayer);

            return newPlayer;
        }
        else {
            log.error("Error creating player with userId: " + userId);
            // TODO: Exception handling if creating a player doesn't work
            return null;
        }
    }

    /*
     * Adds an existing player to an existing game.
     * @Param gameId, playerId
     */
    // TODO: The relation between the game and the player doesn't work yet
    public String addPlayer(Long gameId, Long playerId){
        Player player = playerRepository.findOne(playerId);
        return gameService.addPlayer(gameId, player);
    }

    public Player getPlayer(Long gameId, Long playerId) {
        log.debug("getPlayer: " + gameId);

        Player player = playerRepository.findOne(playerId);

        // Verifying that the player exists in the game
        if (player != null && player.getGame().getId().equals(gameId)){
            return player;
        }

        // TODO Exception handling if player doesn't exist
        return null;
    }

    public Player findById(Long playerId){
        Player player = playerRepository.findOne(playerId);

        if (player != null){
            return player;
        }

        //TODO: Exception handling when player doesn't exist
        return null;
    }

    public List<Player> getPlayers(Long gameId) {
        log.debug("listPlayers of Game " + gameId);

        List<Player> result = new ArrayList<>();
        playerRepository.findAll().forEach(result::add);

        return result;
    }
}
