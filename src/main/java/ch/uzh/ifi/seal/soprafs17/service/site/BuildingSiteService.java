package ch.uzh.ifi.seal.soprafs17.service.site;

import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.site.*;
import ch.uzh.ifi.seal.soprafs17.exceptions.InternalServerException;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import ch.uzh.ifi.seal.soprafs17.repository.ASiteRepository;
import ch.uzh.ifi.seal.soprafs17.service.game.StoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static ch.uzh.ifi.seal.soprafs17.GameConstants.*;

@Service
@Transactional
public class BuildingSiteService {

    private final Logger log = LoggerFactory.getLogger(BuildingSiteService.class);
    private final ASiteRepository aSiteRepository;
    private final StoneService stoneService;

    @Autowired
    public BuildingSiteService(ASiteRepository aSiteRepository, StoneService stoneService) {
        this.aSiteRepository = aSiteRepository;
        this.stoneService = stoneService;
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
    /*
     * Creates a Dummy-Stone on each Site of the Game for the Front-End Mapping/Modelling Purposes
     */
    public void createDummyData(Long gameId){
        List<BuildingSite> result = aSiteRepository.findAllBuildingSites(gameId);

        for (BuildingSite site : result){
            if (site.getId().intValue() % 2 == 0){
                Stone stone = stoneService.createStone("BLACK");
                site.addStone(stone);
            }
            else {
                Stone stone = stoneService.createStone("WHITE");
                site.addStone(stone);
            }
            aSiteRepository.save(site);
        }
    }
}