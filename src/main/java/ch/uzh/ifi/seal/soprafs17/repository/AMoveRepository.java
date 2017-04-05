package ch.uzh.ifi.seal.soprafs17.repository;

import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("AMoveRepository")
public interface AMoveRepository extends CrudRepository<AMove, Long> {

   /* @Query("SELECT m FROM AMove m WHERE m.gameId = :gameId")
    public List<AMove> findAllGameMoves(@Param("gameId") Long gameId);

    @Query("SELECT m FROM AMove m WHERE m.gameId = :gameId AND m.roundNr = :roundNr")
    public List<AMove> findAllRoundMoves(@Param("gameId") Long gameId, @Param("roundNr") int roundNr);*/

}
