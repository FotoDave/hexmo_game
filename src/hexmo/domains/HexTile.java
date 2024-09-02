package hexmo.domains;

import java.util.Objects;

/**
 * Classe représentant une case hexagonale
 */
public class HexTile {
	
	private static final double SQRT_TREE = Math.sqrt(3);
	private static final double DOUBLE_TREE = 3.0;
	private static final double DOUBLE_TWO = 2.0;
	
	private AxialCordonate axialCordonate;
	private HexColor color;
	
	/**
	 * Constructeur qui me permet de mettre ma classe HexTile dans un état cohérant
	 * @param coord : Potentiel attribut axialCordonate
	 * @param givenColor : Potentiel attribut color
	 */
	public HexTile(AxialCordonate coord, HexColor givenColor) {
		this.axialCordonate = Objects.requireNonNull(coord);
		this.color = Objects.requireNonNull(givenColor);
	}
	
	/**
	 * Génère une coordonnée pixel X
	 * @return
	 */
	public float getPixelCoordonateX() {
		return (float) (SQRT_TREE * this.axialCordonate.getQ() + SQRT_TREE / DOUBLE_TWO * this.axialCordonate.getR());
	}
	
	/**
	 * Génère une coordonnée pixel Y
	 * @return
	 */
	public float getPixelCoordonateY() {
		return (float) (DOUBLE_TREE / DOUBLE_TWO * this.axialCordonate.getR());
	}

	public AxialCordonate getAxialCordonate() {
		return axialCordonate;
	}

	public void setAxialCordonate(AxialCordonate givenAC) {
		this.axialCordonate = Objects.requireNonNull(givenAC);
	}

	public HexColor getColor() {
		return color;
	}

	public void setColor(HexColor givenColor) {
		this.color = Objects.requireNonNull(givenColor);
	}
}
