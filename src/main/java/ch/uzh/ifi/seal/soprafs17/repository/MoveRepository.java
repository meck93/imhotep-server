package ch.uzh.ifi.seal.soprafs17.repository;

import ch.uzh.ifi.seal.soprafs17.entity.Move;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("moveRepository")
public interface MoveRepository extends CrudRepository<Move, Long> {
    //TODO Implement the MoveRepository -> see UserRepository as an example
}
