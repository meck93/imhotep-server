package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.game.StoneQuarry;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.entity.site.MarketPlace;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.BadRequestHttpException;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.service.card.MarketCardService;
import ch.uzh.ifi.seal.soprafs17.service.card.RoundCardService;
import ch.uzh.ifi.seal.soprafs17.service.game.RoundService;
import ch.uzh.ifi.seal.soprafs17.service.game.StoneQuarryService;
import ch.uzh.ifi.seal.soprafs17.service.scoring.ScoringService;
import ch.uzh.ifi.seal.soprafs17.service.site.BuildingSiteService;
import ch.uzh.ifi.seal.soprafs17.service.site.MarketPlaceService;
import ch.uzh.ifi.seal.soprafs17.service.user.SupplySledService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
    private final ScoringService scoringService;

    @Autowired
    public GameService(GameRepository gameRepository, BuildingSiteService buildingSiteService,
                       RoundService roundService, RoundCardService roundCardService, MarketPlaceService marketPlaceService,
                       StoneQuarryService stoneQuarryService, MarketCardService marketCardService,
                       SupplySledService supplySledService, ScoringService scoringService) {
        this.gameRepository = gameRepository;
        this.buildingSiteService = buildingSiteService;
        this.roundService = roundService;
        this.roundCardService = roundCardService;
        this.marketPlaceService = marketPlaceService;
        this.stoneQuarryService = stoneQuarryService;
        this.marketCardService = marketCardService;
        this.supplySledService = supplySledService;
        this.scoringService = scoringService;
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

    public void deleteGame(Long gameId) {
        Game game = gameRepository.findById(gameId);

        if (game == null) throw new NotFoundException(gameId, "Game");

        gameRepository.delete(game);

        log.debug("Deleted Game: {}", game);
    }

    public void setNrOfPlayers(Long gameId, int nrOfPlayers) {
        log.debug("Nr of Players: " + nrOfPlayers + " of Game: " + gameId);

        Game game = gameRepository.findById(gameId);
        game.setNumberOfPlayers(nrOfPlayers);

        gameRepository.save(game);
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
        log.debug("getPlayersByGameId: " + gameId);

        return gameRepository.findPlayersByGameId(gameId);
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

    /*
     * Test method to score the all Building Sites of a Game
     */
    public synchronized void score(Long gameId){

        Game game = this.findById(gameId);

        List<BuildingSite> buildingSites = Collections.synchronizedList(new ArrayList<>());
        buildingSites.addAll(game.getBuildingSites());

        synchronized (buildingSites){
            Iterator<BuildingSite> it = buildingSites.iterator();
            while (it.hasNext()){
                BuildingSite buildingSite = it.next();
                this.scoringService.score(game, buildingSite.getSiteType());
            }
        }
    }

    /*
     * Test method to score a specific BuildingSite of a Game
     */
    public synchronized void score(Long gameId, String siteType){

        Game game = this.findById(gameId);

        this.scoringService.score(game, siteType);
    }
}