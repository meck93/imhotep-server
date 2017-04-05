package ch.uzh.ifi.seal.soprafs17.repository;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("marketCardRepository")
public interface MarketCardRepository extends CrudRepository<MarketCard, Long> {

    /**
     * Selects all marketCards associated with the gameId
     * @param   gameId
     * @pre     game =/= NULL   => gameId from marketCards must be initialized
     * @return  list of red marketCards
     */
    @Query("SELECT r FROM MarketCard r WHERE r.gameId = :gameId")
    List<MarketCard> findAllMarketCards(@Param("gameId")Long gameId);
}
