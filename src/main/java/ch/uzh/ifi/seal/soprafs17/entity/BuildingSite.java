package ch.uzh.ifi.seal.soprafs17.entity;


import ch.uzh.ifi.seal.soprafs17.constant.SiteType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * Created by Dave and Cristi on 24.03.2017.
 */


@Entity
public class BuildingSite implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private SiteType siteType;

    @OneToMany(targetEntity = Stone.class)
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
}
