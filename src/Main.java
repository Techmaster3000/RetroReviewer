import java.io.File;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    // Scanner object for user input
    private static final Scanner scanner = new Scanner(System.in);
    // Filepath for the reviews
    static String localDir = System.getProperty("user.dir");
    static String Filepath = localDir + File.separator + "reviews" + File.separator;

    public static void SpaceInvader() {
        System.out.println("\u001B[97m          ############");
        System.out.println("     ####################");
        System.out.println("    ########################");
        System.out.println("  ####  ####  ####  ####  ####");
        System.out.println("################################");
        System.out.println("    ######    ####    ######");
        System.out.println("      ##                ##\n\u001B[32m");
    }

    /**
     * Generates and prints a random exit text to the console.
     */
    public static void GenerateExitText() {
        switch (new Random().nextInt(4) + 1) {
            case 1:
                System.out.println("Game Over!");
                break;
            case 2:
                System.out.println("\"Talk about a low-budget flight! No food or movies? I'm outta here!\"");
                break;
            case 3:
                System.out.println("\"Snake? Snaaaaaaake!\"");
                break;
            case 4:
                System.out.println("\"Sayonara, Shadow the Hedgehog!\"");
                break;
            default:
                System.out.println("Bye Bye!");

        }
    }

    /**
     * Handles the review process from the customer.
     * Asks for game name, gameplay, graphics, storyline ratings, and a review comment.
     * Writes the review to a file.
     */
    private static void reviewKlant() {

        System.out.println("Geef de naam van de game");
        String gameNaam = scanner.nextLine();

        System.out.println("Beoordeel de gameplay van 1 tot 10: ");
        int gameplay = scanner.nextInt();

        System.out.println("Beoordeel de graphics van 1 tot 10: ");
        int graphics = scanner.nextInt();

        System.out.println("Beoordeel de verhaallijn van 1 tot 10: ");
        int verhaallijn = scanner.nextInt();

        System.out.println("Geef een toelichting op de review en sluit af met een #: ");
        StringBuilder toelichting = new StringBuilder();
        while (!scanner.hasNext("#")) {
            toelichting.append(scanner.nextLine() + "\n");
        }

        double totaalScore = ((gameplay + graphics + verhaallijn) / 3);

        System.out.println("Review voor: " + gameNaam);
        System.out.println("Gameplay: " + gameplay);
        System.out.println("Graphics: " + graphics);
        System.out.println("Verhaallijn: " + verhaallijn);
        System.out.println("Uw totaalscore is: " + totaalScore);
        System.out.println("Toelichting: " + toelichting);

        String filename = Filepath + gameNaam + " review";
        File file = new File(filename + ".txt");
        int i = 1;
        while (file.exists()) {
            filename = Filepath + gameNaam + " review" + i;
            file = new File(filename + ".txt");
            i++;
        }

        List<String> answers = List.of(gameNaam, "Gameplay: " + gameplay, "Graphics: " + graphics, "Verhaallijn: " + verhaallijn, "Totaalscore: " + totaalScore, "Toelichting: " + toelichting);
        try {
            if (!file.createNewFile()) {
                System.out.println("File already exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Write write = new Write(filename + ".txt");
        write.writeAllLines(answers);
        System.out.println("Bedankt voor uw review!");
        scanner.reset();
        //delay 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //clear the screen
        System.out.print(" \\033[H\\033[2J");
        System.out.flush();
        scanner.next();
        MainMenu();

    }


    /**
     * Displays the main menu and handles user input for navigation.
     */
    public static void MainMenu() {
        System.out.println("1. Zie ranglijst");
        System.out.println("2. Geef review over game");
        System.out.println("3. Ga naar uitverkoop");
        System.out.println("4. Exit");
        //handle input mismatch exception
        int input = 0;
        try {
            input = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            scanner.next();
            //clear the screen
            System.out.print("\033[H\033[2J");
            System.out.flush();
            MainMenu();
        }
        switch (input) {
            case 1:
                //rangLijst();
                break;

            case 2:
                scanner.nextLine();
                reviewKlant();
                break;

            case 3:
                //uitverkoop();
                break;
            case 4:
                GenerateExitText();
                System.exit(0);

                break;
            default:
                System.out.println("Invalid input");
                System.out.flush();
                MainMenu();

        }
    }

    /**
     * Main method for the application.
     * Displays the Space Invader ASCII art and opens the main menu.
     */
    public static void main(String[] args) {
        SpaceInvader();
        MainMenu();
    }
}
