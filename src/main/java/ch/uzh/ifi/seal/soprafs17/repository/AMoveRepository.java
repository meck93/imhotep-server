package ch.uzh.ifi.seal.soprafs17.repository;

import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("AMoveRepository")
public interface AMoveRepository extends CrudRepository<AMove, Long> {

    // Finding the last five moves
    @Query(value = "SELECT m FROM AMove m WHERE m.gameId = :gameId ORDER BY m.id DESC")
    Page<AMove> findLastFiveMoves(@Param("gameId") Long gameId, Pageable pageable);
}
