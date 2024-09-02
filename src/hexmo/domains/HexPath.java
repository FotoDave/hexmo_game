package hexmo.domains;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

/**
 * Classe permettant de détecter le chemin pour le gagnant de la partie
 * 
 * 
 * It-3-q3. Choix du type et de l’implémentation d’une collection pour mémoriser les cases à visiter
 * 
 * 		Comme interface de collection, j'ai choisi l'interface 'Queue' parce que j'avais besoin 
 * 		d'implémenter le principe FIFO (First In First Out) c'est à dire l'ajout et le retrait des 
 * 		éléments de ma collection suivant un ordre spécifique. 
 * 
 * 		Pour cela, comme implémentation de ma collection j'ai opté pour une 'ArrayDeque()'. Parce que 
 * 		j'avais besoin d'utiliser les méthodes: 
 * 			-> 'offer()' pour l'ajout des éléments à la fin de ma collection. Elle a une complexité
 * 				temporelle s'exprimant en O(1).
 * 			-> 'poll()' pour le retrait des éléments en tête de ma collection. Elle a une complexité
 * 				temporelle s'exprimant en O(1).
 */
public class HexPath {

	private static final char NO_BORDER = ' ';
	
	private final int rayon;
	private final HexColor color;
	private final Map<AxialCordonate, HexPathCordonate> alreadySeen;
	private final Set<AxialCordonate> coloredTiles;
	private final Queue<HexPathCordonate> neighborsToVisit;
	
	/**
	 * Constructeur de ma classe HexPath
	 * @param givenColoredTiles : collection de coordonnées des tuilles colorées reçue en paramètre
	 * @param givenRayon : rayon reçu en paramètre
	 * @param givenColor : coleur reçu en paramètre
	 */
	public HexPath(Set<AxialCordonate> givenColoredTiles, int givenRayon, HexColor givenColor){
		Objects.requireNonNull(givenColoredTiles);
		this.rayon = givenRayon;
		this.color = Objects.requireNonNull(givenColor);
		this.alreadySeen =  new HashMap<AxialCordonate, HexPathCordonate>();
		this.neighborsToVisit = new ArrayDeque<HexPathCordonate>();
		this.coloredTiles = new HashSet<AxialCordonate>();
		for(AxialCordonate element : givenColoredTiles) {
			coloredTiles.add(element);
		}
	}
	
	/**
	 * Determine la valeur de la longueur du chemin détecté
	 * @return : longueur du chemin
	 * 
	 * It-3-q2. CTT de l’algorithme de détection de chemins
	 * 
	 * 		-> Après analyse, il en ressort que la CTT de notre algorithme au pire des cas
	 * 		est de O(n + m) avec 
	 * 			- 'n' qui est le nombre de coordonnées des tuilles initialement colorées
	 * 			- 'm' qui est le nombre de voisin de chaque coordonnées de tuilles colorées
	 * 
	 * 
	 * 		-> En conclusion : On peut en déduire une CTT de O(n), où 'n' est le nombre de
	 * 		de coordonnées des tuilles colorées susceptibles d'être traitées.
	 * 
	 * 		Le détail du calcul est expliqué dans cette méthode ci-dessous.
	 */
	public int getPath() {
		//Etape 1 : Initialisation
		//Complexité au pire des cas : O(n) où 'n' est le nombre coordonnées des tuilles colorées
		if(color == HexColor.RED) {
			initialisationRedCordonates();
		}else {
			initialisationBlueCordonates();
		}
		//Etape 2 : Boucle
		while(!neighborsToVisit.isEmpty()) {
			//Je retire une cordonnée c de voisinAVisiter
			//Complexité au pire des cas : O(1)
			HexPathCordonate cord = neighborsToVisit.poll();
			//Je recupers tous les voisins de c
			Set<AxialCordonate> neighborsCordonate = cord.getAxialCordonate().getNeighborTiles();
			//Pour chaque voisin détecté
			//Complexité au pire des cas : O(m) avec 'm' est le nombre de voisin à traiter pour chaque coordonnées
				for (AxialCordonate ax : neighborsCordonate){
					//Si le voisin a la même couleur que c
					if(coloredTiles.contains(ax)) {
						//Complexité au pire des cas : O(1)
						HexPathCordonate neigPathCordonate = new HexPathCordonate(ax, cord.getBord(), cord.getDistance() + 1);
						//Si l’un des voisins a déjà été vu et provient d’un autre bord que c
						//Complexité au pire des cas : O(1)
						if(alreadySeen.containsKey(neigPathCordonate.getAxialCordonate())) {
							HexPathCordonate alreadySeenCord = alreadySeen.get(neigPathCordonate.getAxialCordonate());
							if(cord.getBord() != alreadySeenCord.getBord()) {
								return cord.getDistance() + alreadySeenCord.getDistance() + 2;
							}
						}//Sinon
						//Complexité au pire des cas : O(1)
						else {
							rememberNeighbor(neigPathCordonate);
						}
					}
				}
			}
		return 0;
	}
	
	/**
	 * Se souviens qu'on a déjà vue le voisin, et renseigne 
	 * qu'il a lui aussi des voisins à visiter
	 */
	private void rememberNeighbor(HexPathCordonate neigPathCordonate) {
		alreadySeen.put(neigPathCordonate.getAxialCordonate(), neigPathCordonate);
		neighborsToVisit.offer(neigPathCordonate);
	}
	
	
	/**
	 * Détecte les coordonnées Rouge situées à un bord du plateau
	 */
	private void initialisationRedCordonates() {
		for (AxialCordonate cord : coloredTiles) {
			if (isAtBoardEdge(cord)) {
	            addInitialCoordinateForRed(cord);
	        }
		}
	}
	
	/**
	 * Détecte les coordonnées Bleu situées à un bord du plateau
	 */
	private void initialisationBlueCordonates() {
		for (AxialCordonate cord : coloredTiles) {
			if (isAtBoardEdge(cord)) {
	            addInitialCoordinateForBlue(cord);
	        }
		}
	}
	
	/**
	 * Ajoute les coordonnées des cases rouges détectées au bord du plateau
	 * dans voisin à visiter et dans déjàVue
	 * @param cord : Coordonnée reçu en paramètre
	 */
	private void addInitialCoordinateForRed(AxialCordonate cord) {
		Objects.requireNonNull(cord);
	    char borderType = getBorderTypeForRed(cord);
	    if (borderType != NO_BORDER) {
	        HexPathCordonate hexPathCordonate = new HexPathCordonate(cord, borderType, 0);
	        alreadySeen.put(hexPathCordonate.getAxialCordonate(), hexPathCordonate);
	        neighborsToVisit.offer(hexPathCordonate);
	    }
	}
	
	/**
	 * Ajoute les coordonnées des cases rouges détectées au bord du plateau
	 * dans voisin à visiter et dans déjàVue
	 * @param cord : Coordonnée reçu en paramètre
	 */
	private void addInitialCoordinateForBlue(AxialCordonate cord) {
		Objects.requireNonNull(cord);
	    char borderType = getBorderTypeForBlue(cord);
	    if (borderType != NO_BORDER) {
	        HexPathCordonate hexPathCordonate = new HexPathCordonate(cord, borderType, 0);
	        alreadySeen.put(hexPathCordonate.getAxialCordonate(), hexPathCordonate);
	        neighborsToVisit.offer(hexPathCordonate);
	    }
	}
	
	/**
	 * Détecte si un coordonnée est situé sur un des bord du plateau
	 * @param cord : Coordonnée reçu en paramètre
	 * @return : 'true' si oui, 'false' si non.
	 */
	private boolean isAtBoardEdge(AxialCordonate cord) {
		Objects.requireNonNull(cord);
	    return Math.abs(cord.getQ()) == rayon || Math.abs(cord.getR()) == rayon || Math.abs(cord.getS()) == rayon;
	}
	
	/**
	 * Détermine le type de bord à attribuer à une coordonnée de couleur de case Bleue
	 * @param cord : Coordonnée reçu
	 * @return le type de bord
	 */
	private char getBorderTypeForBlue(AxialCordonate cord) {
		Objects.requireNonNull(cord);
	    if (cord.getQ() == -rayon) return 'Q';
	    if (cord.getR() == -rayon) return 'R';
	    if (cord.getS() == -rayon) return 'S';
	    return NO_BORDER;
	}
	
	/**
	 * Détermine le type de bord à attribuer à une coordonnée de couleur de case Rouge
	 * @param cord : Coordonnée reçu
	 * @return le type de bord
	 */
	private char getBorderTypeForRed(AxialCordonate cord) {
		Objects.requireNonNull(cord);
	    if (cord.getQ() == rayon) return 'Q';
	    if (cord.getR() == rayon) return 'R';
	    if (cord.getS() == rayon) return 'S';
	    return NO_BORDER;
	}
	
}
