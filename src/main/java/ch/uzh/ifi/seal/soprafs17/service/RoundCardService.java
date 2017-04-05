package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.constant.RoundCardType;
import ch.uzh.ifi.seal.soprafs17.constant.ShipSize;
import ch.uzh.ifi.seal.soprafs17.entity.RoundCard;
import ch.uzh.ifi.seal.soprafs17.repository.RoundCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class RoundCardService {

    private final Logger log = LoggerFactory.getLogger(RoundCardService.class);
    private final RoundCardRepository roundCardRepository;

    @Autowired
    public RoundCardService(RoundCardRepository roundCardRepository) {
        this.roundCardRepository = roundCardRepository;
    }

    /**
     * Calls the appropriate method for creating a deck of cards according to the rules.
     * @param amountOfPlayers
     * @param gameId
     */
    public void createRoundCards(int amountOfPlayers, Long gameId) {
        // decide which card set to create
        switch (amountOfPlayers) {
            case 2:
                createTwoHeadRoundCardSet(gameId, RoundCardType.TWO_HEADS);
                break;
            case 3:
                createThreeHeadRoundCardSet(gameId, RoundCardType.THREE_HEADS);
                break;
            case 4:
                createFourHeadRoundCardSet(gameId, RoundCardType.FOUR_HEADS);
                break;
        }
    }


    /**
     * Creates a deck of seven roundCards with the specified HeadType.
     * @param gameId
     * @pre game != NULL
     * @post seven roundCards are stored under the game id
     */
    public void createTwoHeadRoundCardSet(Long gameId, RoundCardType roundCardType){
        log.debug("Creating seven roundCards for Type: " + roundCardType + " associated to gameId: " + gameId);

        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.L, ShipSize.M, ShipSize.M);
        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.L, ShipSize.L, ShipSize.S);
        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.M, ShipSize.M, ShipSize.S);
        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.L, ShipSize.M, ShipSize.S);
        createRoundCard(gameId, roundCardType, ShipSize.L, ShipSize.M, ShipSize.M, ShipSize.S);
        createRoundCard(gameId, roundCardType, ShipSize.L, ShipSize.L, ShipSize.M, ShipSize.M);
        createRoundCard(gameId, roundCardType, ShipSize.L, ShipSize.L, ShipSize.M, ShipSize.S);
    }

    public void createThreeHeadRoundCardSet(Long gameId, RoundCardType roundCardType) {
        log.debug("Creating seven roundCards for Type: " + roundCardType + " associated to gameId: " + gameId);

        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.M, ShipSize.M, ShipSize.S);
        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.L, ShipSize.L, ShipSize.M);
        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.L, ShipSize.M, ShipSize.M);
        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.XL, ShipSize.M, ShipSize.S);
        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.L, ShipSize.M, ShipSize.M);
        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.L, ShipSize.M, ShipSize.S);
        createRoundCard(gameId, roundCardType, ShipSize.L, ShipSize.L, ShipSize.M, ShipSize.S);
        createRoundCard(gameId, roundCardType, ShipSize.L, ShipSize.L, ShipSize.L, ShipSize.M);
    }

    public void createFourHeadRoundCardSet(Long gameId, RoundCardType roundCardType) {
        log.debug("Creating seven roundCards for Type: " + roundCardType + " associated to gameId: " + gameId);

        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.XL, ShipSize.M, ShipSize.S);
        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.L, ShipSize.L, ShipSize.M);
        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.L, ShipSize.L, ShipSize.L);
        createRoundCard(gameId, roundCardType, ShipSize.L, ShipSize.L, ShipSize.L, ShipSize.M);
        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.XL, ShipSize.L, ShipSize.M);
        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.XL, ShipSize.M, ShipSize.M);
        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.L, ShipSize.M, ShipSize.M);
    }

    /*
     * Creating an individual roundCard
     */
    public void createRoundCard(Long gameId, RoundCardType roundCardType, ShipSize sizeOne, ShipSize sizeTwo, ShipSize sizeThree, ShipSize sizeFour) {
        log.debug("Creating a roundCard of Type: " + roundCardType);

        RoundCard roundCard = new RoundCard();
        roundCard.setGameId(gameId);
        roundCard.setHeads(roundCardType);
        roundCard.setAlreadyChosen(false);

        ArrayList<ShipSize> shipSizes = new ArrayList<>();
        shipSizes.add(sizeOne);
        shipSizes.add(sizeTwo);
        shipSizes.add(sizeThree);
        shipSizes.add(sizeFour);
        roundCard.setShipSizes(shipSizes);

        roundCardRepository.save(roundCard);
    }

    /**
     * Deals a random card from the currently available roundCards.
     * @param gameId
     * @pre game =/= NULL && game.roundCounter =l= 6
     * @return RoundCard roundCard
     */
    public RoundCard getRoundCard(Long gameId) {
        log.debug("Picking a roundCard by random from all roundCards associated with gameId: " + gameId);

        List<RoundCard> roundCardDeck = new ArrayList<>();
        roundCardRepository.findAllRoundCards(gameId).forEach(roundCardDeck::add);

        // Removing all alreadyChosen roundCards from the deck
        roundCardDeck.removeIf(RoundCard::isAlreadyChosen);

        // Choosing one of the new roundCards by random
        Random rnd = new Random();
        RoundCard currentCard = roundCardDeck.get(rnd.nextInt(roundCardDeck.size()-1));

        // Marking the chosen card as used in a Round
        currentCard.setAlreadyChosen(true);
        roundCardRepository.save(currentCard);

        return currentCard;
    }

    public void deleteCard(RoundCard roundCard){
        roundCardRepository.delete(roundCard);
    }
}
