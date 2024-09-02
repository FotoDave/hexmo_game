package hexmo.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HexTileTests {

	@Test
	void testRedHextile() {
		//Given Hextile
		HexTile hexTile = new HexTile(new AxialCordonate(1, 2), HexColor.RED);
		//Expected values
		assertEquals(HexColor.RED, hexTile.getColor());
		assertEquals(new AxialCordonate(1, 2), hexTile.getAxialCordonate());
	}
	
	@Test
	void testBlueHextile() {
		//Given Hextile
		HexTile hexTile = new HexTile(new AxialCordonate(0, 1), HexColor.BLUE);
		//Expected values
		assertEquals(HexColor.BLUE, hexTile.getColor());
		assertEquals(new AxialCordonate(0, 1), hexTile.getAxialCordonate());
	}
	
	@Test
	void testNullHexTile() {
		//Given Hextile with null values
		assertThrows(NullPointerException.class, () -> new HexTile(null, null));
	}
	
	@Test
	void testSetHexTileValue() {
		//Given Hextile
		HexTile hexTile = new HexTile(new AxialCordonate(0, 1), HexColor.BLUE);
		//Set values
		hexTile.setAxialCordonate(new AxialCordonate(1, 2));
		hexTile.setColor(HexColor.RED);
		//Expected values
		assertEquals(HexColor.RED, hexTile.getColor());
		assertEquals(new AxialCordonate(1, 2), hexTile.getAxialCordonate());
	}
	
	@Test
	void testHexTilePixelCordonate() {
		//Given Hextile
		HexTile hexTile = new HexTile(new AxialCordonate(1, 1), HexColor.RED);
		//Expected values
		assertEquals(2.598076105117798, hexTile.getPixelCoordonateX());
		assertEquals(1.5, hexTile.getPixelCoordonateY());
	}

}
