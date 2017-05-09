package ch.uzh.ifi.seal.soprafs17.service.game;


import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.move.GetStonesMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlaceStoneMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlayCardMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.SailShipMove;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.BadRequestHttpException;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.move.MoveService;
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
    private final GameRepository gameRepository;
    private final MoveService moveService;

    @Autowired
    public LobbyService(GameService gameService, PlayerService playerService, GameRepository gameRepository, MoveService moveService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.gameRepository = gameRepository;
        this.moveService = moveService;
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

        // Check whether the player is the owner
        if (toBeDeleted.getPlayerNumber() == 1){
            // Deleting all players and the Game - if the owner's going to be deleted
            this.deleteGame(gameId);
        }
        else {
            // Only the specified player with ID: playerId is going to be deleted
            this.playerService.deletePlayer(playerId);
            this.gameService.updateNrOfPlayers(gameId);
        }
    }

    /*
     * Deleting a Game
     */
    public void deleteGame(Long gameId) {
        Game game = this.gameService.findById(gameId);

        // Deleting All Players in the Game
        game.getPlayers().forEach(player -> this.playerService.deletePlayer(player.getId()));
        // Setting the Game to finished
        this.gameService.stopGame(game.getId());
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

    public void fastForward(Long gameId, Long playerId) {
        Game game = gameService.findById(gameId);
        Player player = playerService.findPlayerById(playerId);

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

        this.gameService.initializeGame(gameId);

        for (int i = 1; i <= 5; i++){
            // Creating the six rounds
            this.gameService.initializeRound(gameId);
        }

        game = this.gameService.findById(gameId);

        // Creating 4 Dummy Moves for the MoveLog
        GetStonesMove move1 = new GetStonesMove(GameConstants.GET_STONES);
        move1.setGameId(gameId);
        move1.setPlayerNr(1);
        move1.setRoundNr(5);

        PlayCardMove move2 = new PlayCardMove(GameConstants.PLAY_CARD);
        move2.setGameId(gameId);
        move2.setPlayerNr(2);
        move2.setRoundNr(5);
        move2.setMarketCardType(MarketCardType.HAMMER);
        move2.setShipId(18L);
        move2.setPlaceOnShip(1);
        move2.setCardId(39L);

        PlaceStoneMove move3 = new PlaceStoneMove(GameConstants.PLACE_STONE);
        move3.setGameId(gameId);
        move3.setRoundNr(5);
        move3.setPlayerNr(1);
        move3.setPlaceOnShip(2);
        move3.setShipId(18L);

        SailShipMove move4 = new SailShipMove(GameConstants.SAIL_SHIP);
        move4.setGameId(gameId);
        move4.setRoundNr(5);
        move4.setPlayerNr(2);
        move4.setShipId(18L);
        move4.setTargetSiteId(game.getBuildingSite("OBELISK").getId());

        // Logging the four dummy moves
        this.moveService.logMove(move1, game);
        this.moveService.logMove(move2, game);
        this.moveService.logMove(move3, game);
        this.moveService.logMove(move4, game);

        // Setting the roundCounter to the correct value
        this.gameService.initializeRound(gameId);

        this.gameRepository.save(game);

        game = this.gameService.findById(gameId);

        //Set stones on burialChamber
        Stone s1 = new Stone();
        Stone s2 = new Stone();
        Stone s3 = new Stone();
        Stone s4 = new Stone();
        Stone s5 = new Stone();
        Stone s6 = new Stone();
        Stone s7 = new Stone();
        Stone s8 = new Stone();
        Stone s9 = new Stone();
        Stone s10 = new Stone();
        Stone s11 = new Stone();
        Stone s12 = new Stone();

        List<Stone> ls = game.getBuildingSite(GameConstants.BURIAL_CHAMBER).getStones();

        s1.setColor(GameConstants.WHITE);
        s2.setColor(GameConstants.WHITE);
        s3.setColor(GameConstants.WHITE);
        s4.setColor(GameConstants.BLACK);
        s5.setColor(GameConstants.BLACK);
        s6.setColor(GameConstants.WHITE);
        s7.setColor(GameConstants.WHITE);
        s8.setColor(GameConstants.BLACK);
        s9.setColor(GameConstants.WHITE);
        s10.setColor(GameConstants.BLACK);
        s11.setColor(GameConstants.BLACK);
        s12.setColor(GameConstants.BLACK);

        ls.add(s1);
        ls.add(s2);
        ls.add(s3);
        ls.add(s4);
        ls.add(s5);
        ls.add(s6);
        ls.add(s7);
        ls.add(s8);
        ls.add(s9);
        ls.add(s10);
        ls.add(s11);
        ls.add(s12);

        game.getBuildingSite(GameConstants.BURIAL_CHAMBER).setStones(ls);

        //Set stones on pyramid
        Stone s13 = new Stone();
        Stone s14 = new Stone();
        Stone s15 = new Stone();
        Stone s16 = new Stone();
        Stone s17 = new Stone();
        Stone s18 = new Stone();
        Stone s19 = new Stone();

        List<Stone> ls2 = game.getBuildingSite(GameConstants.PYRAMID).getStones();

        s13.setColor(GameConstants.WHITE);
        s14.setColor(GameConstants.WHITE);
        s15.setColor(GameConstants.WHITE);
        s16.setColor(GameConstants.BLACK);
        s17.setColor(GameConstants.BLACK);
        s18.setColor(GameConstants.WHITE);
        s19.setColor(GameConstants.WHITE);

        ls2.add(s13);
        ls2.add(s14);
        ls2.add(s15);
        ls2.add(s16);
        ls2.add(s17);
        ls2.add(s18);
        ls2.add(s19);

        game.getBuildingSite(GameConstants.PYRAMID).setStones(ls2);

        //Set stones on temple
        Stone s20 = new Stone();
        Stone s21 = new Stone();
        Stone s22 = new Stone();
        Stone s23 = new Stone();

        List<Stone> ls3 = game.getBuildingSite(GameConstants.TEMPLE).getStones();

        s20.setColor(GameConstants.WHITE);
        s21.setColor(GameConstants.WHITE);
        s22.setColor(GameConstants.WHITE);
        s23.setColor(GameConstants.BLACK);

        ls3.add(s20);
        ls3.add(s21);
        ls3.add(s22);
        ls3.add(s23);

        game.getBuildingSite(GameConstants.TEMPLE).setStones(ls3);

        //Set stones on Obelisk
        Stone s24 = new Stone();
        Stone s25 = new Stone();
        Stone s26 = new Stone();
        Stone s27 = new Stone();
        Stone s28 = new Stone();
        Stone s29 = new Stone();
        Stone s30 = new Stone();

        List<Stone> ls4 = game.getBuildingSite(GameConstants.OBELISK).getStones();

        s24.setColor(GameConstants.WHITE);
        s25.setColor(GameConstants.WHITE);
        s26.setColor(GameConstants.WHITE);
        s27.setColor(GameConstants.BLACK);
        s28.setColor(GameConstants.BLACK);
        s29.setColor(GameConstants.WHITE);
        s30.setColor(GameConstants.WHITE);

        ls4.add(s24);
        ls4.add(s25);
        ls4.add(s26);
        ls4.add(s27);
        ls4.add(s28);
        ls4.add(s29);
        ls4.add(s30);

        game.getBuildingSite(GameConstants.OBELISK).setStones(ls4);

        //Removing the stones from the supply sled
        List<Stone> stoneQuarry1 = game.getStoneQuarry().getBlackStones();
        List<Stone> stoneQuarry2 = game.getStoneQuarry().getWhiteStones();

        // Correcting the Stones on the StoneQuarry
        for (int i = 0; i<10;i++) {
            stoneQuarry1.remove(0);
        }

        for (int i = 0; i<19;i++) {
            stoneQuarry2.remove(0);
        }

        game.getStoneQuarry().setBlackStones(stoneQuarry1);
        game.getStoneQuarry().setWhiteStones(stoneQuarry2);

        //Setting the handCards
        MarketCard chisel = new MarketCard();
        MarketCard lever = new MarketCard();
        MarketCard sail = new MarketCard();
        MarketCard statue1 = new MarketCard();
        MarketCard statue2 = new MarketCard();
        MarketCard statue3 = new MarketCard();
        MarketCard statue4 = new MarketCard();
        MarketCard burialDeco = new MarketCard();

        //Setting marketCard color
        chisel.setColor(GameConstants.BLUE);
        lever.setColor(GameConstants.BLUE);
        sail.setColor(GameConstants.BLUE);
        statue1.setColor(GameConstants.VIOLET);
        statue2.setColor(GameConstants.VIOLET);
        statue3.setColor(GameConstants.VIOLET);
        statue4.setColor(GameConstants.VIOLET);
        burialDeco.setColor(GameConstants.GREEN);

        //Setting marketCardType
        chisel.setMarketCardType(MarketCardType.CHISEL);
        lever.setMarketCardType(MarketCardType.LEVER);
        sail.setMarketCardType(MarketCardType.SAIL);
        statue1.setMarketCardType(MarketCardType.STATUE);
        statue2.setMarketCardType(MarketCardType.STATUE);
        statue3.setMarketCardType(MarketCardType.STATUE);
        statue4.setMarketCardType(MarketCardType.STATUE);
        burialDeco.setMarketCardType(MarketCardType.BURIAL_CHAMBER_DECORATION);

        //Setting marketCards gameId
        chisel.setGameId(gameId);
        lever.setGameId(gameId);
        sail.setGameId(gameId);
        statue1.setGameId(gameId);
        statue2.setGameId(gameId);
        statue3.setGameId(gameId);
        statue4.setGameId(gameId);
        burialDeco.setGameId(gameId);

        List<MarketCard> handcardsP1 = game.getPlayerByPlayerNr(1).getHandCards();
        List<MarketCard> handcardsP2 = game.getPlayerByPlayerNr(2).getHandCards();

        // Add the handCards of the Player 1
        handcardsP1.add(sail);
        handcardsP1.add(lever);
        handcardsP1.add(burialDeco);

        // Add the handCards of the Player 2
        handcardsP2.add(statue1);
        handcardsP2.add(statue2);
        handcardsP2.add(statue3);
        handcardsP2.add(statue4);
        handcardsP2.add(chisel);

        game.getPlayerByPlayerNr(1).setHandCards(handcardsP1);
        game.getPlayerByPlayerNr(2).setHandCards(handcardsP2);

        //Setting player points
        int[] scoreP1 = game.getPlayerByPlayerNr(1).getPoints();
        int[] scoreP2 = game.getPlayerByPlayerNr(2).getPoints();

        //Setting temple score
        scoreP1[1] = 1;
        scoreP2[1] = 3;

        //Setting pyramid score
        scoreP1[0] = 6;
        scoreP2[0] = 11;

        //total points
        scoreP1[5] = scoreP1[1] + scoreP1[0];
        scoreP2[5] = scoreP2[1] + scoreP2[0];

        game.getPlayerByPlayerNr(1).setPoints(scoreP1);
        game.getPlayerByPlayerNr(2).setPoints(scoreP2);

        gameRepository.save(game);
    }
}