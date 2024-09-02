package hexmo.supervisors.mainmenu;

import java.util.List;
import java.util.Objects;

import hexmo.domains.DefaultHexGameFactory;
import hexmo.domains.HexGameFactory;
import hexmo.supervisors.commons.ViewId;

/**
 * Fournit les données qu'une vue représentant le menu principal doit afficher.
 * <p>Réagit aux événements de haut niveau signalé par sa vue.</p>
 * */
public class MainMenuSupervisor {

	public static final int EXIT_ITEM = 3;
	public static final int RAYON_TROIS = 3;
	public static final int RAYON_QUATRE = 4;
	public static final int RAYON_CINQ = 5;
	

	private MainMenuView view;
	private final HexGameFactory factory;

	public MainMenuSupervisor(DefaultHexGameFactory givenFactory) {
		this.factory = Objects.requireNonNull(givenFactory);
	}

	public void setView(MainMenuView view) {
		this.view = Objects.requireNonNull(view, "view is expected to be a reference to a defined view");
		//TODO : définir les items de la vue.
		this.view.setItems(List.of("Nouvelle Partie (R = 3)", "Nouvelle Partie (R = 4)", "Nouvelle Partie (R = 5)", "Quitter"));
	}

	/**
	 * Méthode appelée par la vue quand l'utilisateur sélectionne un item.
	 * 
	 * @param itemPos la position de l'item sélectionné. {@code item in [0; items.length[}
	 * */
	public void onItemSelected(int itemPos) {
		if(EXIT_ITEM == itemPos) {
			view.confirmExit();
		}
		//TODO : Démarrer une nouvelle partie
		switch (itemPos) {
			case 0 -> {
				this.factory.createGame(RAYON_TROIS);
			}
			case 1 -> {
				this.factory.createGame(RAYON_QUATRE);
			}
			case 2 -> {
				this.factory.createGame(RAYON_CINQ);	
			}
			default -> {
			}
		}
		
		//TODO : naviguer vers l'écran de jeu
		view.onEnter(ViewId.PLAY_GAME);
		view.goTo(ViewId.PLAY_GAME);
	}

}
