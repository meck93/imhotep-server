package ch.uzh.ifi.seal.soprafs17.repository;

import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("AMoveRepository")
public interface AMoveRepository extends CrudRepository<AMove, Long> {
}
