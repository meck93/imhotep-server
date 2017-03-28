package ch.uzh.ifi.seal.soprafs17.service;

/**
 * Created by Cristian on 26.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.SupplySled;
import ch.uzh.ifi.seal.soprafs17.repository.SupplySledRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SupplySledService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final SupplySledRepository supplySledRepository;

    @Autowired
    public SupplySledService(SupplySledRepository supplySledRepository) {
        this.supplySledRepository = supplySledRepository;
    }

    public SupplySled supplySledInfo(){
        SupplySled supplySled = new SupplySled();
        return supplySled;
    }
}
