package ch.uzh.ifi.seal.soprafs17.entity.move;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "MOVE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "MOVE_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class AMove implements Serializable {

	private static final long serialVersionUID = 1L;

	public AMove(String moveType){
		this.moveType = moveType;
	}

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private Long roundId;

	@Column
	private Long playerId;

	@Column(name = "MOVE_TYPE", nullable = false, insertable = false, updatable = false)
	private String moveType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoundId() {
		return roundId;
	}

	public void setRoundId(Long roundId) {
		this.roundId = roundId;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}
}
