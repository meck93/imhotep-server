package ch.uzh.ifi.seal.soprafs17.service;

/**
 * Created by Cristian on 26.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.Stone;
import ch.uzh.ifi.seal.soprafs17.repository.StoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StoneService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final StoneRepository stoneRepository;

    @Autowired
    public StoneService(StoneRepository stoneRepository) {
        this.stoneRepository = stoneRepository;
    }

    public Stone stoneInfo(){
        Stone stone = new Stone();
        return stone;
    }
}
