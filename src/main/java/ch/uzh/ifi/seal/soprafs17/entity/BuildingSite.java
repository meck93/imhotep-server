package ch.uzh.ifi.seal.soprafs17.entity;


import ch.uzh.ifi.seal.soprafs17.constant.SiteType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by Dave and Cristi on 24.03.2017.
 */


@Entity
public class BuildingSite implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated
    @Column(nullable = false)
    SiteType siteType;


    @Column
    @JsonIgnore
    private Game game;

    public SiteType getSiteType() {
        return siteType;
    }

    public void setSiteType(SiteType siteType) {
        this.siteType = siteType;
    }
}
