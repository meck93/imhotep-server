package ch.uzh.ifi.seal.soprafs17.repository;

import ch.uzh.ifi.seal.soprafs17.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.uzh.ifi.seal.soprafs17.entity.Move;

@Repository("moveRepository")
public interface MoveRepository extends CrudRepository<Move, Long> {
    Move findByName(String name);
    Move findById (Long id);
}
