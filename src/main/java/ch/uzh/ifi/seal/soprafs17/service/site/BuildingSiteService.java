package ch.uzh.ifi.seal.soprafs17.service.site;

import ch.uzh.ifi.seal.soprafs17.entity.site.*;
import ch.uzh.ifi.seal.soprafs17.exceptions.InternalServerException;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import ch.uzh.ifi.seal.soprafs17.repository.ASiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static ch.uzh.ifi.seal.soprafs17.GameConstants.*;

@Service
@Transactional
public class BuildingSiteService {

    private final Logger log = LoggerFactory.getLogger(BuildingSiteService.class);
    private final ASiteRepository aSiteRepository;

    @Autowired
    public BuildingSiteService(ASiteRepository aSiteRepository) {
        this.aSiteRepository = aSiteRepository;
    }

    /*
     * Creating a building site and returning it
     * @param SiteType siteType the type of building site to be created
     */
    public BuildingSite createBuildingSite(String siteType, Long gameId){
        log.debug("Creating a building site of type: " + siteType);

        BuildingSite buildingSite = null;

        switch (siteType){
            case PYRAMID: buildingSite = new Pyramid(gameId); break;
            case TEMPLE: buildingSite = new Temple(gameId); break;
            case OBELISK: buildingSite = new Obelisk(gameId); break;
            case BURIAL_CHAMBER: buildingSite = new BurialChamber(gameId); break;
        }

        if (buildingSite != null) {
            buildingSite.setStones(new ArrayList<>());
        }
        else {
            throw new InternalServerException("Failed to create BuildingSite: " + siteType);
        }

        aSiteRepository.save(buildingSite);

        return buildingSite;
    }

    /*
     * Returns the BuildingSite of {siteType} associated to the Game with Id: {gameId}
     * @PARAM gameId the Game Id, siteType the type of the BuildingSite
     */
    public BuildingSite getBuildingSite(Long gameId, String siteType) {
        log.debug("Finding BuildingSite: " + siteType + " of Game: " + gameId);

        BuildingSite result = aSiteRepository.findBuildingSite(gameId, siteType);

        if (result == null) throw new NotFoundException(siteType);

        return result;
    }
}