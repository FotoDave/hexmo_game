package hexmo.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DefaultHexGameFactoryTest {

    @Test
    public void testGameIsInitiallyNull() {
    	DefaultHexGameFactory factory  = new DefaultHexGameFactory();
    	assertNull(factory.getGame());
    }

    @Test
    public void testCreateGameSetsUpGameCorrectly() {
    	DefaultHexGameFactory factory  = new DefaultHexGameFactory();
        factory.createGame(3);
        HexGame game = factory.getGame();
        Statistique stat = factory.getStatistique();
        assertNotNull(game);
        assertNotNull(stat);
    }

    @Test
    public void testGetGameAfterCreatingMultipleGames() {
    	DefaultHexGameFactory factory  = new DefaultHexGameFactory();
        factory.createGame(3);
        assertNotNull(factory.getGame());

        factory.createGame(5);
        HexGame game = factory.getGame();
        Statistique stat = factory.getStatistique();
        assertNotNull(game);
        assertNotNull(stat);
    }

}
