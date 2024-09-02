package hexmo.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AxialCordonateTests {

	@Test
	void testAxialCordonateCentrale() {
		//Given Axial Cordonate
		AxialCordonate cordonate = new AxialCordonate(0, 0);
		//Expected values
		assertEquals(0, cordonate.getQ());
		assertEquals(0, cordonate.getR());
		assertEquals(0, cordonate.getS());
	}
	
	@Test
	void testNegativeAxialCordonate() {
		//Given Axial Cordonate
		AxialCordonate cordonate = new AxialCordonate(-1, -2);
		//Expected values
		assertEquals(-1, cordonate.getQ());
		assertEquals(-2, cordonate.getR());
		assertEquals(3, cordonate.getS());
	}
	
	@Test
	void testPositiveAxialCordonate() {
		//Given Axial Cordonate
		AxialCordonate cordonate = new AxialCordonate(3, 2);
		//Expected values
		assertEquals(3, cordonate.getQ());
		assertEquals(2, cordonate.getR());
		assertEquals(-5, cordonate.getS());
	}
	
	@Test
	void testSetValuesAxialCordonate() {
		//Given Axial Cordonate
		AxialCordonate cordonate = new AxialCordonate(3, 2);
		//Set values
		cordonate.setQ(1);
		cordonate.setR(2);
		//Expected values
		assertEquals(1, cordonate.getQ());
		assertEquals(2, cordonate.getR());
		assertEquals(-3, cordonate.getS());
	}
	
	@Test
    public void testHashCode() {
		//Given axials cordonates
        AxialCordonate cordonate1 = new AxialCordonate(1, 2);
        AxialCordonate cordonate2 = new AxialCordonate(1, 2);
        //Expected values
        assertEquals(cordonate1.hashCode(), cordonate2.hashCode());
    }
	
	@Test
    public void testEquals() {
		//Given axials cordonates
        AxialCordonate cordonate1 = new AxialCordonate(0, 2);
        AxialCordonate cordonate2 = new AxialCordonate(0, 2);
        Player player = new Player("P1", HexColor.BLUE);
        //Expected values
        assertEquals(cordonate1, cordonate2);
        assertEquals(cordonate1, cordonate1);
        assertNotEquals(cordonate1, player);
    }
	
	@Test
    public void testNotEqualsWithSameObject() {
		//Given axials cordonates
        AxialCordonate cordonate1 = new AxialCordonate(0, 2);
        AxialCordonate cordonate3 = new AxialCordonate(0, 1);
        AxialCordonate cordonate4 = new AxialCordonate(1, 2);
        //Expected values
        assertNotEquals(cordonate1, cordonate3);
        assertNotEquals(cordonate1, cordonate4);
    }
	
	@Test
    public void testCompareTo() {
		//Given axials cordonates
        AxialCordonate cordonate1 = new AxialCordonate(1, 1);
        AxialCordonate cordonate2 = new AxialCordonate(1, 1);
        AxialCordonate cordonate3 = new AxialCordonate(2, 1);
        AxialCordonate cordonate4 = new AxialCordonate(0, 1);
        //Expected values
        assertEquals(0, cordonate1.compareTo(cordonate2));
        assertEquals(Integer.MIN_VALUE, cordonate1.compareTo(null));
        assertEquals(2, cordonate3.compareTo(cordonate4));
    }

}
