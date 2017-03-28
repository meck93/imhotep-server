package ch.uzh.ifi.seal.soprafs17.repository;

import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.entity.Player;
import ch.uzh.ifi.seal.soprafs17.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("gameRepository")
public interface GameRepository extends CrudRepository<Game, Long> {
	User findByName(String name);

	Game findById(Long id);

	@Query("SELECT g.amountOfPlayers FROM Game g WHERE g.id = :gameId")
	int findAmountOfPlayers(@Param("gameId") Long gameId);

	@Query("SELECT g.players FROM Game g WHERE g.id = :gameId")
	List<Player> findPlayersByGameId(@Param("gameId") Long gameId);
}