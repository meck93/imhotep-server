package ch.uzh.ifi.seal.soprafs17.repository;


import ch.uzh.ifi.seal.soprafs17.entity.user.SupplySled;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("supplySledRepository")
public interface SupplySledRepository extends CrudRepository<SupplySled, Long> {

    @Query("SELECT s FROM SupplySled s WHERE s.playerId = :playerId")
    SupplySled findSupplySledByPlayerId(@Param("playerId") Long playerId);
}