package ch.uzh.ifi.seal.soprafs17.entity.game;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.site.ASite;
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
	private int currentPlayer;

	@Column
	private int currentSubRoundPlayer;

	@Column
	private int numberOfPlayers;

	@Column
	private int roundCounter;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("id ASC")
	private List<BuildingSite> buildingSites;

	@OneToOne(cascade = CascadeType.ALL)
	private MarketPlace marketPlace;

	@OneToOne(mappedBy = "game", cascade = CascadeType.ALL)
	private StoneQuarry stoneQuarry;

	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Round> rounds;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "game", orphanRemoval = true)
	@JsonManagedReference
	private List<Player> players;

	public Player getPlayerByPlayerNr(int playerNr){
		for (Player player : this.players){
			if (player.getPlayerNumber() == playerNr){
				return player;
			}
		}
		throw new NotFoundException("game");
	}

	public Round getRoundByRoundCounter(int roundCounter){
		for (Round round : this.rounds){
			if (round.getRoundNumber() == this.getRoundCounter()){
				return round;
			}
		}
		throw new NotFoundException("Round");
	}

	public BuildingSite getBuildingSite(String siteType){
		for (BuildingSite buildingSite : this.buildingSites){
			if (buildingSite.getSiteType().equals(siteType)){
				return buildingSite;
			}
		}
		throw new NotFoundException(siteType);
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

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public int getCurrentSubRoundPlayer() {
		return currentSubRoundPlayer;
	}

	public void setCurrentSubRoundPlayer(int currentSubRoundPlayer) {
		this.currentSubRoundPlayer = currentSubRoundPlayer;
	}

	public List<BuildingSite> getBuildingSites() {
		return buildingSites;
	}

	public void setBuildingSites(List<BuildingSite> buildingSites) {
		this.buildingSites = buildingSites;
	}

	public ASite getSiteById(Long id){

		if (this.marketPlace.getId().equals(id)) return this.marketPlace;

		for (BuildingSite site : buildingSites){
			if (site.getId().equals(id)){
				return site;
			}
		}
		throw new NotFoundException(id, "Site not found!");
	}
}