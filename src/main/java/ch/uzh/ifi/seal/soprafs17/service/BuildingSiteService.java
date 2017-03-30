package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.constant.SiteType;
import ch.uzh.ifi.seal.soprafs17.entity.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.repository.BuildingSiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Cristi and Dave on 24.03.2017.
 */

@Service
@Transactional
public class BuildingSiteService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final BuildingSiteRepository buildingSiteRepository;

    @Autowired
    public BuildingSiteService(BuildingSiteRepository buildingSiteRepository) {
        this.buildingSiteRepository = buildingSiteRepository;
    }

    public BuildingSite createBuildingSite(SiteType siteType, Long gameId){
        BuildingSite buildingSite = new BuildingSite();
        buildingSite.setSiteType(siteType);
        buildingSiteRepository.save(buildingSite);

        return buildingSite;
    }


}