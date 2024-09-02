package hexmo;

import hexmo.domains.DefaultHexGameFactory;
import hexmo.supervisors.commons.ViewId;
import hexmo.supervisors.gameover.GameOverSupervisor;
import hexmo.supervisors.mainmenu.MainMenuSupervisor;
import hexmo.supervisors.playgame.PlayGameSupervisor;
import hexmo.views.MainWindow;
import hexmo.views.SwingMainWindowFactory;
import hexmo.views.AppFactory;

import javax.swing.*;

/**
 * Construit et lance l'application réelle.
 */
public class Program {

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> buildAndRun(new SwingMainWindowFactory()));
    }
    
    /**
     * Construit et lance une application fabriquée avec {@code appBuilder}.
     * 
     * Une fabrique construisant l'interface utilisateur pour une technologie précise.
     */
    public static void buildAndRun(AppFactory appBuilder) {
        //TODO : définir les dépendances injectées au moment de construire les superviseurs
    	var factory = new DefaultHexGameFactory();
        var menuSupervisor = new MainMenuSupervisor(factory);
        var playSupervisor = new PlayGameSupervisor(factory);
        var endSupervisor = new GameOverSupervisor(factory);

        MainWindow main = appBuilder.ofSupervisors("B1UE09 - HexMo",
                menuSupervisor,
                playSupervisor,
                endSupervisor);

        main.start(ViewId.MAIN_MENU);
    }
}
