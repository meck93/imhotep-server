package ch.uzh.ifi.seal.soprafs17.entity.user;

import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
public class SupplySled implements Serializable {

    private static final long serialVersionUID = 1L;

    public SupplySled(){
        // Left empty because Hibernate needs an empty constructor
    }

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Column
    @JsonIgnore
    private Long playerId;

    @OneToMany
    private List<Stone> stones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Stone> getStones() {
        return stones;
    }

    public void setStones(List<Stone> stones) {
        this.stones = stones;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
}
