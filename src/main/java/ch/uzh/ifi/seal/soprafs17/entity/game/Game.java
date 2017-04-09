package ch.uzh.ifi.seal.soprafs17.entity.game;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.entity.site.MarketPlace;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Game implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false, unique = true)
	private String owner;

	@Column
	private GameStatus status;

	@Column
	private Integer currentPlayer;

	@Column
	private int roundCounter;

	@OneToOne(cascade = CascadeType.ALL)
	private BuildingSite obelisk;

	@OneToOne(cascade = CascadeType.ALL)
	private BuildingSite burialChamber;

	@OneToOne(cascade = CascadeType.ALL)
	private BuildingSite pyramid;

	@OneToOne(cascade = CascadeType.ALL)
	private BuildingSite temple;

	@Column
	private int numberOfPlayers;

	@OneToOne(targetEntity = MarketPlace.class, cascade = CascadeType.ALL)
	private MarketPlace marketPlace;

	@OneToOne(targetEntity = StoneQuarry.class, mappedBy = "game", cascade = CascadeType.ALL)
	private StoneQuarry stoneQuarry;

	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Round> rounds;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "game", orphanRemoval = true)
	@JsonManagedReference
	private List<Player> players;

	public Player getPlayerByPlayerNr(int playerNr){
		for (Player player : players){
			if (player.getPlayerNumber() == playerNr){
				return player;
			}
		}
		throw new NotFoundException("game");
	}

	public Round getRoundByRoundCounter(int roundCounter){
		Round result = null;
		for (Round round : this.getRounds()){
			if (round.getRoundNumber() == this.getRoundCounter()){
				result = round;
			}
		}

		if (result == null) throw new NotFoundException("Round");

		return result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public Integer getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Integer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public int getRoundCounter() {
		return roundCounter;
	}

	public void setRoundCounter(int roundCounter) {
		this.roundCounter = roundCounter;
	}

	public BuildingSite getObelisk() {
		return obelisk;
	}

	public void setObelisk(BuildingSite obelisk) {
		this.obelisk = obelisk;
	}

	public BuildingSite getBurialChamber() {
		return burialChamber;
	}

	public void setBurialChamber(BuildingSite burialChamber) {
		this.burialChamber = burialChamber;
	}

	public BuildingSite getPyramid() {
		return pyramid;
	}

	public void setPyramid(BuildingSite pyramid) {
		this.pyramid = pyramid;
	}

	public BuildingSite getTemple() {
		return temple;
	}

	public void setTemple(BuildingSite temple) {
		this.temple = temple;
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public MarketPlace getMarketPlace() {
		return marketPlace;
	}

	public void setMarketPlace(MarketPlace marketPlace) {
		this.marketPlace = marketPlace;
	}

	public StoneQuarry getStoneQuarry() {
		return stoneQuarry;
	}

	public void setStoneQuarry(StoneQuarry stoneQuarry) {
		this.stoneQuarry = stoneQuarry;
	}

	public List<Round> getRounds() {
		return rounds;
	}

	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
}