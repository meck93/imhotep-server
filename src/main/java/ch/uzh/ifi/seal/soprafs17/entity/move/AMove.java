package ch.uzh.ifi.seal.soprafs17.entity.move;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "MOVE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "MOVE_TYPE", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = GetStonesMove.class, name = "GET_STONES"),
		@JsonSubTypes.Type(value = PlaceStoneMove.class, name = "PLACE_STONE"),
		@JsonSubTypes.Type(value = SailShipMove.class, name = "SAIL_SHIP"),
		@JsonSubTypes.Type(value = PlayCardMove.class, name = "PLAY_CARD"),
		@JsonSubTypes.Type(value = GetCardMove.class, name = "GET_CARD")})
public abstract class AMove implements Serializable {

	private static final long serialVersionUID = 1L;

	public AMove(){
		// Existence Reason: Hibernate also needs an empty constructor
	}

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private Long gameId;

	@Column(nullable = false)
	private int roundNr;

	@Column(nullable = false)
	private int playerNr;

	@Column(name = "MOVE_TYPE", nullable = false)
	private String moveType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public int getPlayerNr() {
		return playerNr;
	}

	public void setPlayerNr(int playerNr) {
		this.playerNr = playerNr;
	}

	public int getRoundNr() {
		return roundNr;
	}

	public void setRoundNr(int roundNr) {
		this.roundNr = roundNr;
	}
}
