package ch.uzh.ifi.seal.soprafs17.entity.user;

import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
public class SupplySled implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne(targetEntity = Player.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "Player_ID", foreignKey = @ForeignKey(name ="PLAYER_ID_FK"))
    @JsonBackReference(value = "player")
    private Player player;

    @OneToMany(targetEntity = Stone.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
