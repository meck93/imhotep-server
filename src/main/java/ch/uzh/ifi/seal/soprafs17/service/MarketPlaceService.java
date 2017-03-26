package ch.uzh.ifi.seal.soprafs17.service;

/**
 * Created by Cristian on 26.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.MarketPlace;
import ch.uzh.ifi.seal.soprafs17.repository.MarketPlaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MarketPlaceService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final MarketPlaceRepository marketPlaceRepository;

    @Autowired
    public MarketPlaceService(MarketPlaceRepository marketPlaceRepository) {
        this.marketPlaceRepository = marketPlaceRepository;
    }

    public MarketPlace marketPlaceInfo(){
        MarketPlace marketPlace = new MarketPlace();
        return marketPlace;
    }
}
