package ch.uzh.ifi.seal.soprafs17.entity.site;

import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;

import javax.persistence.*;
import java.util.List;

@Entity(name = "BuildingSite")
@DiscriminatorValue(value = "BUILDING_SITE")
public class BuildingSite extends ASite {

    public BuildingSite(){
        // Purposely left blank because Hibernate needs an empty constructor
    }

    public BuildingSite(Long gameId){
        super.setGameId(gameId);
        super.setSiteType("BUILDING_SITE");
    }

    @OneToMany(targetEntity = Stone.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Stone> stones;

    public List<Stone> getStones() {
        return stones;
    }

    public void setStones(List<Stone> stones) {
        this.stones = stones;
    }

    //Do not delete
    public void addStone(Stone stone){
        this.stones.add(stone);
    }
}
