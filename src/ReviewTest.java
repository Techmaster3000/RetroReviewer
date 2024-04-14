import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.util.HashMap;
import java.io.File;
import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {
    @TempDir
    Path tempDir;

    // Test of reviewFile correct een nieuw bestand creëert wanneer er al een bestaand bestand is.
    // Deze test zorgt ervoor dat de methode betrouwbaar een nieuw bestand aanmaakt met een unieke naam
    // in de aanwezigheid van een bestaand bestand, wat cruciaal is om bestandsoverschrijdingen te voorkomen.


    @Test
    void reviewsFileTest() {
        String gameName = "TestGame";
        String baseFilename = tempDir.resolve("TestGame review").toString();

        // Creating an existing file to simulate initial condition
        File existingFile = new File(baseFilename + "1.txt");
        try {
            assertTrue(existingFile.createNewFile(), "File 1 should be successfully created");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Running the method under test
        File resultFile = ReviewHandler.reviewFile(existingFile.getPath(), gameName, baseFilename + "2");

        // Determine the expected new file name
        File expectedFile2= new File(baseFilename + "2.txt"); // Adjust based on debug output
        assertEquals(expectedFile2.getPath(), resultFile.getPath(), "The expected new filename should match.");
    }
    @Test
    //write a test for ReviewHandler
    public void testReviewHandler() {
        // Create a new game
        String filepath = System.getProperty("user.dir") + File.separator + "reviews" + File.separator;
        Game game = new Game("Mega Man 5", "Platformer", "NES", 10.0, 0);
        // Create a new game map
        HashMap<String, Game> gameMap = new HashMap<>();
        // Add the game to the game map
        gameMap.put("Mega Man 5", game);
        // Create a new review file

        // Create a new review handler
        InputStream in = new ByteArrayInputStream("Mega Man 5\n10\n10\n10\n#\nn\n5\n".getBytes());
        Scanner scanner = new Scanner(in);
        File file = new File(filepath + "Mega Man 5 review.txt");
        Main.scanner = scanner;
        //give System.in the int 5 to simulate user input
        // Write the review to the file
        ReviewHandler.writeReview(gameMap, scanner, new GameSearcher(scanner), filepath);

        // Check if the file was created
        assertTrue(file.exists());
        file.delete();

    }
}