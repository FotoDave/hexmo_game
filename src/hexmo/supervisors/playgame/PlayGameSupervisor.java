package hexmo.supervisors.playgame;


import hexmo.domains.DefaultHexGameFactory;
import hexmo.domains.HexColor;
import hexmo.domains.HexGame;
import hexmo.domains.HexGameFactory;
import hexmo.supervisors.commons.*;
import java.util.Objects;

/**
 * Réagit aux événements utilisateurs de sa vue en mettant à jour 
 * une partie en cours et fournit à sa vue les données à afficher.
 * 
 * 
 * It-2-q4 : Complexité temporelle de l'algorithme décrit 
 * 
 * Dans la classe 'HexBoard', précisemment dans la méthode 'detectBridge(HexColor color)',
 * en examinant également les imbrications de ses méthodes internes, nous observons que :
 * 
 * 		1- La méthode "buildColoredTiles(coloredTiles, color)" s'exprime en O(n²) 
 * 			où n = nombre total de tuile dans la collection 'hexTilesCollection'
 * 
 * 		2- La méthode "getDiagonalsTiles(origine)" s'exprime en O(1), car elle fait juste un calcul
 * 			fixe de position. De même pour la méthode "getListOfCommonsNeighbors(origine, diagonal)"
 * 
 * 		3- La méthode "buildBridge(voisinCommuns)" s'exprime en O(m) où m = la taille de la liste
 * 			des voisins communs.
 * 
 * 
 * 		Conclusion : Après observation, la CTT au pire des cas de l'algorithme de détection 
 * 		des ponts s'exprime en : 
 * 			O(n²) => n = nombre total de tuille contenu dans le plateau de jeu. 
 * 
 * */
public class PlayGameSupervisor {
	private static final String SWAPPING_MESSAGE = "Joueur P2, voulez vous procéder au swapping ?";
	private static final String ERROR_MESSAGE = "La case est incompatible avec votre couleur";
	private static final double SQRT_TREE = Math.sqrt(3);
	private static final double DOUBLE_TREE = 3.0;
	private static final double DOUBLE_TWO = 2.0;
	
	
	private PlayGameView view;
	private final HexGameFactory factory;
	private HexGame game;


	public PlayGameSupervisor(DefaultHexGameFactory givenFactory) {
		this.factory = Objects.requireNonNull(givenFactory);
	}

	/**
	 * Définit la vue de ce superviseur à {@code view}.
	 * */
	public void setView(PlayGameView view) {
		this.view = Objects.requireNonNull(view);
	}
	

	/**
	 * Méthode appelée juste avant que la vue de ce superviseur soit affichée à l'écran.
	 * <p>Le superviseur affiche les données de départ du jeu (cout de la case active, nombre de trésors, bourse du joueur, etc.).
	 * Il dessine également les cases et indique quelle case est active.</p>
	 * */
	public void onEnter(ViewId fromView) {
		if (ViewId.MAIN_MENU == fromView) {
			//TODO : faire le rendu initial de l'écran de jeu
			view.clearTiles();
			view.setTileAt(0, 0, TileType.RED);
			view.setActiveTile(0, 0);
		}
		view.clearTiles();
		//Recupération de la partie
		this.game = this.factory.getGame();
		//Création et affichage de la partie de jeu
		createGameBoard();
		view.setActionMessages(game.getGameMessage(), game.getPlayerMessage());
	}
	
	/**
	 * Se charge d'afficher le plateau de jeu, case après case
	 */
	private void createGameBoard() {
		var cordonates = game.getListCordonateBoard();
		for (var cordonate : cordonates) {
			int q = cordonate.get(0);
			int r = cordonate.get(1);
			float x = generatePixelCoordonateX(q, r);
			float y = generatePixelCoordonateY(r);
			HexColor color = game.getHexTileColor(q, r);
			view.setTileAt(x, y, getCorrectTileType(color));
		}
	}
	
	/**
	 * Retourne la bonne couleur correspondante à afficher
	 * @param givenColor : couleur reçu
	 * @return : couleur à afficher
	 */
	private TileType getCorrectTileType(HexColor givenColor) {
		Objects.requireNonNull(givenColor);
		switch (givenColor) {
			case RED -> {
				return TileType.RED;
			}
			case BLUE -> {
				return TileType.BLUE;
			}
			case HIGHLIGHT -> {
				return TileType.HIGHLIGHT;	
			}
			default -> {
				return TileType.UNKNOWN;
			}
		}
	}
	
	/**
	 * Construit le message à afficher dans l'état de la partie
	 */
	private void buildMessage() {
		view.setActionMessages(game.getGameMessage(), game.getPlayerMessage(), game.getActiveTileMessage());
	}
	

	/**
	 * Tente de déplacer la case active de {@code deltaRow} lignes et de {@code deltaRow} colonnes.
	 * 
	 * <p>Cette méthode doit vérifier que les coordonnées calculées correspondent bien à une case du terrain.</p>
	 * */
	public void onMove(int dx, int dy) {
		//TODO : valider et changer de case active. Appelez les méthodes adéquates de la vue.
		var listCordonates = game.movement(dx, dy);
		if(!listCordonates.isEmpty()) {
			float x = generatePixelCoordonateX(listCordonates.get(0), listCordonates.get(1));
			float y = generatePixelCoordonateY(listCordonates.get(1));
			view.setActiveTile(x, y);
			buildMessage();
		}
	}

	/**
	 * Tente d'affecter la case active et met à jour l'affichage en conséquence.
	 * 
	 * <p>Ne fait rien si la case active a déjà été affectée.</p>
	 * */
	public void onSet() {
		//TODO : affecter si possible
		if(game.canPlay()) {
			game.setActiveTile();
			game.switchPlayer();
			buildMessage();
			view.clearTiles();
			createGameBoard();
			askSwapping();
			if(game.gameOver()) {
				view.goTo(ViewId.END_GAME);
			}
		}else {
			view.displayErrorMessage(ERROR_MESSAGE);
		}
	}
	
	/**
	 * Demande au joueur s'il veut swapper dans la
	 * mesure du possible
	 */
	private void askSwapping() {
		if(game.canSwap()) {
			if(view.askQuestion(SWAPPING_MESSAGE)) {
				game.swapping();
				buildMessage();
			}else {
				game.disabledSwapping();
			}
		}
	}
	
	
	/**
	 * Génère une coordonnée pixel X
	 * @return
	 */
	public float generatePixelCoordonateX(int q, int r) {
		return (float) (SQRT_TREE * q + SQRT_TREE / DOUBLE_TWO * r);
	}
	
	/**
	 * Génère une coordonnée pixel Y
	 * @return
	 */
	public float generatePixelCoordonateY(int r) {
		return (float) (DOUBLE_TREE / DOUBLE_TWO * r);
	}


	/**
	 * Méthode appelée par la vue quand l'utilisateur souhaite interrompre la partie.
	 * 
	 * <p>Ce superviseur demande à sa vue de naviguer vers le menu principal.</p>
	 * */
	public void onStop() {
		//TODO : naviguer vers le menu principal
		view.goTo(ViewId.MAIN_MENU);
	}

}
