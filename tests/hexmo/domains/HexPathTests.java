package hexmo.domains;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

class HexPathTests {

	@Test
	void testGetCorrectPathWithRedTiles() {
		//Given the Set
		Set<AxialCordonate> coloredTiles = Set.of(new AxialCordonate(0, -3),
				new AxialCordonate(0, -2), new AxialCordonate(1, -2),
				new AxialCordonate(2, -2), new AxialCordonate(3, -3));
		//Given HexPath
		HexPath hexPath = new HexPath(coloredTiles, 3, HexColor.RED);
		
		//Expected value
		assertEquals(5, hexPath.getPath());
	}
	
	@Test
	void testGetCorrectPathWithBlueTiles() {
		//Given the Set
		Set<AxialCordonate> coloredTiles = Set.of(new AxialCordonate(3, 0),
				new AxialCordonate(2, 0), new AxialCordonate(2, -1),
				new AxialCordonate(2, -2), new AxialCordonate(3, -3));
		//Given HexPath
		HexPath hexPath = new HexPath(coloredTiles, 3, HexColor.BLUE);
		
		//Expected value
		assertEquals(5, hexPath.getPath());
	}
	
	@Test
	void testNoPathWithEmptyTiles() {
		//Given the Set
		Set<AxialCordonate> coloredTiles = Set.of();
		//Given HexPath
		HexPath hexPath = new HexPath(coloredTiles, 3, HexColor.BLUE);
		
		//Expected value
		assertEquals(0, hexPath.getPath());
	}
	
	@Test
	void testNoPathWithRedTiles() {
		//Given the Set
		Set<AxialCordonate> coloredTiles = Set.of(new AxialCordonate(-3, 0), new AxialCordonate(-2, 0),
				new AxialCordonate(-2, 2), new AxialCordonate(-3, 3),
				new AxialCordonate(-2, 1));
		//Given HexPath
		HexPath hexPath = new HexPath(coloredTiles, 3, HexColor.RED);
		
		//Expected value
		assertEquals(5, hexPath.getPath());
	}
	
	@Test
	void testHexPathWithNullValues() {
		//Given Statistique with null values
		assertThrows(NullPointerException.class, () -> new HexPath(null, 3, null));
	}

}
