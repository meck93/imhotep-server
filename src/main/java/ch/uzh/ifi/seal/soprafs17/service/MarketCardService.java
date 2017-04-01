package ch.uzh.ifi.seal.soprafs17.service;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.MarketCard;
import ch.uzh.ifi.seal.soprafs17.repository.MarketCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.ArrayList;

import static ch.uzh.ifi.seal.soprafs17.constant.MarketCardType.*;

@Service
@Transactional
public class MarketCardService {

    private final Logger log = LoggerFactory.getLogger(MarketCardService.class);
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
    public MarketCard createMarketCard(Long gameId, Color color, MarketCardType marketCardType){
        log.debug("Creating a marketCard" + gameId);
        MarketCard marketCard = new MarketCard();
        marketCard.setGameId(gameId);
        marketCard.setColor(color);
        marketCard.setMarketCardType(marketCardType);
        marketCardRepository.save(marketCard);
        return marketCard;
    }

    /*
    public MarketCard getMarketCard(Long gameId) {

        log.debug("Picking a random marketCard out of the marketCardDeck associated with gameId: " + gameId);

        ArrayList<MarketCard> marketCardDeck = new ArrayList<>();
        marketCardRepository.findAllMarketCards(gameId).forEach(marketCardDeck::add);

        // Removing all alreadyChosen roundCards from the deck
        for (MarketCard marketCard : marketCardDeck) {
            if (marketCard.isAlreadyChosen()) {
                marketCardDeck.remove(marketCard);
            }
        }

        // Choosing one of the new roundCards by random
        Random rnd = new Random();
        MarketCard currentCard = marketCardDeck.get(rnd.nextInt(marketCardDeck.size()-1));

        // Marking the chosen card as used in a Round
        currentCard.setAlreadyChosen(true);
        marketCardRepository.save(currentCard);

        return marketCard;
    }
    */

    /**
     * Creates the marketCardDeck
     * @param   gameId
     * @pre     game != NULL
     * @post    34 marketCards of the different marketCardTypes
     */
    public ArrayList<MarketCard> createMarketCardDeck(Long gameId) {
        log.debug("Creating a marketCardDeck");

        ArrayList<MarketCard> marketCardDeck= new ArrayList<>();

        marketCardDeck.add(createMarketCard(gameId, Color.red, PAVED_PATH));
        marketCardDeck.add(createMarketCard(gameId, Color.red, PAVED_PATH));
        marketCardDeck.add(createMarketCard(gameId, Color.red, SARCOPHAGUS));
        marketCardDeck.add(createMarketCard(gameId, Color.red, SARCOPHAGUS));
        marketCardDeck.add(createMarketCard(gameId, Color.red, ENTRANCE));
        marketCardDeck.add(createMarketCard(gameId, Color.red, ENTRANCE));

        marketCardDeck.add(createMarketCard(gameId, Color.green, PYRAMID_DECORATION));
        marketCardDeck.add(createMarketCard(gameId, Color.green, PYRAMID_DECORATION));
        marketCardDeck.add(createMarketCard(gameId, Color.green, TEMPLE_DECORATION));
        marketCardDeck.add(createMarketCard(gameId, Color.green, TEMPLE_DECORATION));
        marketCardDeck.add(createMarketCard(gameId, Color.green, BURIAL_CHAMBER_DECORATION));
        marketCardDeck.add(createMarketCard(gameId, Color.green, BURIAL_CHAMBER_DECORATION));
        marketCardDeck.add(createMarketCard(gameId, Color.green, OBELISK_DECORATION));
        marketCardDeck.add(createMarketCard(gameId, Color.green, OBELISK_DECORATION));

        marketCardDeck.add(createMarketCard(gameId, Color.magenta, STATUE));
        marketCardDeck.add(createMarketCard(gameId, Color.magenta, STATUE));
        marketCardDeck.add(createMarketCard(gameId, Color.magenta, STATUE));
        marketCardDeck.add(createMarketCard(gameId, Color.magenta, STATUE));
        marketCardDeck.add(createMarketCard(gameId, Color.magenta, STATUE));
        marketCardDeck.add(createMarketCard(gameId, Color.magenta, STATUE));
        marketCardDeck.add(createMarketCard(gameId, Color.magenta, STATUE));
        marketCardDeck.add(createMarketCard(gameId, Color.magenta, STATUE));
        marketCardDeck.add(createMarketCard(gameId, Color.magenta, STATUE));
        marketCardDeck.add(createMarketCard(gameId, Color.magenta, STATUE));

        marketCardDeck.add(createMarketCard(gameId, Color.blue, CHISEL));
        marketCardDeck.add(createMarketCard(gameId, Color.blue, CHISEL));
        marketCardDeck.add(createMarketCard(gameId, Color.blue, CHISEL));
        marketCardDeck.add(createMarketCard(gameId, Color.blue, LEVER));
        marketCardDeck.add(createMarketCard(gameId, Color.blue, LEVER));
        marketCardDeck.add(createMarketCard(gameId, Color.blue, HAMMER));
        marketCardDeck.add(createMarketCard(gameId, Color.blue, HAMMER));
        marketCardDeck.add(createMarketCard(gameId, Color.blue, SAIL));
        marketCardDeck.add(createMarketCard(gameId, Color.blue, SAIL));
        marketCardDeck.add(createMarketCard(gameId, Color.blue, SAIL));

        return marketCardDeck;
    }



}
