package ch.uzh.ifi.seal.soprafs17.service;

/**
 * Created by Dave on 21.03.2017.
 */


import ch.uzh.ifi.seal.soprafs17.entity.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LobbyService {

    private final Logger log = LoggerFactory.getLogger(LobbyService.class);
    private final GameService gameService;
    private final PlayerService playerService;
    private final UserService userService;

    @Autowired
    public LobbyService(GameService gameService, PlayerService playerService, UserService userService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.userService = userService;
        }
    /*
     * This method returns a list of all games
     */
    public List<Game> listGames(){
        return gameService.listGames();
    }

    /*
     * Calls the gameService to create a Game
     */
    public Game createGame(Game game, Long userId){
        // TODO: Add check whether the user exists, whether the user is already in game
        // Creates a new game
        Game newGame = gameService.createGame(game.getName(), game.getOwner());
        // Creates a new player from the user who created the game
        String newPlayer = playerService.initializePlayer(newGame.getId(), userId);

        return newGame;
    }
    /*
     * Implementation of a User joining a Game. User -> Player. Player -> Joins Game.
     */
    public String joinGame(Long gameId, Long userId){
        return playerService.initializePlayer(gameId, userId);
    }
}
