package hexmo.supervisors.gameover;

import java.util.List;
import java.util.Objects;

import hexmo.domains.DefaultHexGameFactory;
import hexmo.domains.HexGameFactory;
import hexmo.domains.Statistique;
import hexmo.supervisors.commons.ViewId;

/**
 * Réagit aux événements de haut-niveau de sa vue et lui fournit des données à afficher.
 * 
 * It-3-q1. Préconditions et postconditions de l’algorithme de détection de chemin
 * 			
 * 		-> Précondition : L'algorithme reçoit en entrée une collection de coordonnées des 
 * 			tuilles colorées (Rouge ou Bleu) disposées idéalement au bord, et de part et 
 * 			d'autre du plateau de jeu.
 * 
 * 		-> Postcondition : La donnée de sortie de mon algorithme doit être un nombre entier
 * 		résultant de la somme des distances des coordonnées formant un chemin.
 * 		S'il n'y a pas de chemin détecté, la donnée de sortie est un nombre égale à 0.
 * 
 * 		-> Implémentation de la méthode : Cette méthode est implémentée dans la classe 'HexPath'
 * 		et porte le nom de méthode 'getPath()'.
 * 
 * 
 * 
 * It-3-q2. CTT de l’algorithme de détection de chemins
 * 
 * 		Implémentation de la méthode : Cette méthode est implémentée dans la classe 'HexPath'
 * 		et porte le nom de 'getPath()'.
 * 
 * */
public class GameOverSupervisor {
	
	private GameOverView view;
	private final HexGameFactory factory;
	private final Statistique stat;

	/**
	 * 
	 * */
	public GameOverSupervisor(DefaultHexGameFactory givenFactory) {
		this.factory = Objects.requireNonNull(givenFactory);
		this.stat = Objects.requireNonNull(this.factory.getStatistique());
	}
	
	public void setView(GameOverView view) {
		if(view == null) {
			return;
		}
		
		this.view = view;
	}
	
	/**
	 * Méthode appelée par la vue quand une navigation vers elle est en cours.
	 * 
	 * @param fromView la vue d'origine. Correspond a priori à une constante définie dans ViewNames.
	 * */
	public void onEnter(ViewId fromView) {
		//TODO : générer les résultats et les afficher.
		List<String> messages = Objects.requireNonNull(this.stat.getMessageOfGameOverView());
		/*String[] tabMessages = new String[messages.size()];
		for(int i = 0; i < messages.size(); i++) {
			tabMessages[i] = messages.get(i);
		}*/
		this.view.setStats(messages.toArray(new String[0]));
	}

	/**
	 * Méthode appelée par la vue quand l'utilisateur souhaite retourner au menu principal.
	 * */
	public void onGoToMain() {
		//TODO : retourner au menu principal
		this.view.goTo(ViewId.MAIN_MENU);
	}
}
