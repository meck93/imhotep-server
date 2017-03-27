package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.entity.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.repository.BuildingSiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    public BuildingSite obeliskEvent(){
        BuildingSite obelisk = new BuildingSite();
        obelisk.setMsg("I am Obelisk and I received your request.");
        return obelisk;
    }

    public BuildingSite burrialChamberEvent(){
        BuildingSite burrialChamber = new BuildingSite();
        burrialChamber.setMsg("I am the burrial chamber and I received your request.");
        return burrialChamber;
    }

    public BuildingSite templeEvent(){
        BuildingSite temple = new BuildingSite();
        temple.setMsg("I am the temple and I received your request.");
        return temple;
    }

    public BuildingSite pyramidEvent(){
        BuildingSite pyramid = new BuildingSite();
        pyramid.setMsg("I am the pyramid and I received your request.");
        return pyramid;
    }


}