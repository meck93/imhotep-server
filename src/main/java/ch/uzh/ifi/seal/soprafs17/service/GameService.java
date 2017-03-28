package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.constant.SiteType;
import ch.uzh.ifi.seal.soprafs17.entity.*;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
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
    private final BuildingSiteService buildingSiteService;
    private final RoundService roundService;
    private final RoundCardService roundCardService;

    @Autowired
    public GameService(GameRepository gameRepository, BuildingSiteService buildingSiteService, RoundService roundService, RoundCardService roundCardService) {
        this.gameRepository = gameRepository;
        this.buildingSiteService = buildingSiteService;
        this.roundService = roundService;
        this.roundCardService = roundCardService;
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
        newGame.setRounds(new ArrayList<>());

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
        // adds player to the game
        game.getPlayers().add(player);
        // sets the correct amount of players
        int amountOfPlayers = game.getAmountOfPlayers() + 1;
        game.setAmountOfPlayers(amountOfPlayers);
        gameRepository.save(game);

        log.debug("Added Player with playerId: " + player.getId() + " to the game with gameId: " + gameId);

        return "games" + "/" + gameId + "/players/" + amountOfPlayers;
    }

    public Game findById(Long gameId) {
        // TODO: Excepetion handling if not found
        log.debug("getGame: " + gameId);
        return gameRepository.findById(gameId);
    }

    public int findAmountOfPlayers(Long gameId) {
        log.debug("getAmountOfPlayers: " + gameId);
        return gameRepository.findAmountOfPlayers(gameId);
    }

    public List<Player> findPlayersByGameId(Long gameId) {
        log.debug("getPlayersByGameId: " + gameId);
        return gameRepository.findPlayersByGameId(gameId);
    }

    // TODO: Change parameter to player specific not user specific
    public void startGame(Long gameId, Long playerId) {
        log.debug("startGame: " + gameId);
        // check if game exists and then call init and the player is allowd to start it ===> throw 412 or access denied
        gameInit(gameId);

        Game game = gameRepository.findById(gameId);

        Round round = roundService.createRound(gameId);
        List<Round> rounds = game.getRounds();
        rounds.add(round);
        game.setRounds(rounds);

        gameRepository.save(game);



        // TODO Implement the check & implement startGame() here
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
        /*String owner = game.getOwner();

        // Same access question as above
        // User owner = userService.getUserByToken(userToken);

        if (owner != null && game != null && game.getOwner().equals(owner.getUsername())) {
            // TODO: Stop game in GameService
        }*/
    }
      
    public void gameInit(Long gameId){

        Game game = gameRepository.findById(gameId);
        int amountOfPlayers = game.getAmountOfPlayers();

        /*
        MarketPlace market = new MarketPlace();
        StoneQuarry stoneQuarry = new StoneQuarry();
        */
        // implement this for every site
        game.setObelisk(buildingSiteService.createBuildingSite(SiteType.OBELISK, gameId));

        game.setRoundCounter(1);
        game.setStatus(GameStatus.RUNNING);
        gameRepository.save(game);

        // TODO: Tell roundCardService to create roundCards for this game
        roundCardService.createRoundCards(amountOfPlayers, gameId);
        /*game.setStoneQuarry(stoneQuarry);*/
    }
}