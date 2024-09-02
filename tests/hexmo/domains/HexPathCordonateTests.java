package hexmo.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HexPathCordonateTests {

	@Test
	void testHexPathCordonateWithCorrectsValues() {
		//Given the AxialCordonate
		AxialCordonate cord = new AxialCordonate(0, 2);
		//Given HexPathCordonate
		HexPathCordonate hp = new HexPathCordonate(cord, 'R', 0);
	
		//Expected value
		assertEquals(cord, hp.getAxialCordonate());
		assertEquals('R', hp.getBord());
		assertEquals(0, hp.getDistance());
	}
	
	@Test
	void testHexPathCordonateWithNullValues() {
		//Given Statistique with null values
		assertThrows(NullPointerException.class, () -> new HexPathCordonate(null, ' ',0));
	}

}
