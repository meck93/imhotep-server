package ch.uzh.ifi.seal.soprafs17.entity.site;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "SITE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "SITE_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class ASite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long gameId;

    @Column(name = "SITE_TYPE", updatable = false, insertable = false)
    private String siteType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }
}
