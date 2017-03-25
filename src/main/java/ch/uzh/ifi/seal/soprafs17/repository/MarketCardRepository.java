package ch.uzh.ifi.seal.soprafs17.repository;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.MarketCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("marketCardRepository")
public interface MarketCardRepository extends CrudRepository<MarketCard, Long> {



}
