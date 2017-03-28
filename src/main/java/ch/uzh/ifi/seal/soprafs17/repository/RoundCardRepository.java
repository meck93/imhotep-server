package ch.uzh.ifi.seal.soprafs17.repository;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.RoundCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("roundCardRepository")
public interface RoundCardRepository extends CrudRepository<RoundCard, Long> {

    @Query("SELECT r FROM RoundCard r WHERE r.gameId = :gameId")
    List<RoundCard> findAllRoundCards(@Param("gameId")Long gameId);
}
