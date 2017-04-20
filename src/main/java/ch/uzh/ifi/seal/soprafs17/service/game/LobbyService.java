package ch.uzh.ifi.seal.soprafs17.service.game;


import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.BadRequestHttpException;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.user.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LobbyService {

    private final GameService gameService;
    private final PlayerService playerService;

    @Autowired
    public LobbyService(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
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
    public Game createGame(Game game, Long userId) {
        // Creates a new game
        Game newGame = gameService.createGame(game.getName(), game.getOwner());

        try {
            // Player joins the Game
            this.joinGame(newGame.getId(), userId);
        }

        // Remove the Game again if the User could not be converted into a Player
        catch (BadRequestHttpException badRequestException){
            // Delete the game
            gameService.deleteGame(newGame.getId());
            // Rethrow the exception
            throw badRequestException;
        }

        return newGame;
    }

    /*
     * Implementation of a User joining a Game. User -> Player. Player -> Joins Game.
     */
    public void joinGame(Long gameId, Long userId){
        // Creating a Player
        Player player = playerService.createPlayer(gameId, userId);

        // Initializing the new player
        playerService.initializePlayer(gameId, player);

        // Set the player in the Game
        this.gameService.addPlayer(gameId, player);

        // Update the Number of Players
        this.gameService.updateNrOfPlayers(gameId);
    }

    /*
     * Implementation of a Player removing himself from a Game.
     */
    public void removePlayer(Long gameId, Long playerId){
        // The player to be deleted
        Player toBeDeleted = this.playerService.findPlayerById(playerId);

        // Only the specified player with ID: playerId is going to be deleted
        this.playerService.deletePlayer(playerId);
        this.gameService.updateNrOfPlayers(gameId);

        // Check whether the player is the owner
        if (toBeDeleted.getPlayerNumber() == 1){
            // Deleting all players and the Game - if the owner's going to be deleted
            this.deleteGame(gameId);
        }
    }

    /*
     * Deleting a Game
     */
    public void deleteGame(Long gameId) {
        Game game = this.gameService.findById(gameId);

        // Deleting All Players in the Game
        game.getPlayers().forEach(player -> {
            if (player.getPlayerNumber() != 1){
                this.playerService.deletePlayer(player.getId());
                this.gameService.updateNrOfPlayers(gameId);
            }
        });

        // Deleting the game
        this.gameService.deleteGame(gameId);
    }

    /*
     * Starting the Game
     */
    public void startGame(Long gameId, Long playerId){
        Player player = playerService.findPlayerById(playerId);
        Game game = gameService.findById(gameId);

        // Check that the Game is not already running
        if (gameService.findById(gameId).getStatus() == GameStatus.RUNNING){
            throw new BadRequestHttpException("The game has already been started - currently running!");
        }
        // Check if the player is the owner
        if (!game.getOwner().equals(player.getUsername())){
            throw new BadRequestHttpException("The Player: " + playerId + " is not the owner of the Game: " + gameId);
        }
        // Check that the Minimum Amount of Players is fulfilled
        if (gameService.findNrOfPlayers(gameId) < GameConstants.MIN_PLAYERS){
            throw new BadRequestHttpException("The game has less than minimum of two Players, therefore, it cannot start yet");
        }

        // Starting the Game
        this.gameService.startGame(gameId);
    }
    /*
     * Stopping a Game
     */
    public void stopGame(Long gameId) {
        this.gameService.stopGame(gameId);
    }

}