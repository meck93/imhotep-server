package ch.uzh.ifi.seal.soprafs17.repository;

import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("roundRepository")
public interface RoundRepository extends CrudRepository<Round, Long> {

    @Query("SELECT r FROM Round r WHERE r.id = :roundId")
    Round findById(@Param("roundId") Long roundId);

    @Query("SELECT r FROM Round r WHERE r.game = :roundId")
    Round findByRoundNr(@Param("roundId") Long roundId);

}
