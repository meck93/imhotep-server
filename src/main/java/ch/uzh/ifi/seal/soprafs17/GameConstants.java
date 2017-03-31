package ch.uzh.ifi.seal.soprafs17;

public class GameConstants {

	// max and min players per game
	public final static Integer MIN_PLAYERS = 2;
	public final static Integer MAX_PLAYERS = 4;

	// color for the players
	public final static String BROWN = "BROWN";
	public final static String GRAY = "GRAY";
	public final static String WHITE = "WHITE";
	public final static String BLACK = "BLACK";

	// size of the different ships
	public final static int XL_SHIP = 4;
	public final static int L_SHIP = 3;
	public final static int M_SHIP = 2;
	public final static int S_SHIP = 1;

	// starting points
	public final static int START_POINTS = 0;

	// amount of starting stones
	public final static int START_STONES = 30;

	// sonarquabe suggested to add a private constructor instead of the implicit public one
	private GameConstants() {};
}
