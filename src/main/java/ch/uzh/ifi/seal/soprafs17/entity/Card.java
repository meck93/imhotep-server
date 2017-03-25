package ch.uzh.ifi.seal.soprafs17.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Cristian on 25.03.2017.
 */

@Entity
public abstract class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private Long roundCardId;

    private Long marketCardId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoundCardId() {
        return roundCardId;
    }

    public void setRoundCardId(Long roundCardId) {
        this.roundCardId = roundCardId;
    }

    public Long getMarketCardId() {
        return marketCardId;
    }

    public void setMarketCardId(Long marketCardId) {
        this.marketCardId = marketCardId;
    }
}
