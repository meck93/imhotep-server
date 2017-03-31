package ch.uzh.ifi.seal.soprafs17.repository;

import ch.uzh.ifi.seal.soprafs17.constant.SiteType;
import ch.uzh.ifi.seal.soprafs17.entity.BuildingSite;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository("buildingSiteRepository")
public interface BuildingSiteRepository  extends CrudRepository<BuildingSite, Long> {

    @Query("SELECT b FROM BuildingSite b WHERE (b.siteType = :siteType) AND (b.gameId = :gameId)")
    public BuildingSite findBuildingSite(@Param("gameId") Long gameId, @Param("siteType")SiteType siteType);
}