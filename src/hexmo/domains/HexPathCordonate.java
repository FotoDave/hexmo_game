package hexmo.domains;

import java.util.Objects;

/**
 * Classe contenant les coordonnées spécifiques pour la détection du chemin
 */
public final class HexPathCordonate {
	
	private final AxialCordonate axialCordonate;
	private final char bord;
	private final int distance;
	
	/**
	 * Constructeur de ma classe HexPathCordonate
	 * @param givenCoord : Coordonnée reçue en paramètre
	 * @param givenBord : Bord reçu en paramètre
	 * @param givenDistance : Distance reçu en paramètre
	 */
	public HexPathCordonate(AxialCordonate givenCoord, char givenBord, int givenDistance) {
		Objects.requireNonNull(givenCoord);
		this.axialCordonate = new AxialCordonate(givenCoord.getQ(), givenCoord.getR());
		this.bord = givenBord;
		this.distance = givenDistance;
	}

	public AxialCordonate getAxialCordonate() {
		return axialCordonate;
	}

	public char getBord() {
		return bord;
	}

	public int getDistance() {
		return distance;
	}
	
}
