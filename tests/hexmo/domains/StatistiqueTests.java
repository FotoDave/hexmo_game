package hexmo.domains;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class StatistiqueTests {

	@Test
	void testStatistiqueWithCorrectsPlayers() {
		//Given the player One
		Player playerOne = new Player("P1", HexColor.RED);
		//Given the player Two
		Player playerTwo = new Player("P2", HexColor.BLUE);
		
		Statistique stat = new Statistique(playerOne, playerTwo);
		playerOne.updateScore(5.0);
		playerTwo.updateScore(2.5);
		stat.saveResult("P1 (RED)", 5.0, 2.5, 24);
		List<String> expectedList = List.of("RESULTAT DE LA PARTIE", "",
				"Vainqueur P1 (RED)", "Score du vainqueur 5.0",
				"Score du perdant 2.5", "Taux de remplissage 24%", "",
				"STATISTIQUE", "", "Score de P1 5.0", "Score de P2 2.5",
				"Taux de remplissage moyen 24%", "");
		//Expected value
		assertArrayEquals(expectedList.toArray(new String[0]), 
			stat.getMessageOfGameOverView().toArray(new String[0]));
	}
	
	@Test
	void testStatistiqueWithNoPlayer() {
		//Given Statistique with null values
		assertThrows(NullPointerException.class, () -> new Statistique(null, null));
	}

}
