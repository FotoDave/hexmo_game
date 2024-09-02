package hexmo.domains;

/**
 * Décrit le comportement d'une fabrique de jeux
 */
public interface HexGameFactory {
	
	/**
	 * Créer la partie de jeu
	 * @param rayon : rayon du plateau
	 */
	void createGame(int rayon);
	
	/**
	 * Fournie une instance de la dernière partie crée
	 * @return HexGame : Instance de la dernière partie crée
	 */
	HexGame getGame();

	/**
	 * Fournie une instance de la Statistique
	 * @return Statistique : Instance de Statistique
	 */
	Statistique getStatistique();
	
}
