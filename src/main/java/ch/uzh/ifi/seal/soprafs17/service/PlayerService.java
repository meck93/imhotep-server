package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
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
    private final SupplySledService supplySledService;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, GameService gameService, UserService userService, SupplySledService supplySledService) {
        this.playerRepository = playerRepository;
        this.gameService = gameService;
        this.userService = userService;
        this.supplySledService = supplySledService;
    }
    /*
     * Creates a new player in the database
     * @Param gameId - to identify the correct game, userToken - so the player can be associated to a specific user
     */
    public Player createPlayer(Long gameId, Long userId) {
        log.debug("creating Player from User with userId: " + userId);

        Game game = gameService.findById(gameId);
        int playerNumber = gameService.findNrOfPlayers(gameId) + 1;

        User user = userService.getUser(userId);
        Long playerId = user.getId();

        if (game != null && game.getPlayers().size() < GameConstants.MAX_PLAYERS) {
            // create a new player entity
            Player newPlayer = new Player();
            newPlayer.setUser(user);
            newPlayer.setUsername(user.getUsername());
            newPlayer.setId(playerId);
            newPlayer.setGame(game);
            newPlayer.setPlayerNumber(playerNumber);

            // save the new entity in the database
            playerRepository.save(newPlayer);

            return newPlayer;
        }
        else {
            log.error("Error creating player with userId: " + userId);
            // TODO: Exception handling if creating a player doesn't work
            return null;
        }
    }

    public String initializePlayer(Long gameId, Long userId) {
        log.debug("creating Player from User with userId: " + userId);

        Player player = createPlayer(gameId, userId);

        // Initializing the Move List and the StartPoints
        player.setMoves(new ArrayList<>());
        player.setPoints(GameConstants.START_POINTS);

        // assign the color according to the playerNumber
        switch (player.getPlayerNumber()) {
            case 1: player.setColor(GameConstants.BLACK); break;
            case 2: player.setColor(GameConstants.WHITE); break;
            case 3: player.setColor(GameConstants.BROWN); break;
            case 4: player.setColor(GameConstants.GRAY); break;
        }

        // Saving the changes to the DB
        playerRepository.save(player);

        //add the SupplySled to the player
        supplySledService.createSupplySled(player);

        // Set the correct amountOfPlayers
        gameService.setNrOfPlayers(gameId, player.getPlayerNumber());

        return "games" + "/" + gameId + "/players/" + player.getPlayerNumber();
    }

    public Player getPlayer(Long gameId, Long playerNr) {
        log.debug("getPlayer: " + playerNr + "of Game: " + gameId);

        List<Player> players = gameService.findPlayersByGameId(gameId);
        // Getting the Player at position of playerNr
        Player player = players.get(playerNr.intValue() - 1);

        // Verifying that the player exists in the game
        if (player != null && player.getGame().getId().equals(gameId)){
            return player;
        }

        // TODO Exception handling if player doesn't exist
        log.error("Couldn't find PlayerNr: " + playerNr + " in Game: " + gameId);
        return null;
    }

    public List<Player> getPlayers(Long gameId) {
        log.debug("list Players of Game " + gameId);

        List<Player> result = new ArrayList<>();
        gameService.findPlayersByGameId(gameId).forEach(result::add);

        return result;
    }
}
