package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.constant.BuildingSiteType;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.repository.ASiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public BuildingSite createBuildingSite(BuildingSiteType buildingSiteType, Long gameId){
        log.debug("Creating a building site of type: " + buildingSiteType);

        BuildingSite buildingSite = new BuildingSite(buildingSiteType, gameId);
        buildingSite.setStones(new ArrayList<>());

        aSiteRepository.save(buildingSite);

        return buildingSite;
    }

    /*
     * Returns the BuildingSite of {siteType} associated to the Game with Id: {gameId}
     * @PARAM gameId the Game Id, siteType the type of the BuildingSite
     */
    public BuildingSite getBuildingSite(Long gameId, BuildingSiteType buildingSiteType) {
        log.debug("Finding BuildingSite: " + buildingSiteType + " of Game: " + gameId);

        BuildingSite result = aSiteRepository.findBuildingSite(gameId, buildingSiteType);

        if (!result.equals(null)){
            return result;
        }
        else {
            log.error("Couldn't find the requested BuildingSite for Game: " + gameId);
            return null;
            // TODO: Throw a proper exception with HTTP Response
        }

    }
    /*
     * Creates a Dummy-Stone on each Site of the Game for the Front-End Mapping/Modelling Purposes
     */
    public void createDummyData(Long gameId){
        List<BuildingSite> result = aSiteRepository.findAllBuildingSites(gameId);

        for (BuildingSite site : result){
            Stone stone = stoneService.createStone("BLACK");
            site.addStone(stone);
            aSiteRepository.save(site);
        }
    }
}