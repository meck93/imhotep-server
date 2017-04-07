package ch.uzh.ifi.seal.soprafs17.repository;

/**
 * Created by Cristian on 26.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.game.SupplySled;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("supplySledRepository")
public interface SupplySledRepository extends CrudRepository<SupplySled, Long> {
}