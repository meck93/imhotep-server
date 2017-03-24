package ch.uzh.ifi.seal.soprafs17.repository;

import ch.uzh.ifi.seal.soprafs17.entity.BuildingSite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Dave and Cristi on 24.03.2017.
 */

@Repository("buildingSiteRepository")
public interface BuildingSiteRepository  extends CrudRepository<BuildingSite, Long> {
}