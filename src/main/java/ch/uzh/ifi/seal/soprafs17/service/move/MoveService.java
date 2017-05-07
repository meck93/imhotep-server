package ch.uzh.ifi.seal.soprafs17.service.move;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.GetCardMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlayCardMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.SailShipMove;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.exceptions.ApplyMoveException;
import ch.uzh.ifi.seal.soprafs17.exceptions.InternalServerException;
import ch.uzh.ifi.seal.soprafs17.exceptions.MoveValidationException;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.BadRequestHttpException;
import ch.uzh.ifi.seal.soprafs17.repository.AMoveRepository;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.game.RoundService;
import ch.uzh.ifi.seal.soprafs17.service.move.rule.RuleManager;
import ch.uzh.ifi.seal.soprafs17.service.move.validation.ValidationManager;
import ch.uzh.ifi.seal.soprafs17.service.scoring.ScoringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static ch.uzh.ifi.seal.soprafs17.GameConstants.*;

/**
 * Service class for managing moves.
 */
@Service
@Transactional
public class MoveService {

    private final Logger log = LoggerFactory.getLogger(MoveService.class);

    private final GameRepository gameRepository;
    private final ValidationManager validationManager;
    private final RuleManager ruleManager;
    private final GameService gameService;
    private final RoundService roundService;
    private final ScoringService scoringService;
    private final AMoveRepository aMoveRepository;

    @Autowired
    public MoveService(GameRepository gameRepository, ValidationManager validationManager, RuleManager ruleManager, GameService gameService, RoundService roundService, ScoringService scoringService, AMoveRepository aMoveRepository) {
        this.gameRepository = gameRepository;
        this.validationManager = validationManager;
        this.ruleManager = ruleManager;
        this.gameService = gameService;
        this.roundService = roundService;
        this.scoringService = scoringService;
        this.aMoveRepository = aMoveRepository;
    }

    public synchronized void validateAndApply(AMove move) throws BadRequestHttpException, InternalServerException {
        log.debug("Validates and applies the Move: {}", move.getMoveType());

        Game game = gameRepository.findById(move.getGameId());

        try {
            // Validate the move if it can be applied
            this.validationManager.validate(move, game);
        }
        catch (MoveValidationException moveValException) {
            throw new BadRequestHttpException(moveValException);
        }

        // Logging the move - saving it into the database including the description
        this.logMove(move, game);

        try {
            // Applying the Move to the Game
            game = ruleManager.applyRules(move, game);
        }
        catch (ApplyMoveException applyMoveException) {
            throw new InternalServerException(applyMoveException);
        }

        // Saving the changed Game state into the DB
        this.gameRepository.save(game);

        //Scoring the Pyramid - after every Move
        this.scoringService.score(game, GameConstants.PYRAMID);

        // Checking if the Game is still in the Status: SUBROUND
        this.checkSubRound(move, game);

        // Checking if the game advances to the next Round
        this.checkNextRound(game);
    }

    private synchronized void checkNextRound(Game game){
        log.debug("Checks if the Game: {} needs to proceed to the next Round", game.getId());

        // Advancing the Game to the next Player, only if the Game is in Status: RUNNING
        if (game.getStatus() == GameStatus.RUNNING) {
            // Advancing the currentPlayer
            game.setCurrentPlayer((game.getCurrentPlayer()) % (game.getPlayers().size()) + 1);

            // Checking whether all ships have been sailed or not
            if (this.roundService.goToNextRound(game.getRoundByRoundCounter())){
                // After six Rounds the Game will be ended
                if (game.getRoundCounter() == GameConstants.LAST_ROUND) {
                    // Scoring End of the Game (Burial_Chamber, MarketCards, Obelisk, Temple)
                    this.scoringService.score(game, GameConstants.TEMPLE);
                    this.scoringService.score(game, GameConstants.OBELISK);
                    this.scoringService.score(game, GameConstants.BURIAL_CHAMBER);
                    this.scoringService.score(game, GameConstants.CARD);

                    // Stopping the Game -> Status Change -> Winning Screen
                    this.gameService.stopGame(game.getId());
                }
                // Game is not finished yet
                else {
                    // Clear all site harbors (remove sailed ships from last round)
                    for (BuildingSite buildingSite: game.getBuildingSites()) {
                        buildingSite.setDocked(false);
                    }
                    // Remove the sailedShip from the Market Place
                    game.getMarketPlace().setDocked(false);

                    // Scoring at the End of the Round (Temple)
                    this.scoringService.score(game, GameConstants.TEMPLE);

                    // All Ships have sailed -> Initialize a new Round
                    this.gameService.initializeRound(game.getId());
                }
            }
        }
        // Saving the changes to the DB
        this.gameRepository.save(game);
    }

    private synchronized void checkSubRound(AMove move, Game game){
        log.debug("Checks if the Game: {} is currently in a SubRound", game.getId());

        // Advancing the Sub-Round when the Game is in Status: SUBROUND
        // MoveTypes allowed: Lever -> sailed to MarketPlace, Sail -> Sailed to MarketPlace
        // GetCard -> retrieving Card from Marketplace, SailShip -> sailed to MarketPlace
        if (game.getStatus() == GameStatus.SUBROUND && (move.getMoveType().equals(GameConstants.SAIL_SHIP)
                || move.getMoveType().equals(GameConstants.GET_CARD)
                || move.getMoveType().equals(MarketCardType.SAIL.toString()) || move.getMoveType().equals(MarketCardType.LEVER.toString()))) {

            // Retrieving the correct Ship
            Ship ship = game.getRoundByRoundCounter().getShipById(game.getMarketPlace().getDockedShipId());

            // Setting the next SubRoundPlayer
            this.nextSubRoundPlayer(game, ship);

            // Saving the changes to the DB
            this.gameRepository.save(game);
        }
    }

    private void nextSubRoundPlayer(Game game, Ship ship){
        log.debug("Checks if the Game: {} needs to proceed to the next SubRoundPlayer", game.getId());

        // Setting the next currentSubRoundPlayer according to the next Stone on the ship according to the placeOnShip
        if (!ship.getStones().isEmpty()){
            for (int i = 1; i <= ship.getMAX_STONES(); i++){
                if (ship.getStoneByPlace(i) != null){
                    switch (ship.getStoneByPlace(i).getColor()){
                        case BLACK: game.setCurrentSubRoundPlayer(1); break;
                        case WHITE: game.setCurrentSubRoundPlayer(2); break;
                        case BROWN: game.setCurrentSubRoundPlayer(3); break;
                        case GRAY:  game.setCurrentSubRoundPlayer(4); break;
                    }
                    // Putting the Stone from the MarketPlace back to the StoneQuarry
                    game.getStoneQuarry().getStonesByPlayerNr(game.getCurrentSubRoundPlayer()).add(ship.getStoneByPlace(i));
                    // Removing the stone from the Stones on the Ship
                    ship.getStones().remove(ship.getStoneByPlace(i));
                    // Only loop to the first hit -> afterwards break
                    break;
                }
            }
        }

        // All stones have been unloaded from the Ship
        else {
            // Returning the Game back to its normal running state
            game.setStatus(GameStatus.RUNNING);
        }

        // Saving the changes to the DB
        this.gameRepository.save(game);
    }

    protected void logMove(final AMove move, final Game game){
        log.debug("Logging Move: {} of Type: {} in Game: {}", move.getId(), move.getMoveType(), game.getId());

        // Logging the userName for any type of Move
        move.setUserName(game.getPlayerByPlayerNr(move.getPlayerNr()).getUsername());

        // Specifying what shall be logged for a SAIL_SHIP Move
        if (move.getMoveType().equals(GameConstants.SAIL_SHIP)) {
            // Set the destination of the move
            ((SailShipMove) move).setTargetSiteType(game.getSiteById(((SailShipMove) move).getTargetSiteId()).getSiteType());
        }
        // Specifying what shall be logged for a GET_CARD Move
        else if (move.getMoveType().equals(GameConstants.GET_CARD)) {
            // Setting the MarketCardType to the same of the taken Card
            ((GetCardMove) move).setMarketCardType(game.getMarketPlace().getMarketCardById(((GetCardMove) move).getMarketCardId()).getMarketCardType());
        }
        // Specifying what shall be logged for a SAIL or LEVER Move
        else if (move.getMoveType().equals(MarketCardType.LEVER.toString()) || move.getMoveType().equals(MarketCardType.SAIL.toString())) {
            // Set the destination of the move
            ((PlayCardMove) move).setTargetSiteType(game.getSiteById(((PlayCardMove) move).getTargetSiteId()).getSiteType());
            // Setting the MarketCardType to the same of the taken Card
            ((PlayCardMove) move).setMarketCardType(game.getPlayerByPlayerNr(move.getPlayerNr()).getMarketCardById(((PlayCardMove) move).getCardId()).getMarketCardType());
        }
        // Specifying what shall be logged for a HAMMER or CHISEL Move
        else if (move.getMoveType().equals(MarketCardType.HAMMER.toString()) || move.getMoveType().equals(MarketCardType.CHISEL.toString())) {
            // Setting the MarketCardType to the same of the taken Card
            ((PlayCardMove) move).setMarketCardType(game.getPlayerByPlayerNr(move.getPlayerNr()).getMarketCardById(((PlayCardMove) move).getCardId()).getMarketCardType());
        }

        // Saving the Move including Description to the database
        this.aMoveRepository.save(move);
    }

    public Page<AMove> findLastMoves(Long gameId, int numberOfMoves){
        log.debug("Returning the last {} Moves in Game {}", numberOfMoves, gameId);

        // Creating a Query to return only the top numberOfMoves, starting on Page 0
        PageRequest pageable = new PageRequest(0, numberOfMoves);

        return this.aMoveRepository.findLastFiveMoves(gameId, pageable);
    }
}