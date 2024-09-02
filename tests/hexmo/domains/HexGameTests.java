package hexmo.domains;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class HexGameTests {

	@Test
	void testHexgameWithNullValues() {
		assertThrows(NullPointerException.class, () -> new HexGame(null, null, null, null));
	}
	
	@Test
	void testHexgameSwapping() {
		//Given HexGame
		Player playerOne = new Player("P1", HexColor.RED);
		Player playerTwo = new Player("P2", HexColor.BLUE);
		HexBoard board = new HexBoard(3);
		Statistique stat = new Statistique(playerOne, playerTwo);
		HexGame hexGame = new HexGame(playerOne, playerTwo, board, stat);
		
		assertTrue(hexGame.canSwap());
		
		hexGame.swapping();
		hexGame.swapping();
		
		assertTrue(hexGame.isUseSwap());
		assertFalse(hexGame.canSwap());
	}
	
	@Test
	void testHexgameMovement() {
		Player playerOne = new Player("P1", HexColor.RED);
		Player playerTwo = new Player("P2", HexColor.BLUE);
		HexBoard board = new HexBoard(1);
		Statistique stat = new Statistique(playerOne, playerTwo);
		HexGame hexGame = new HexGame(playerOne, playerTwo, board, stat);
		
		List<Integer> actualList = hexGame.movement(0, 1);
		
		List<Integer> expectedList = List.of(0,1);
		assertEquals(expectedList.size(), actualList.size());
	}
	
	@Test
	void testHexgameCanNotMove() {
		Player playerOne = new Player("P1", HexColor.RED);
		Player playerTwo = new Player("P2", HexColor.BLUE);
		HexBoard board = new HexBoard(1);
		Statistique stat = new Statistique(playerOne, playerTwo);
		HexGame hexGame = new HexGame(playerOne, playerTwo, board, stat);
		hexGame.setActivePlayer(playerOne);
		
		List<Integer> actualList = hexGame.movement(2, 1);
		List<Integer> expectedList = List.of(0,1);
		assertNotEquals(expectedList.size(), actualList.size());
	}
	
	@Test
	void testListCoordonate() {
		Player playerOne = new Player("P1", HexColor.RED);
		Player playerTwo = new Player("P2", HexColor.BLUE);
		HexBoard board = new HexBoard(1);
		Statistique stat = new Statistique(playerOne, playerTwo);
		HexGame hexGame = new HexGame(playerOne, playerTwo, board, stat);
		
		List<List<Integer>> actualList = hexGame.getListCordonateBoard();
		
		List<List<Integer>> expectedList = new ArrayList<>(List.of(
	            List.of(0, 0),
	            List.of(1, 0),
	            List.of(-1, 0),
	            List.of(0, 1),
	            List.of(0, -1),
	            List.of(1, -1),
	            List.of(-1, 1)
	        ));
		assertEquals(expectedList.size(), actualList.size());
	}
	
	@Test
	void testGetHexTileColor() {
		Player playerOne = new Player("P1", HexColor.RED);
		Player playerTwo = new Player("P2", HexColor.BLUE);
		HexBoard board = new HexBoard(1);
		Statistique stat = new Statistique(playerOne, playerTwo);
		HexGame hexGame = new HexGame(playerOne, playerTwo, board, stat);
		
		assertEquals(hexGame.getHexTileColor(0, 1), HexColor.UNKNOWN);
	}
	
	@Test
	void testHexGameMessagePlayerOne() {
		Player playerOne = new Player("P1", HexColor.RED);
		Player playerTwo = new Player("P2", HexColor.BLUE);
		HexBoard board = new HexBoard(1);
		Statistique stat = new Statistique(playerOne, playerTwo);
		HexGame hexGame = new HexGame(playerOne, playerTwo, board, stat);
		
		hexGame.setActivePlayer(playerOne);
		
		assertEquals("Case Active (q : 0, r : 0, s : 0)", 
				hexGame.getActiveTileMessage());
		assertEquals("P1 (RED) vs P2 (BLUE)", hexGame.getGameMessage());
		assertEquals("Au tour de P1 (RED)", hexGame.getPlayerMessage());
		hexGame.switchPlayer();
	}
	
	@Test
	void testHexGameMessagePlayerTwo() {
		Player playerOne = new Player("P1", HexColor.RED);
		Player playerTwo = new Player("P2", HexColor.BLUE);
		HexBoard board = new HexBoard(1);
		Statistique stat = new Statistique(playerOne, playerTwo);
		HexGame hexGame = new HexGame(playerOne, playerTwo, board, stat);
		
		hexGame.setActivePlayer(playerTwo);
		hexGame.canPlay();
		assertEquals("Case Active (q : 0, r : 0, s : 0)", 
				hexGame.getActiveTileMessage());
		assertEquals("P1 (RED) vs P2 (BLUE)", hexGame.getGameMessage());
		assertEquals("Au tour de P2 (BLUE)", hexGame.getPlayerMessage());
		hexGame.switchPlayer();
	}
	
	@Test
	void testSetActiveTileWithColorRed() {
		Player playerOne = new Player("P1", HexColor.RED);
		Player playerTwo = new Player("P2", HexColor.BLUE);
		HexBoard board = new HexBoard(1);
		Statistique stat = new Statistique(playerOne, playerTwo);
		HexGame hexGame = new HexGame(playerOne, playerTwo, board, stat);
		
		hexGame.setActivePlayer(playerOne);
		hexGame.setActiveTile();
	}
	
	@Test
	void testSetActiveTileWithColorBlue() {
		Player playerOne = new Player("P1", HexColor.RED);
		Player playerTwo = new Player("P2", HexColor.BLUE);
		HexBoard board = new HexBoard(1);
		Statistique stat = new Statistique(playerOne, playerTwo);
		HexGame hexGame = new HexGame(playerOne, playerTwo, board, stat);
		
		hexGame.setActivePlayer(playerTwo);
		hexGame.setActiveTile();
		hexGame.canPlay();
	}
	
	@Test
	void testGameOverWhenPlayerOneWin() {
		Player playerOne = new Player("P1", HexColor.RED);
		Player playerTwo = new Player("P2", HexColor.BLUE);
		HexBoard board = new HexBoard(3);
		Statistique stat = new Statistique(playerOne, playerTwo);
		HexGame hexGame = new HexGame(playerOne, playerTwo, board, stat);
		
		board.updateHextileInBoard(new HexTile(new AxialCordonate(0, -3), HexColor.RED));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(0, -2), HexColor.RED));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(1, -2), HexColor.RED));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(2, -2), HexColor.RED));
        
        hexGame.setActivePlayer(playerOne);
        hexGame.movement(3, -3);
        hexGame.setActiveTile();
        
        assertTrue(hexGame.gameOver());
        
	}
	
	
	@Test
	void testGameOverWhenPlayerTwoWin() {
		Player playerOne = new Player("P1", HexColor.RED);
		Player playerTwo = new Player("P2", HexColor.BLUE);
		HexBoard board = new HexBoard(3);
		Statistique stat = new Statistique(playerOne, playerTwo);
		HexGame hexGame = new HexGame(playerOne, playerTwo, board, stat);
		
		board.updateHextileInBoard(new HexTile(new AxialCordonate(0, -3), HexColor.BLUE));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(0, -2), HexColor.BLUE));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(-1, -1), HexColor.BLUE));
        board.updateHextileInBoard(new HexTile(new AxialCordonate(-2, 0), HexColor.BLUE));
        
        hexGame.setActivePlayer(playerTwo);
        hexGame.movement(-3, 0);
        hexGame.setActiveTile();
        
        assertTrue(hexGame.gameOver());
        
	}
}
