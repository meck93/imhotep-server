package ch.uzh.ifi.seal.soprafs17.repository;

/**
 * Created by Cristian on 26.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("stoneRepository")
public interface StoneRepository extends CrudRepository<Stone, Long> {
}