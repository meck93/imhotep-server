package ch.uzh.ifi.seal.soprafs17.repository;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("roundRepository")
public interface RoundRepository extends CrudRepository<Round, Long> {

    @Query("SELECT r FROM Round r WHERE r.id = :roundId")
    Round findById(@Param("roundId") Long roundId);

}
