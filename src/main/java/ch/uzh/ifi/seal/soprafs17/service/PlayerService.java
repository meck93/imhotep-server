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
    private final int START_POINTS = 0;

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
        int playerNumber = gameService.findAmountOfPlayers(gameId) + 1;
        User user = userService.getUser(userId);
        Long playerId = user.getId();

        if ((game != null) && (user != null) && (game.getPlayers().size() < GameConstants.MAX_PLAYERS)) {
            // create a new player entity
            Player newPlayer = new Player();
            newPlayer.setUser(user);
            newPlayer.setUsername(user.getUsername());
            newPlayer.setId(playerId);
            newPlayer.setGame(game);
            newPlayer.setMoves(new ArrayList<>());
            newPlayer.setPoints(START_POINTS);
            newPlayer.setPlayerNumber(playerNumber);

            // assign the color according to the playerNumber
            switch (playerNumber) {
                case 1: newPlayer.setColor(GameConstants.BLACK); break;
                case 2: newPlayer.setColor(GameConstants.WHITE); break;
                case 3: newPlayer.setColor(GameConstants.BROWN); break;
                case 4: newPlayer.setColor(GameConstants.GRAY); break;
            }

            // save the new entity in the database
            playerRepository.save(newPlayer);

            //add the SupplySled to the player
            newPlayer.setSupplySled(supplySledService.createSupplySled(newPlayer));

            // save the same player again after adding the SupplySled
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
        return null;
    }

    public List<Player> getPlayers(Long gameId) {
        log.debug("list Players of Game " + gameId);

        List<Player> result = new ArrayList<>();
        gameService.findPlayersByGameId(gameId).forEach(result::add);

        return result;
    }
}
