package ch.uzh.ifi.seal.soprafs17.repository;

/**
 * Created by Cristian on 26.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.MarketPlace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("marketPlaceRepository")
public interface MarketPlaceRepository extends CrudRepository<MarketPlace, Long> {
}
