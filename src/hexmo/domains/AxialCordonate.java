package hexmo.domains;

import java.util.Objects;
import java.util.Set;

/**
 * Classe représentant les cordonnées pour un candidat
 */
public class AxialCordonate implements Comparable<AxialCordonate> {

	private static final int MINUS_ONE = -1;
	
	private int q;
	private int r;
	
	/**
	 * Constructeur qui met ma classe AxialCordonate dans un état cohérant
	 * @param positionQ : Position Q reçu
	 * @param positionR : Position R reçu
	 */
	public AxialCordonate(int positionQ, int positionR) {
		this.q = positionQ;
		this.r = positionR;
	}
	
	public int getQ() {
		return q;
	}

	public void setQ(int q) {
		this.q = q;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}
	
	/**
	 * Retourne la coordonnée S
	 * @return valeur de la coordonnée
	 */
	public int getS() {
		return (this.q * MINUS_ONE) + (this.r * MINUS_ONE);
	}
	
	/**
	 * Retourne une collection de ses cordonnées voisines
	 * @return : collection non modifiable de coordonnées
	 */
	public Set<AxialCordonate> getNeighborTiles(){
		return Set.of(new AxialCordonate(q, r + 1),
				new AxialCordonate(q - 1, r + 1),
				new AxialCordonate(q + 1, r),
				new AxialCordonate(q - 1, r),
				new AxialCordonate(q, r - 1),
				new AxialCordonate(q + 1, r - 1));
	}
	
	/**
	 * Retourne une collection de ses coordonnées diagonales
	 * @return : collection non modifiable de coordonnées
	 */
	public Set<AxialCordonate> getDiagonalsTiles(){
		return Set.of(new AxialCordonate(q - 1, r - 1),
				new AxialCordonate(q + 1, r - 2),
				new AxialCordonate(q + 2, r - 1),
				new AxialCordonate(q + 1, r + 1),
				new AxialCordonate(q - 1, r + 2),
				new AxialCordonate(q - 2, r + 1));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(q, r);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AxialCordonate)) {
			return false;
		}
		AxialCordonate other = (AxialCordonate) obj;
		return (this.q == other.q) && (this.r == other.r);
	}


	@Override
	public int compareTo(AxialCordonate givenAC) {
		if(givenAC == null) {
			return Integer.MIN_VALUE;
		}
		
		if(this.q != givenAC.q) {
			return this.q - givenAC.q;
		}
		
		return this.r - givenAC.r;
	}
}
