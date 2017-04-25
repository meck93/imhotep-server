package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.game.StoneQuarry;
import ch.uzh.ifi.seal.soprafs17.entity.site.MarketPlace;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.BadRequestHttpException;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.service.card.MarketCardService;
import ch.uzh.ifi.seal.soprafs17.service.card.RoundCardService;
import ch.uzh.ifi.seal.soprafs17.service.game.RoundService;
import ch.uzh.ifi.seal.soprafs17.service.game.StoneQuarryService;
import ch.uzh.ifi.seal.soprafs17.service.site.BuildingSiteService;
import ch.uzh.ifi.seal.soprafs17.service.site.MarketPlaceService;
import ch.uzh.ifi.seal.soprafs17.service.user.SupplySledService;
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

    private final Logger log = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;
    private final BuildingSiteService buildingSiteService;
    private final RoundService roundService;
    private final RoundCardService roundCardService;
    private final MarketPlaceService marketPlaceService;
    private final StoneQuarryService stoneQuarryService;
    private final MarketCardService marketCardService;
    private final SupplySledService supplySledService;

    @Autowired
    public GameService(GameRepository gameRepository, BuildingSiteService buildingSiteService,
                       RoundService roundService, RoundCardService roundCardService, MarketPlaceService marketPlaceService,
                       StoneQuarryService stoneQuarryService, MarketCardService marketCardService,
                       SupplySledService supplySledService) {
        this.gameRepository = gameRepository;
        this.buildingSiteService = buildingSiteService;
        this.roundService = roundService;
        this.roundCardService = roundCardService;
        this.marketPlaceService = marketPlaceService;
        this.stoneQuarryService = stoneQuarryService;
        this.marketCardService = marketCardService;
        this.supplySledService = supplySledService;
    }
    /*
     * Implementation of the createGame method:
     * @Param Name - Name of the Game, Owner - Name of the User/Player
     */
    public Game createGame(String name, String owner) {

        if (gameRepository.findByName(name) != null){
            throw new BadRequestHttpException("A Game with name: " + name + " already exists!");
        }
        if (gameRepository.findByOwner(owner) != null){
            throw new BadRequestHttpException("A Game with the owner: " + owner + " already exists!");
        }

        // Creating the Game and saving it to the Repository
        Game newGame = new Game();
        newGame.setName(name);
        newGame.setOwner(owner);
        newGame.setNumberOfPlayers(0);
        newGame.setRoundCounter(0);
        newGame.setPlayers(new ArrayList<>());
        newGame.setRounds(new ArrayList<>());
        newGame.setBuildingSites(new ArrayList<>());
        newGame.setStatus(GameStatus.PENDING);

        gameRepository.save(newGame);

        log.debug("Created Information for Game: {}", newGame);

        return newGame;
    }

    public void addPlayer(Long gameId, Player player){
        log.debug("Adding the player to the Game");

        Game game = this.findById(gameId);

        boolean inGame = false;

        for (Player playerInGame : game.getPlayers()){
            if (player.getId().equals(playerInGame.getId())){
                inGame = true;
            }
        }

        if (!inGame){
            game.getPlayers().add(player);
            gameRepository.save(game);

        }
    }

    public void updateNrOfPlayers(Long gameId) {
        log.debug("Updating the Nr of Players in Game: " + gameId);

        Game game = this.findById(gameId);

        // setting the correct amount of players
        game.setNumberOfPlayers(game.getPlayers().size());

        // Readjust the Player Number & the Color if the Amount of Players has changed
        for (int i = 0; i < game.getPlayers().size(); i++){
            // Readjusting the PlayerNumbers
            game.getPlayers().get(i).setPlayerNumber(i+1);
            // assign the color according to the playerNumber
            switch (game.getPlayers().get(i).getPlayerNumber()) {
                case 1: game.getPlayerByPlayerNr(1).setColor(GameConstants.BLACK); break;
                case 2: game.getPlayerByPlayerNr(2).setColor(GameConstants.WHITE); break;
                case 3: game.getPlayerByPlayerNr(3).setColor(GameConstants.BROWN); break;
                case 4: game.getPlayerByPlayerNr(4).setColor(GameConstants.GRAY); break;
            }
        }

        gameRepository.save(game);
    }

    public void deleteGame(Long gameId) {
        Game game = this.findById(gameId);

        gameRepository.delete(game);

        log.debug("Deleted Game: {}", game);
    }

    public List<Game> listGames() {
        log.debug("listGames");

        List<Game> result = new ArrayList<>();
        gameRepository.findAll().forEach(result::add);

        return result;
    }

    public Game findById(Long gameId) {
        log.debug("getGame: " + gameId);

        Game game = gameRepository.findById(gameId);

        // Testing whether this game actually exists or not
        if (game == null) throw new NotFoundException(gameId, "Game");

        return game;
    }

    public int findNrOfPlayers(Long gameId) {
        log.debug("getAmountOfPlayers: " + gameId);

        return gameRepository.findNrOfPlayers(gameId);
    }

    /**
     * Returns the list of all players associated with Game: {GameId}
     */
    public List<Player> findPlayersByGameId(Long gameId) {
        log.debug("Find Players of Game: " + gameId);

        return gameRepository.findPlayersByGameId(gameId);
    }

    public int sizeOfQuarry(Long gameId, int playerNumber) {
        log.debug("Size of the Stone Quarry: " + gameId);

        Game game = this.findById(gameId);

        if (playerNumber > game.getNumberOfPlayers()){
            throw new NotFoundException(playerNumber, "PlayerNumber");
        }

        return game.getStoneQuarry().getStonesByPlayerNr(playerNumber).size();
    }

    /**
     * Handles the initialisation of the game board. Requires an already created but not
     * running game with more than 1 player
     * @param gameId
     * @pre game =/= NULL && state of game =/= RUNNING && game.amountOfPlayers =g= 2
     * @post For all round specific attributes of a game and round: attribute =/= NULL
     */
    public void startGame(Long gameId) {
        log.debug("startGame: " + gameId);

        // Initializing the game
        initializeGame(gameId);
        // Initializing the first round
        initializeRound(gameId);
    }

    /**
     * @param gameId
     * @post For all game specific attributes:  attribute =/= NULL
     */
    public void initializeGame(Long gameId){
        log.debug("Initializes the Game: " + gameId);

        // Initiates the game
        Game game = this.findById(gameId);
        int amountOfPlayers = this.findNrOfPlayers(gameId);

        // Creates all roundCards required for the Game
        roundCardService.createRoundCards(amountOfPlayers, gameId);

        // Create the marketCardDeck
        marketCardService.createMarketCardSet(gameId);

        // Create the marketPlace
        MarketPlace marketPlace = marketPlaceService.createMarketPlace(gameId);
        game.setMarketPlace(marketPlace);

        // Create the four BuildingSites for the game
        game.getBuildingSites().add(buildingSiteService.createBuildingSite(GameConstants.OBELISK, gameId));
        game.getBuildingSites().add(buildingSiteService.createBuildingSite(GameConstants.PYRAMID, gameId));
        game.getBuildingSites().add(buildingSiteService.createBuildingSite(GameConstants.TEMPLE, gameId));
        game.getBuildingSites().add(buildingSiteService.createBuildingSite(GameConstants.BURIAL_CHAMBER, gameId));

        // Create the stoneQuarry & fill it with Stones
        StoneQuarry stoneQuarry = stoneQuarryService.createStoneQuarry(game);
        stoneQuarryService.fillQuarry(stoneQuarry);

        // Filling the SupplySled
        supplySledService.fillSupplySleds(game);

        // Setting the Status to Running
        game.setStatus(GameStatus.RUNNING);

        // Setting the CurrentPlayer value to the playerNr of the 1. Player in the List of Players
        game.setCurrentPlayer(game.getPlayers().get(0).getPlayerNumber());
        game.setCurrentSubRoundPlayer(0);

        gameRepository.save(game);
    }

    public void initializeRound(Long gameId) {
        log.debug("Initializes Round for Game: " + gameId);

        Game game = this.findById(gameId);

        // Creating the first round of the game
        Round round = roundService.createRound(gameId, game);

        // Initializing the first round
        roundService.initializeRound(round.getId(), gameId);

        // Setting the roundCounter to the correct value
        game.setRoundCounter(round.getRoundNumber());

        // adding marketCards to the marketPlace
        List<MarketCard> fourCards = marketCardService.getMarketCardDeck(gameId);
        game.getMarketPlace().setMarketCards(fourCards);

        gameRepository.save(game);
    }

    public void stopGame(Long gameId) {
        log.debug("stopGame: " + gameId);

        Game game = this.findById(gameId);

        game.setStatus(GameStatus.FINISHED);

        gameRepository.save(game);
    }

    public void saveGame(Long gameId) {
        log.debug("saveGame: " + gameId);
        Game game = this.findById(gameId);
        gameRepository.save(game);
    }
}