package hexmo.domains;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class HexBoardTests {
	
//	private static final Map<AxialCordonate, HexTile> SINGLETON_MAP = 
//			Map.of(new AxialCordonate(0, 0), new HexTile(new AxialCordonate(0, 0), HexColor.UNKNOWN));
	
	@Test
    void testCanMoveWithinBounds() {
        HexBoard board = new HexBoard(2);
        assertTrue(board.canMove(new AxialCordonate(0, 0)));
        assertTrue(board.canMove(new AxialCordonate(2, 0)));
        assertEquals(2, board.getRayon());
    }

    @Test
    void testCanMoveOutOfBound() {
        HexBoard board = new HexBoard(2);
        assertFalse(board.canMove(new AxialCordonate(3, 0)));
        assertFalse(board.canMove(new AxialCordonate(0, 3)));
        assertEquals(2, board.getRayon());
    }

    @Test
    void testCanMoveOnBoundaryCondition() {
        HexBoard board = new HexBoard(2);
        assertTrue(board.canMove(new AxialCordonate(1, 1)));
        assertFalse(board.canMove(new AxialCordonate(2, 1)));
    }
	
    @Test
    void testCanPlayOnUnknownTileWithinBounds() {
        HexBoard board = new HexBoard(2);
        assertTrue(board.canPlay(new AxialCordonate(0, 0), HexColor.RED));
    }

    @Test
    void testCannotPlayOnPlayedTile() {
        HexBoard board = new HexBoard(2);
        AxialCordonate coord = new AxialCordonate(0, 0);
        board.updateHextileInBoard(new HexTile(coord, HexColor.BLUE));
        assertFalse(board.canPlay(coord, HexColor.RED));
        assertEquals(5, board.getfillRateBoard());
    }

    @Test
    void testCannotPlayOnBoundaryWithoutAuthorization() {
        HexBoard board = new HexBoard(1);
        AxialCordonate coord = new AxialCordonate(1, 0);
        assertTrue(board.canPlay(coord, HexColor.BLUE));
    }
    
    @Test
    void testCanPlayOnTileWithUnknownColor() {
        HexBoard board = new HexBoard(2);
        AxialCordonate coord = new AxialCordonate(0, 0);
        board.updateHextileInBoard(new HexTile(coord, HexColor.UNKNOWN));
        assertTrue(board.canPlay(coord, HexColor.RED));
    }

    @Test
    void testCanPlayOnTileWithHighlightColor() {
        HexBoard board = new HexBoard(2);
        AxialCordonate coord = new AxialCordonate(1, 0);
        board.updateHextileInBoard(new HexTile(coord, HexColor.HIGHLIGHT));
        assertTrue(board.canPlay(coord, HexColor.BLUE));
    }

    @Test
    void testCannotPlayOnTileWithOtherColor() {
        HexBoard board = new HexBoard(2);
        AxialCordonate coord = new AxialCordonate(1, 1);
        board.updateHextileInBoard(new HexTile(coord, HexColor.RED));
        assertFalse(board.canPlay(coord, HexColor.BLUE));
        assertEquals(5, board.getfillRateBoard());
    }
    
    @Test
    void testCanPlayOnUnknownTileValid() {
        HexBoard board = new HexBoard(1);
        AxialCordonate coord = new AxialCordonate(0, 0);
        board.updateHextileInBoard(new HexTile(coord, HexColor.UNKNOWN));
        assertTrue(board.canPlay(coord, HexColor.RED));
    }

    @Test
    void testCanPlayOnHighlightTileValid() {
        HexBoard board = new HexBoard(1);
        AxialCordonate coord = new AxialCordonate(0, 0);
        board.updateHextileInBoard(new HexTile(coord, HexColor.HIGHLIGHT));
        assertTrue(board.canPlay(coord, HexColor.BLUE));
    }

    @Test
    void testCannotPlayOnColoredTile() {
        HexBoard board = new HexBoard(1);
        AxialCordonate coord = new AxialCordonate(0, 0);
        board.updateHextileInBoard(new HexTile(coord, HexColor.RED));
        assertFalse(board.canPlay(coord, HexColor.RED));
    }
    
    @Test
    void testAuthorizationWithSpecificCoordinates() {
        int rayon = 5;
        HexBoard board = new HexBoard(rayon);
        AxialCordonate coord1 = new AxialCordonate(-3, -2);
        board.updateHextileInBoard(new HexTile(coord1, HexColor.UNKNOWN));
        assertTrue(board.canPlay(coord1, HexColor.RED));
    }
    
    @Test
    void testAuthorizationWhenQSIsMinusRadius() {
        int rayon = 5;
        HexBoard board = new HexBoard(rayon);
        AxialCordonate coord = new AxialCordonate(0, rayon);
        board.updateHextileInBoard(new HexTile(coord, HexColor.UNKNOWN));
        assertTrue(board.canPlay(coord, HexColor.RED));
    }
    
    @Test
    void testClearBridgeUpdatesAllHighlightedTiles() {
        HexBoard board = new HexBoard(3);
        // Initialiser avec des tuiles spécifiques nécessitant une mise à jour
        board.updateHextileInBoard(new HexTile(new AxialCordonate(0, 0), HexColor.HIGHLIGHT));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(1, -1), HexColor.HIGHLIGHT));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(-1, 1), HexColor.RED)); 

        // Exécuter clearBridge qui doit utiliser listHexTilesToUpdate
        board.clearBridge();

        // Vérifier que toutes les tuiles HIGHLIGHT ont été mises à jour
        assertEquals(HexColor.UNKNOWN, board.getHexTileColor(0, 0));
        assertEquals(HexColor.UNKNOWN, board.getHexTileColor(1, -1));
        assertEquals(HexColor.RED, board.getHexTileColor(-1, 1));
    }
    
    @Test
    void testGetListCordonateBoard() {
        HexBoard board = new HexBoard(1);
        List<List<Integer>> expectedCoordinates = new ArrayList<>(List.of(
            List.of(0, 0),
            List.of(1, 0),
            List.of(-1, 0),
            List.of(0, 1),
            List.of(0, -1),
            List.of(1, -1),
            List.of(-1, 1)
        ));

        List<List<Integer>> actualCoordinates = board.getListCordonateBoard();

        assertEquals(expectedCoordinates.size(), actualCoordinates.size());

        for (List<Integer> coord : expectedCoordinates) {
            assertTrue(actualCoordinates.contains(coord));
        }
    }
    
    @Test
    public void testDetectBadBridge() {
        HexBoard board = new HexBoard(5);
        // Configuration initiale des tuiles
        board.updateHextileInBoard(new HexTile(new AxialCordonate(0, 0), HexColor.RED));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(1, -1), HexColor.RED));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(1, -2), HexColor.RED));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(2, -2), HexColor.RED));

        // Déclencher la détection des ponts
        board.detectBridge(HexColor.RED);

        boolean expectedValue = board.getHexTileColor(1, -1) == HexColor.HIGHLIGHT;
        assertFalse(expectedValue);
    }
    
    @Test
    public void testDetectGoodBridgeAgain() {
        HexBoard board = new HexBoard(3);
        // Configuration initiale des tuiles
        board.updateHextileInBoard(new HexTile(new AxialCordonate(-3, 3), HexColor.RED));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(-1, 2), HexColor.RED));

        // Déclencher la détection des ponts
        board.detectBridge(HexColor.RED);
        boolean expectedValue = board.getHexTileColor(-2, 2) == HexColor.HIGHLIGHT;
        assertTrue(expectedValue);
    }
    
    @Test
    void testCorrectPath() {
        HexBoard board = new HexBoard(3);
        board.updateHextileInBoard(new HexTile(new AxialCordonate(0, -3), HexColor.RED));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(0, -2), HexColor.RED));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(1, -2), HexColor.RED));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(2, -2), HexColor.RED));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(3, -3), HexColor.RED));
        assertEquals(5, board.pathVictory(HexColor.RED));
    }
    
    @Test
    void testNoPath() {
        HexBoard board = new HexBoard(3);
        board.updateHextileInBoard(new HexTile(new AxialCordonate(0, -3), HexColor.BLUE));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(0, -2), HexColor.RED));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(1, -2), HexColor.RED));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(2, -2), HexColor.RED));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(3, -3), HexColor.BLUE));
        assertEquals(0, board.pathVictory(HexColor.BLUE));
    }

}
