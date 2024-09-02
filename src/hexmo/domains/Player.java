package hexmo.domains;

import java.util.Objects;

/**
 * Classe représentant le joueur
 */
public class Player {
	
	private final String name;
	private HexColor color;
	private double score;
	
	/**
	 * Constructeur permettant de mettre ma classe Player dans un état cohérant
	 * @param givenName : Potentiel attribut name
	 * @param givenColor : Potentiel attribut color
	 */
	public Player(String givenName, HexColor givenColor) {
		this.name = Objects.requireNonNull(givenName);
		this.color = Objects.requireNonNull(givenColor);
		this.score = 0;
	}

	public String getName() {
		return this.name;
	}

	public HexColor getColor() {
		return this.color;
	}

	public void setColor(HexColor givenColor) {
		this.color = Objects.requireNonNull(givenColor);
	}
	
	public double getScore() {
		return this.score;
	}
	
	/**
	 * Met à jour le score du joueur
	 * @param givenScore : score reçu en paramètre
	 */
	public void updateScore(double givenScore) {
		this.score += givenScore;
	}
	
	/**
	 * Retourne la description du joueur
	 * @return
	 */
	public String getPlayerDescription() {
		return name + " (" + color.toString() + ")";
	}
}
