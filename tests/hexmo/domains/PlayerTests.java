package hexmo.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTests {

	@Test
	void testPlayerOne() {
		//Given the player One
		Player playerOne = new Player("P1", HexColor.RED);
		//Expected playerName
		assertEquals("P1", playerOne.getName());
		//Expected HexColor
		assertEquals(HexColor.RED, playerOne.getColor());
		//Expected Description
		assertEquals("P1 (RED)", playerOne.getPlayerDescription());
		//Expected Score
		assertEquals(0, playerOne.getScore());
	}
	
	@Test
	void testPlayerTwo() {
		//Given the player Two
		Player playerTwo = new Player("P2", HexColor.BLUE);
		//Expected playerName
		assertEquals("P2", playerTwo.getName());
		//Expected HexColor
		assertEquals(HexColor.BLUE, playerTwo.getColor());
		//Expected Description
		assertEquals("P2 (BLUE)", playerTwo.getPlayerDescription());
		//Expected Score
		assertEquals(0, playerTwo.getScore());
	}
	
	@Test
	void testNullPlayer() {
		//Given player with null values
		assertThrows(NullPointerException.class, () -> new Player(null, null));
	}
	
	@Test
	void testSetColorAndScorePlayer() {
		//Given the player One
		Player player = new Player("P1", HexColor.RED);
		//Set color 
		player.setColor(HexColor.BLUE);
		//Update Score 
		player.updateScore(7.5);
		//Expected HexColor
		assertEquals(HexColor.BLUE, player.getColor());
		//Expected Score
		assertEquals(7.5, player.getScore());
	}

}
