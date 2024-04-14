import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Scanner;
import java.util.HashMap;
import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GamesTest {
    @Test
    public void testSearchSingleGame() {
        GameSearcher gameSearcher = new GameSearcher(new Scanner(System.in));
        HashMap<String, Game> gameMapTest = new HashMap<>();
        Game game = new Game("Super Mario Bros", "Platformer", "NES", 10.0, 0);
        gameMapTest.put("Super Mario Bros", game);
        assertEquals(game.getName(), gameSearcher.gameSearch("Super Mario Bros", gameMapTest));
    }
    @Test
    public void testSearchMultipleGames() {
        GameSearcher gameSearcher = new GameSearcher(new Scanner(System.in));
        HashMap<String, Game> gameMapTest = new HashMap<>();
        Game game1 = new Game("Super Mario Bros", "Platformer", "NES", 10.0, 0);
        Game game2 = new Game("Super Mario Bros 2", "Platformer", "NES", 10.0, 0);
        gameMapTest.put("Super Mario Bros", game1);
        gameMapTest.put("Super Mario Bros 2", game2);
        assertEquals("Super Mario Bros", gameSearcher.gameSearch("Super Mario Bros", gameMapTest));
    }
    @Test
    public void testSearchNoGame() {
        GameSearcher gameSearcher = new GameSearcher(new Scanner(System.in));
        HashMap<String, Game> gameMapTest = new HashMap<>();
        Game game = new Game("Super Mario Bros", "Platformer", "NES", 10.0, 0);
        gameMapTest.put("Super Mario Bros", game);
        assertEquals(null, gameSearcher.gameSearch("Super Mario Bros 2", gameMapTest));
    }
    @Test
    public void testAutoComplete() {
        String simulatedUserInput = "y\n"; // The \n is for simulating the enter key
        InputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);
        GameSearcher gameSearcher = new GameSearcher(scanner);
        HashMap<String, Game> gameMapTest = new HashMap<>();
        Game game = new Game("Super Mario Bros", "Platformer", "NES", 10.0, 0);
        gameMapTest.put("Super Mario Bros 2", game);
        //give simulated user input
        assertEquals("Super Mario Bros 2", gameSearcher.gameSearch("Super Mario", gameMapTest));
    }
    @Test
    public void testAutoCompleteReject() {
        String simulatedUserInput = "n\n"; // The \n is for simulating the enter key
        InputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);
        GameSearcher gameSearcher = new GameSearcher(scanner);
        HashMap<String, Game> gameMapTest = new HashMap<>();
        Game game = new Game("Super Mario Bros", "Platformer", "NES", 10.0, 0);
        gameMapTest.put("Super Mario Bros 2", game);
        //give simulated user input
        assertEquals("other*", gameSearcher.gameSearch("Super Mario", gameMapTest));
    }
    @Test
    public void testPriceCalculation() {
        Game game = new Game("Super Mario Bros", "Platformer", "NES", 10.0, 20);
        double expectedPrice = 8.0;
        assertEquals(expectedPrice, game.getNewPrice());
    }
}
