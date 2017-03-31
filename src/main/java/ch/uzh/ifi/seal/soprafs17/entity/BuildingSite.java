package ch.uzh.ifi.seal.soprafs17.entity;


import ch.uzh.ifi.seal.soprafs17.constant.SiteType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class BuildingSite implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long gameId;

    @Column(nullable = false)
    private SiteType siteType;

    @OneToOne(targetEntity = Ship.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Ship dockedShip;

    @OneToMany(targetEntity = Stone.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Stone> stones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SiteType getSiteType() {
        return siteType;
    }

    public void setSiteType(SiteType siteType) {
        this.siteType = siteType;
    }

    public List<Stone> getStones() {
        return stones;
    }

    public void setStones(List<Stone> stones) {
        this.stones = stones;
    }

    public Ship getDockedShip() {
        return dockedShip;
    }

    public void setDockedShip(Ship dockedShip) {
        this.dockedShip = dockedShip;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
