package ch.uzh.ifi.seal.soprafs17.repository;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.MarketCard;
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
    @Query("SELECT r FROM MarketCard r WHERE r.gameId = :gameId AND r.color = red")
    List<MarketCard> findAllRedMarketCards(@Param("gameId")Long gameId);

    @Query("SELECT r FROM MarketCard r WHERE r.gameId = :gameId AND r.color = blue")
    List<MarketCard> findAllBlueMarketCards(@Param("gameId")Long gameId);

    @Query("SELECT r FROM MarketCard r WHERE r.gameId = :gameId AND r.color = green")
    List<MarketCard> findAllGreenMarketCards(@Param("gameId")Long gameId);

    @Query("SELECT r FROM MarketCard r WHERE r.gameId = :gameId AND r.color = violet")
    List<MarketCard> findAllVioletMarketCards(@Param("gameId")Long gameId);
}
