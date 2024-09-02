package hexmo.domains;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Classe représentant le plateau hexagonale du jeu
 */
public class HexBoard {
	
	private final Map<AxialCordonate, HexTile> hexTileCollection;
	private final Set<AxialCordonate> bridge;
	
	private final int rayon;
	
	/**
	 * Constructeur qui permet de mettre HexBoard dans un état cohérant
	 * @param rayon : Rayon reçu
	 */
	public HexBoard(int rayon) {
		this.hexTileCollection = new HashMap<AxialCordonate, HexTile>();
		this.bridge = new HashSet<AxialCordonate>();
		for (int i = -rayon; i <= rayon; i++) {
			for (int j = -rayon; j <= rayon; j++) {
				if(Math.abs(i + j) <= rayon) {
					AxialCordonate cordonate = new AxialCordonate(i, j);
					HexTile hexTile = new HexTile(cordonate, HexColor.UNKNOWN);
					this.hexTileCollection.put(cordonate, hexTile);
				}
			}
		}
		this.rayon = rayon;
	}
	
	/**
	 * Détermine si une coordonnée axiale fait parti du plateau de jeux
	 * @param coord : coordonnée axiale reçu
	 * @return : true si 'Oui', false si 'Non'
	 */
	public boolean canMove(AxialCordonate coord) {
		Objects.requireNonNull(coord);
		return (Math.abs(coord.getQ()) <= rayon) 
				&& (Math.abs(coord.getR()) <= rayon) 
				&& (Math.abs(-coord.getQ() - coord.getR()) <= rayon);
	}
	
	/**
	 * Détecte si la valeur maximale des composantes d'une coordonnée axiale est égale au rayon
	 * @param coord : coordonnée axiale reçu
	 * @return : true si 'Oui', false si 'Non'
	 */
	private boolean detectMaxCordonateValue(AxialCordonate coord) {
		Objects.requireNonNull(coord);
		return Math.max(Math.abs(coord.getQ()), 
				Math.max(Math.abs(coord.getR()), 
							Math.abs(coord.getS()))) == rayon;
	}
	
	/**
	 * Determine si une tuile peut être définie sur une coordonnées spécifique
	 * selon la couleur active du joueur dans le plateau de jeu
	 * @param coord : coordonnée axiale reçu
	 * @param givenColor : couleur active du joueur
	 * @return true si 'Oui', false si 'Non'
	 */
	public boolean canPlay(AxialCordonate coord, HexColor givenColor) {
		Objects.requireNonNull(coord);
		Objects.requireNonNull(givenColor);
		HexTile tile = hexTileCollection.get(coord);
		if(detectMaxCordonateValue(coord)
				&& (tile.getColor() == HexColor.UNKNOWN || tile.getColor() == HexColor.HIGHLIGHT)) {
			return authorization(coord, givenColor);
		}else {
			return (tile.getColor() == HexColor.UNKNOWN) || (tile.getColor() == HexColor.HIGHLIGHT);
		}
	}
	
	/**
	 * Valide l'occupation d'une case en fonction de la couleur du joueur actif
	 * @param coord : coordonnée axiale reçu
	 * @param givenColor : couleur du joueur actif
	 * @return true si 'Oui', false si 'Non'
	 */
	private boolean authorization(AxialCordonate coord, HexColor givenColor) {
		Objects.requireNonNull(coord);
		Objects.requireNonNull(givenColor);
		if(givenColor == HexColor.RED){
			return (coord.getR() + coord.getS()) == -rayon 
					|| (coord.getQ() + coord.getR()) == -rayon 
					|| (coord.getQ() + coord.getS()) == -rayon;
		}else{
			return (coord.getR() + coord.getS()) == rayon 
					|| (coord.getQ() + coord.getR()) == rayon 
					|| (coord.getQ() + coord.getS()) == rayon;
		}
	}
	
	/**
	 * Détecte le chemin reliant les deux bords du plateau de jeu
	 * @param givenColor : Couleur du joueur actif
	 * @return un nombre positif s'il y a un chemin, sinon '0'
	 */
	public int pathVictory(HexColor givenColor) {
		//Collection de coordonnées en fonction de la couleur du joueur actif
		Set<AxialCordonate> coloredTiles = new HashSet<AxialCordonate>();
		for (var element : hexTileCollection.entrySet()) {
			if(element.getValue().getColor() == givenColor) {
				coloredTiles.add(element.getKey());
			}
		}
		//Je transfers la liste des cases colorées à HexPath pour qu'il détecte s'il y a un chemin
		HexPath hexPath = new HexPath(Collections.unmodifiableSet(coloredTiles), rayon, givenColor);
		return hexPath.getPath();
	}
	
	/**
	 * Modifie la couleur de la tuile jouée sur le plateau de jeu
	 * @param givenHexTile : Tuile à modifier
	 */
	public void updateHextileInBoard(HexTile givenHexTile) {
		Objects.requireNonNull(givenHexTile);
		AxialCordonate coord = givenHexTile.getAxialCordonate();
		if(hexTileCollection.containsKey(coord)) {
			hexTileCollection.put(coord, new HexTile(new AxialCordonate(coord.getQ(), coord.getR()), givenHexTile.getColor()));
		}
	}
	
	/**
	 * Détection de pont en fonction du joueur qui a la main
	 * @param color : Couleur du joueur qui a la main
	 */
	public void detectBridge(HexColor color) {
		Objects.requireNonNull(color);
		Map<AxialCordonate, HexTile> coloredTiles = new HashMap<AxialCordonate, HexTile>();
		buildColoredTiles(coloredTiles, color);
		for (var coloredTile : coloredTiles.entrySet()) {
			AxialCordonate origine = coloredTile.getKey();
			Set<AxialCordonate> diagonals = origine.getDiagonalsTiles();
			for (AxialCordonate diagonal : diagonals) {
				if(hexTileCollection.containsKey(diagonal) 
						&& hexTileCollection.get(diagonal).getColor() == color) {
					List<AxialCordonate> voisinCommuns = getListOfCommonsNeighbors(origine, diagonal);
					buildBridge(voisinCommuns);
				}
			}
		}
	}
	
	/**
	 * Retourne la liste de coordonées voisines communes
	 * entre une coordonnée d'origine et sa diagonale
	 * @param origine : Coordonnée de l'origine
	 * @param diagonal : Coordonnée de sa diagonale
	 * @return : Liste non modifiable de coordonnées voisines
	 */
	private List<AxialCordonate> getListOfCommonsNeighbors(AxialCordonate origine, AxialCordonate diagonal){
		Objects.requireNonNull(origine);
		Objects.requireNonNull(diagonal);
		List<AxialCordonate> result = new ArrayList<AxialCordonate>();
		Set<AxialCordonate> neighborsOrignCollection = origine.getNeighborTiles();
		Set<AxialCordonate> neighborsDiagonalCollection = diagonal.getNeighborTiles();
		for(AxialCordonate voisin : neighborsOrignCollection){
			if(neighborsDiagonalCollection.contains(voisin)) {
				result.add(new AxialCordonate(voisin.getQ(), voisin.getR()));
			}
		}
		return Collections.unmodifiableList(result);
	}
	
	/**
	 * Construit ma collection de tuiles colorées en fonction d'une couleur spécifique
	 * @param givenMap : Collection à contruire
	 * @param givenColor : Couleur souhaitée pour les tuiles
	 */
	private void buildColoredTiles(Map<AxialCordonate, HexTile> givenMap, HexColor givenColor) {
		Objects.requireNonNull(givenMap);
		Objects.requireNonNull(givenColor);
		for (var tile : hexTileCollection.entrySet()) {
			if(tile.getValue().getColor() == givenColor) {
				AxialCordonate temp = new AxialCordonate(tile.getKey().getQ(), tile.getKey().getR());
				givenMap.put(temp, new HexTile(temp, tile.getValue().getColor()));
			}
		}
	}
	
	/**
	 * Construit et met en évidence le pont
	 * @param givenList : Liste contenant les coordonées voisines communes
	 */
	private void buildBridge(List<AxialCordonate> givenList) {
		Objects.requireNonNull(givenList);
		if(!givenList.isEmpty() && checkCommonsNeighbors(givenList)) {
			for (AxialCordonate coord : givenList) {
				hexTileCollection.put(coord, new HexTile(new AxialCordonate(coord.getQ(), coord.getR()), HexColor.HIGHLIGHT));
				bridge.add(coord);
			}
		}
	}
	
	/**
	 * Vérifie si les voisins communs sont admissibles à la création du pont
	 * @param givenList : Liste contenant les coordonées voisines communes
	 * @return : true si 'Oui', false si'Non'
	 */
	private boolean checkCommonsNeighbors(List<AxialCordonate> givenList) {
		Objects.requireNonNull(givenList);
		boolean test = true;
		for (AxialCordonate temp : givenList) {
			if(hexTileCollection.get(temp).getColor() == HexColor.RED 
					|| hexTileCollection.get(temp).getColor() == HexColor.BLUE) {
				test = false;
				return test;
			}
		}
		return test;
	}
	
	
	/**
	 * Retourne la liste de toutes les coordonnées du plateau de jeu
	 * @return : Liste non modifiable
	 */
	public List<List<Integer>> getListCordonateBoard(){
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		for (var element : hexTileCollection.entrySet()) {
			result.add(List.of(element.getKey().getQ(), element.getKey().getR()));
		}
		return Collections.unmodifiableList(result);
	}
	
	/**
	 * Retourne la couleur d'une tuile en fonction de ses coordonnées
	 * @return : Couleur de la tuile du plateau de jeu
	 */
	public HexColor getHexTileColor(int q, int r) {
		AxialCordonate cordonate = new AxialCordonate(q, r);
		HexTile hexTile = hexTileCollection.get(cordonate);
		return hexTile.getColor();
	}
	
	/**
	 * Vider la collection de pont et réassigne une couleur inconnue aux
	 * tuiles du plateau qui étaient présentes dans ma collection de pont
	 */
	public void clearBridge() {
		for (AxialCordonate temp : listHexTilesToUpdate()) {
			if(hexTileCollection.containsKey(temp)) {
				hexTileCollection.put(temp, new HexTile(new AxialCordonate(temp.getQ(), temp.getR()), HexColor.UNKNOWN));
			}
		}
		bridge.clear();
	}
	
	/**
	 * Retourne une liste de coordonnés représentant les tuiles à modifier
	 * dans le plateau de jeu
	 * @return : Liste non modifiable
	 */
	private List<AxialCordonate> listHexTilesToUpdate(){
		List<AxialCordonate> toChange = new ArrayList<AxialCordonate>();
		for (var coord : hexTileCollection.entrySet()) {
			if(coord.getValue().getColor() == HexColor.HIGHLIGHT) {
				toChange.add(coord.getKey());
			}
		}
		return Collections.unmodifiableList(toChange);
	}
	
	/**
	 * Calcule le taux de remplissage du plateau
	 * @return : taux de remplissage arrondi
	 */
	public int getfillRateBoard() {
		int countColoredTiles = 0;
		for (var element : hexTileCollection.entrySet()) {
			if(element.getValue().getColor() == HexColor.RED
					|| element.getValue().getColor() == HexColor.BLUE) {
				countColoredTiles += 1;
			}
		}
		int nbTotalTiles = hexTileCollection.size();
		double ratio = (double) countColoredTiles / nbTotalTiles;
		double pourcentage = ratio * 100;
		return (int) Math.round(pourcentage);
	}
	
	
	public int getRayon() {
		return rayon;
	}

}
