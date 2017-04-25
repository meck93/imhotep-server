package ch.uzh.ifi.seal.soprafs17.repository;

import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("playerRepository")
public interface PlayerRepository extends CrudRepository<Player, Long> {

    @Query("DELETE FROM Player p WHERE p.id = :id")
    @Modifying
    void deletePlayer(@Param("id") Long id);

    @Query("SELECT p FROM Player p WHERE p.id = :id")
    Player findPlayerById(@Param("id") Long id);

    Player findById(Long id);
}
