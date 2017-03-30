package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.constant.SiteType;
import ch.uzh.ifi.seal.soprafs17.entity.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.repository.BuildingSiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Created by Cristi and Dave on 24.03.2017.
 */

@Service
@Transactional
public class BuildingSiteService {

    private final Logger log = LoggerFactory.getLogger(BuildingSiteService.class);
    private final BuildingSiteRepository buildingSiteRepository;

    @Autowired
    public BuildingSiteService(BuildingSiteRepository buildingSiteRepository) {
        this.buildingSiteRepository = buildingSiteRepository;
    }

    /*
     * Creating a building site and returning it
     * @param SiteType siteType the type of building site to be created
     */
    public BuildingSite createBuildingSite(SiteType siteType){
        log.debug("Creating a building site of type: " + siteType);

        BuildingSite buildingSite = new BuildingSite();
        buildingSite.setSiteType(siteType);
        buildingSite.setStones(new ArrayList<>());
        buildingSiteRepository.save(buildingSite);

        return buildingSite;
    }


}