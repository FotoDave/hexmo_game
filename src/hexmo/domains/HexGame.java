package hexmo.domains;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * Classe représentant une partie de jeu
 */
public class HexGame {
	
	private static final String PLAYER_ONE_NAME = "P1";
	private static final String ACTIVE_TILE = "Case Active (q : %d, r : %d, s : %d)";
	private static final String GAME_MESSAGE = "%s vs %s";
	private static final String PLAYER_MESSAGE = "Au tour de %s";
	
	private final HexTile currentHextile;
	private final Player playerOne;
	private final Player playerTwo;
	private final HexBoard hexBoard;
	private final Statistique stat;
	private boolean swap;
	private boolean isSwap;
	private boolean endGame;
	private Player activePlayer;
	
	/**
	 * Constructeur qui me permet de mettre ma classe HexGame dans un état cohérant
	 * @param givenPlayerOne : PlayerOne potentiel
	 * @param givenPlayerTwo : PlayerTwo potentiel
	 * @param givenHexboard : HexBoard potentiel
	 */
	public HexGame(Player givenPlayerOne, Player givenPlayerTwo, HexBoard givenHexboard, Statistique givenStat) {
		this.playerOne = Objects.requireNonNull(givenPlayerOne);
		this.playerTwo = Objects.requireNonNull(givenPlayerTwo);
		this.hexBoard = Objects.requireNonNull(givenHexboard);
		this.stat = Objects.requireNonNull(givenStat);
		this.swap = true;
		this.isSwap = false;
		this.endGame = false;
		this.currentHextile  = new HexTile(new AxialCordonate(0, 0), HexColor.UNKNOWN);
	}
	
	
	/**
	 * Effectue le swapping dans la partie
	 */
	public void swapping() {
		if(swap) {
			playerOne.setColor(HexColor.BLUE);
			playerTwo.setColor(HexColor.RED);
			activePlayer = playerOne;
			isSwap = true;
			disabledSwapping();
		}
	}
	
	/**
	 * Retourne une liste contenant les cordonnées obtenues après le déplacement
	 * @param q : coordonnées q
	 * @param r : coordonnée r
	 * @return : Une liste non modifiable de coordonnées
	 */
	public List<Integer> movement(int q, int r) {
		List<Integer> list = new ArrayList<Integer>();
		AxialCordonate movement = new AxialCordonate(currentHextile.getAxialCordonate().getQ(), 
										currentHextile.getAxialCordonate().getR());
		movement.setQ(q + movement.getQ());
		movement.setR(r + movement.getR());
		if(hexBoard.canMove(movement)) {
			list.add(movement.getQ());
			list.add(movement.getR());
			currentHextile.setAxialCordonate(movement);
		}
		return Collections.unmodifiableList(list);
	}
	
	/**
	 * Retourne la liste de toutes les coordonnées du plateau de jeu
	 * @return : Liste non modifiable de liste d'éléments
	 */
	public List<List<Integer>> getListCordonateBoard(){
		return Collections.unmodifiableList(hexBoard.getListCordonateBoard());
	}
	
	/**
	 * Retourne la couleur d'une case en fonction de ses coordonnées
	 * @return : Couleur de la case
	 */
	public HexColor getHexTileColor(int q, int r) {
		return hexBoard.getHexTileColor(q, r);
	}
	
	
	/**
	 * Affecte une couleur à la case active en fonction du joueur qui a la main
	 */
	public void setActiveTile() {
		currentHextile.setColor((activePlayer.getColor() == HexColor.RED) ? HexColor.RED : HexColor.BLUE);
		hexBoard.updateHextileInBoard(currentHextile);
		int path = hexBoard.pathVictory(activePlayer.getColor());
		if(path > 0) {
			endGame = true;
			setInformationsEndGame(path);
		}
	}
	
	/**
	 * Définit les informations relatives à la fin de la partie de jeu
	 * et les transfert à Statistique.
	 */
	private void setInformationsEndGame(int path) {
		int taillePathMin = hexBoard.getRayon() + 2;
		String winnerName = activePlayer.getPlayerDescription();
		double winnerScore = getScoreWinner(path, taillePathMin);
		double loserScore = getScoreLooser(path, taillePathMin);
		if(PLAYER_ONE_NAME.equals(activePlayer.getName())) {
			playerOne.updateScore(winnerScore);
			playerTwo.updateScore(loserScore);
		}else {
			playerTwo.updateScore(winnerScore);
			playerOne.updateScore(loserScore);
		}
		stat.saveResult(winnerName, winnerScore, loserScore, hexBoard.getfillRateBoard());
	}
	
	/**
	 * Retourne le score du gagnant
	 * @param givenPath : valeur du chemin
	 * @param givenMin : valeur d'un chemin minimum
	 * @return score du gagnant
	 */
	private double getScoreWinner(int givenPath, int givenMin) {
		return (givenMin - (givenPath - givenMin));
	}
	
	/**
	 * Retourne le score du perdant
	 * @param givenPath : valeur du chemin
	 * @param givenMin : valeur d'un chemin minimum
	 * @return score du perdant
	 */
	private double getScoreLooser(int givenPath, int givenMin) {
		return (givenMin - (givenPath - givenMin)) / 2.0;
	}
	
	/**
	 * Retourne le message relatif à la case active
	 * @return : message
	 */
	public String getActiveTileMessage() {
		return String.format(ACTIVE_TILE, 
				currentHextile.getAxialCordonate().getQ(), 
				currentHextile.getAxialCordonate().getR(), 
				currentHextile.getAxialCordonate().getS());
	}
	
	/**
	 * Retourne le message relatif au joueur qui a la main
	 * @return : message
	 */
	public String getPlayerMessage() {
		return ((playerOne.getName()).equals(activePlayer.getName())) 
				? String.format(PLAYER_MESSAGE, playerOne.getPlayerDescription()) 
				: String.format(PLAYER_MESSAGE, playerTwo.getPlayerDescription());
	}
	
	/**
	 * Retourne le message relatif à la partie de jeu
	 * @return : message
	 */
	public String getGameMessage() {
		return String.format(GAME_MESSAGE, playerOne.getPlayerDescription(), playerTwo.getPlayerDescription());
	}
	
	
	/**
	 * Passe la main au joueur suivant
	 * @return le joueur qui a la main
	 */
	public void switchPlayer() {
		hexBoard.clearBridge();
		activePlayer = (activePlayer.equals(playerOne)) ? playerTwo : playerOne;
		hexBoard.detectBridge(activePlayer.getColor());
	}
	
	/**
	 * Desactive la possibilité de swapper à nouveau
	 */
	public void disabledSwapping() {
		swap = false;
	}
	
	/**
	 * Détermine s'il est possible de swapper ou pas
	 * @return l'état de la variable 'swap'
	 */
	public boolean canSwap() {
		return swap;
	}
	
	/**
	 * Détermine si le joueur a swappé
	 * @return l'état de l'attribut 'isSwap'
	 */
	public boolean isUseSwap() {
		return isSwap;
	}
	
	/**
	 * Détermine si la partie est terminée
	 * @return l'état de la variable 'endGame'
	 */
	public boolean gameOver() {
		return endGame;
	}
	
	/**
	 * Determine si le joueur peut placer une case sur une coordonnées
	 * @return true si c'est possible, false sinon
	 */
	public boolean canPlay() {
		return hexBoard.canPlay(currentHextile.getAxialCordonate(), activePlayer.getColor())
				&& hexBoard.canMove(currentHextile.getAxialCordonate());
	}
	
	/**
	 * Spécifie le joueur actif au début de la partie
	 * @param givenPlayer : Joueur potentiellement actif
	 */
	public void setActivePlayer(Player givenPlayer) {
		this.activePlayer = Objects.requireNonNull(givenPlayer);
	}
	
}
