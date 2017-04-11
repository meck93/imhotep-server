package ch.uzh.ifi.seal.soprafs17.entity.game;

import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.NONE)
public class Ship implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long gameId;

    @Column
    private boolean hasSailed;

    @Column
    @JsonProperty(value = "MIN_STONES")
    private final int MIN_STONES;

    @Column
    @JsonProperty(value = "MAX_STONES")
    private final int MAX_STONES;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BuildingSite_ID")
    private BuildingSite targetSite;

    @OneToMany(targetEntity = Stone.class)
    // Maybe add @OrderBy(position ASC) again -> but then Stone.class needs a variable position
    private List<Stone> stones;

    public Ship() {
        this.MIN_STONES = 0;
        this.MAX_STONES = 0;
    }

    public Ship(int minStone, int maxStone){
        this.MIN_STONES = minStone;
        this.MAX_STONES = maxStone;
    }

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

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public int getMIN_STONE() {
        return MIN_STONES;
    }

    public int getMAX_STONES() {
        return MAX_STONES;
    }

    public boolean isHasSailed() {
        return hasSailed;
    }

    public void setHasSailed(boolean hasSailed) {
        this.hasSailed = hasSailed;
    }

    public BuildingSite getTargetSite() {
        return targetSite;
    }

    public void setTargetSite(BuildingSite targetSite) {
        this.targetSite = targetSite;
    }
}
