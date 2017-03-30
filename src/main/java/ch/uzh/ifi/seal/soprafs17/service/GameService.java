package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.constant.SiteType;
import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.entity.Player;
import ch.uzh.ifi.seal.soprafs17.entity.Round;
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
    private final BuildingSiteService buildingSiteService;
    private final RoundService roundService;
    private final RoundCardService roundCardService;
    private final StoneQuarryService stoneQuarryService;
    private final MarketPlaceService marketPlaceService;

    @Autowired
    public GameService(GameRepository gameRepository, BuildingSiteService buildingSiteService,
                       RoundService roundService, RoundCardService roundCardService,
                       StoneQuarryService stoneQuarryService, MarketPlaceService marketPlaceService) {
        this.gameRepository = gameRepository;
        this.buildingSiteService = buildingSiteService;
        this.roundService = roundService;
        this.roundCardService = roundCardService;
        this.stoneQuarryService = stoneQuarryService;
        this.marketPlaceService = marketPlaceService;
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

    /**
     * Handles the initialisation of the game board. Requires an already created but not
     * running game with more than 1 player
     * @param gameId
     * @param playerId
     * @pre game, player =/= NULL && state of game =/= RUNNING && game.amountOfPlayers =g= 2
     * @post For all round specific attributes of a game and round: attribute =/= NULL
     */
    public void startGame(Long gameId, Long playerId) {
        log.debug("startGame: " + gameId);

        // Check for preconditions reserved => is the player the owner? etc.
        Game game = gameRepository.findById(gameId);
        gameInit(gameId);

        Round round = roundService.createRound(gameId, game);
        List<Round> rounds = game.getRounds();
        rounds.add(round);
        game.setRounds(rounds);

        //roundService.initializeRound()

        gameRepository.save(game);
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


    /**
     * @param gameId
     * @post For all game specific attributes:  attribute =/= NULL
     */
    public void gameInit(Long gameId){
        // Initiates the game
        Game game = gameRepository.findById(gameId);
        int amountOfPlayers = gameRepository.findAmountOfPlayers(gameId);
        // Creates all roundCards required for the Game
        roundCardService.createRoundCards(amountOfPlayers, gameId);
        // Create the marketPlace
        game.setMarketPlace(marketPlaceService.createMarketPlace(gameId));
        // Create the supplySled
        game.setStoneQuarry(stoneQuarryService.createStoneQuarry(gameId,amountOfPlayers));
        // Create the four BuildingSites for the game
        game.setObelisk(buildingSiteService.createBuildingSite(SiteType.OBELISK));
        game.setPyramid(buildingSiteService.createBuildingSite(SiteType.PYRAMID));
        game.setTemple(buildingSiteService.createBuildingSite(SiteType.TEMPLE));
        game.setBurialChamber(buildingSiteService.createBuildingSite(SiteType.BURIAL_CHAMBER));

        // settings for the initial round
        game.setRoundCounter(1);
        game.setStatus(GameStatus.RUNNING);
        gameRepository.save(game);
    }
}
