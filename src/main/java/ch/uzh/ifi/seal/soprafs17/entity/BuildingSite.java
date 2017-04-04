package ch.uzh.ifi.seal.soprafs17.entity;


import ch.uzh.ifi.seal.soprafs17.constant.BuildingSiteType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "BuildingSite")
@DiscriminatorValue(value = "BUILDING_SITE")
public class BuildingSite extends ASite implements Serializable{

    public BuildingSite(){};

    public BuildingSite(BuildingSiteType buildingSiteType, Long gameId){
        super.setGameId(gameId);
        super.setSiteType("BUILDING_SITE");
        this.buildingSiteType = buildingSiteType;
    }

    @Column
    private BuildingSiteType buildingSiteType;

    @OneToOne(targetEntity = Ship.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Ship dockedShip;

    @OneToMany(targetEntity = Stone.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Stone> stones;

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

    public BuildingSiteType getBuildingSiteType() {
        return buildingSiteType;
    }

    public void setBuildingSiteType(BuildingSiteType buildingSiteType) {
        this.buildingSiteType = buildingSiteType;
    }

    //Do not delete
    public void addStone(Stone stone){
        this.stones.add(stone);
    }
}
