package hexmo.domains;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Classe permettant de sauvegarder les statistiques de la partie
 */
public class Statistique {
	private static final String RESULTAT_PARTIE = "RESULTAT DE LA PARTIE";
	private static final String STATS = "STATISTIQUE";
	private static final String NOTHING = "";
	private static final String SCORE_P1 = "Score de P1 ";
	private static final String SCORE_P2 = "Score de P2 ";
	private static final String FILL_RATE = "Taux de remplissage ";
	private static final String CUMULATIVE_FILL_RATE = "Taux de remplissage moyen ";
	private static final String WINNER = "Vainqueur ";
	private static final String WINNER_SCORE = "Score du vainqueur ";
	private static final String LOOSER_SCORE = "Score du perdant ";
	private static final String PERCENTAGE = "%";
	
	private int cumulativeFillRate;
	private final List<String> result;
	private final Player playerOne;
	private final Player playerTwo;
	private int nbPartie;
	
	
	/**
	 * Constructeur de ma classe Statistique
	 * @param givenPlayerOne : Player 1 reçu en paramètre
	 * @param givenPlayerTwo : Player 2 reçu en paramètre
	 */
	public Statistique(Player givenPlayerOne, Player givenPlayerTwo) {
		this.cumulativeFillRate = 0;
		this.nbPartie = 0;
		this.result = new ArrayList<String>();
		this.playerOne = Objects.requireNonNull(givenPlayerOne);
		this.playerTwo = Objects.requireNonNull(givenPlayerTwo);
	}
	
	
	/**
	 * Enregistre le message pour une partie donnée
	 */
	public void saveResult(String givenWinnerName, double givenWinnerScore, 
			double givenLoserScore, int givenFillRate) {
		nbPartie += 1;
		this.cumulativeFillRate += givenFillRate;
		result.add(RESULTAT_PARTIE);
		result.add(NOTHING);
		result.add(WINNER + Objects.requireNonNull(givenWinnerName));
		result.add(WINNER_SCORE + givenWinnerScore);
		result.add(LOOSER_SCORE + givenLoserScore);
		result.add(FILL_RATE + givenFillRate + PERCENTAGE);
		result.add(NOTHING);
		saveStatistiques();
	}
	
	
	/**
	 * Retourne le message que doit afficher GameOverSupervisor
	 * @return Collection non modifiable de messages
	 */
	public List<String> getMessageOfGameOverView(){
		List<String> messages = new ArrayList<String>();
		for (String element : result) {
			messages.add(element);
		}
		result.clear();
		return Collections.unmodifiableList(messages);
	}
	
	/**
	 * Enregistre les informations liées aux statistiques du jeu
	 */
	private void saveStatistiques() {
		result.add(STATS);
		result.add(NOTHING);
		result.add(SCORE_P1 + playerOne.getScore());
		result.add(SCORE_P2 + playerTwo.getScore());
		result.add(CUMULATIVE_FILL_RATE + getRecoveryRate() + PERCENTAGE);
		result.add(NOTHING);
	}
	
	
	/**
	 * Calcule le taux de remplissage moyen
	 * @return le taux de recouvrement
	 */
	private int getRecoveryRate() {
		return (int) Math.round((double) cumulativeFillRate / nbPartie);
	}
	
}
