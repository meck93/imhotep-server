package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.entity.site.MarketPlace;
import ch.uzh.ifi.seal.soprafs17.repository.ASiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
public class MarketPlaceService {

    private final Logger log = LoggerFactory.getLogger(MarketPlaceService.class);
    private final ASiteRepository aSiteRepository;

    @Autowired
    public MarketPlaceService(ASiteRepository aSiteRepository) {
        this.aSiteRepository = aSiteRepository;
    }

    public MarketPlace createMarketPlace(Long gameId) {
        log.debug("Creating a MarketPlace");

        MarketPlace marketPlace = new MarketPlace(gameId);
        marketPlace.setMarketCards(new ArrayList<>());

        aSiteRepository.save(marketPlace);

        return marketPlace;
    }

    public MarketPlace getMarketPlace(Long gameId){
        log.debug("Retrieving Market Place for: " + gameId);

        MarketPlace result = aSiteRepository.findMarketPlace(gameId);

        if (result != null){
            return result;
        }
        else {
            log.error("Not able to retrieve the MarketPlace for: " + gameId);
            return null;
        }
    }
}