package hexmo.domains;

/**
 * Classe représentant la fabrique de jeu
 */
public class DefaultHexGameFactory implements HexGameFactory{
	
	private HexGame game;
	private final Player playerOne;
	private final Player playerTwo;
	private final Statistique stat;
	
	/**
	 * Contructeur de DefaultHexGameFactory
	 */
	public DefaultHexGameFactory() {
		this.playerOne = new Player("P1", HexColor.RED);
		this.playerTwo = new Player("P2", HexColor.BLUE);
		this.stat = new Statistique(playerOne, playerTwo);
	}
	
	/**
	 * Réinitialise la couleur de PlayerOne et PlayerTwo avec des couleurs
	 * cohérentes avant chaque début de partie.
	 */
	private void reinitialisationColorsPlayers() {
		this.playerOne.setColor(HexColor.RED);
		this.playerTwo.setColor(HexColor.BLUE);
	}
	
	@Override
	public void createGame(int rayon) {
		// TODO Auto-generated method stub
		reinitialisationColorsPlayers();
		HexBoard hexBoard = new HexBoard(rayon);
		this.game = new HexGame(playerOne, playerTwo, hexBoard, stat);
		this.game.setActivePlayer(playerOne);
	}

	@Override
	public HexGame getGame() {
		// TODO Auto-generated method stub
		return this.game;
	}
	
	@Override
	public Statistique getStatistique() {
		// TODO Auto-generated method stub
		return this.stat;
	}
		
}
