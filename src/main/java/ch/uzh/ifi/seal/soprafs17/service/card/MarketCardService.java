package ch.uzh.ifi.seal.soprafs17.service.card;


import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.repository.MarketCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ch.uzh.ifi.seal.soprafs17.constant.MarketCardType.*;
import static ch.uzh.ifi.seal.soprafs17.constant.MarketCardType.SAIL;
import static ch.uzh.ifi.seal.soprafs17.constant.MarketCardType.STATUE;

@Service
@Transactional
public class MarketCardService {

    private final Logger log = LoggerFactory.getLogger(MarketCardService.class);

    // Constants for the MarketCard Creation
    private static final int OCCUR_TWICE = 2;
    private static final int OCCUR_THREE_TIMES = 3;
    private static final int OCCUR_TEN_TIMES = 10;
    private static final int MARKET_CARD_DECK_SIZE = 4;

    private final MarketCardRepository marketCardRepository;

    @Autowired
    public MarketCardService(MarketCardRepository marketCardRepository) {
        this.marketCardRepository = marketCardRepository;
    }

    /**
     * Creates one marketCard of a specific MarketCardType
     * @param   gameId, color, marketCardType
     * @pre     game != NULL
     * @post    marketCard is created and saved in the MarketCardRepository
     */
    public MarketCard createMarketCard(Long gameId, String color, MarketCardType marketCardType){
        log.debug("Creating a marketCard" + gameId);

        // Creating a MarketCard of Type: marketCardType
        MarketCard marketCard = new MarketCard();
        marketCard.setGameId(gameId);
        marketCard.setColor(color);
        marketCard.setMarketCardType(marketCardType);

        marketCardRepository.save(marketCard);

        return marketCard;
    }

    /**
     * Creates the marketCardDeck
     * @param   gameId
     * @pre     game =/= NULL
     * @post    34 marketCards of the different marketCardTypes are created
     */
    public void createMarketCardSet(Long gameId) {
        log.debug("Creating a marketCardDeck");

        // create all the marketCards that occur two times
        for(int i=0; i<OCCUR_TWICE; i++) {
            createMarketCard(gameId, GameConstants.RED, PAVED_PATH);
            createMarketCard(gameId, GameConstants.RED, SARCOPHAGUS);
            createMarketCard(gameId, GameConstants.RED, ENTRANCE);
            createMarketCard(gameId, GameConstants.GREEN, PYRAMID_DECORATION);
            createMarketCard(gameId, GameConstants.GREEN, TEMPLE_DECORATION);
            createMarketCard(gameId, GameConstants.GREEN, BURIAL_CHAMBER_DECORATION);
            createMarketCard(gameId, GameConstants.GREEN, OBELISK_DECORATION);
            createMarketCard(gameId, GameConstants.BLUE, LEVER);
            createMarketCard(gameId, GameConstants.BLUE, HAMMER);
        }

        // create all the marketCards that occur three times
        for(int i=0; i<OCCUR_THREE_TIMES; i++) {
            createMarketCard(gameId, GameConstants.BLUE, CHISEL);
            createMarketCard(gameId, GameConstants.BLUE, SAIL);
        }

        // create STATUE marketCards (10 times)
        for(int i=0; i<OCCUR_TEN_TIMES;i++) {
            createMarketCard(gameId, GameConstants.VIOLET, STATUE);
        }
    }

    protected MarketCard getMarketCard(Long gameId) {
        log.debug("A random MarketCard from the deck of Game: " + gameId);

        List<MarketCard> marketCardDeck = new ArrayList<>();
        this.marketCardRepository.findAllMarketCards(gameId).forEach(marketCardDeck::add);

        // Removing all alreadyChosen marketCards from the deck
        marketCardDeck.removeIf(MarketCard::isAlreadyChosen);

        // Choosing one of the new marketCards by random
        Random rnd = new Random();
        MarketCard chosenMarketCard = marketCardDeck.get(rnd.nextInt(marketCardDeck.size()-1));

        // Marking the chosen card as used
        chosenMarketCard.setAlreadyChosen(true);

        this.marketCardRepository.save(chosenMarketCard);

        return chosenMarketCard;
    }

    public List<MarketCard> getMarketCardDeck(Long gameId) {
        log.debug("Four random MarketCards by Game: " + gameId);

        // Create a deck of four Market Cards
        List<MarketCard> fourCards = new ArrayList<>();

        // Adding four random Market Cards to the deck
        for (int i = 1; i <= MARKET_CARD_DECK_SIZE; i++){
            MarketCard marketCard = getMarketCard(gameId);

            // Assign each Market Card with a position number to be ordered on the Market Place
            marketCard.setPositionOnMarketPlace(i);

            this.marketCardRepository.save(marketCard);

            // Add the card to the deck
            fourCards.add(marketCard);
        }

        return fourCards;
    }
}
