import java.io.File;
import java.util.*;

public class Main {
    // Scanner object for user input
    public static Scanner scanner = new Scanner(System.in);
    private static final String filepath = System.getProperty("user.dir") + File.separator + "reviews" + File.separator;
    static GameSearcher gameSearcher = new GameSearcher(scanner);
    static ReviewHandler reviewHandler = new ReviewHandler();
    static JSONHandler jsonHandler = new JSONHandler();
    static UitverkoopSysteem saleSystem = new UitverkoopSysteem();
    private static HashMap<String, Game> gameMap = null;

    public static void spaceInvader() {
        System.out.println("\u001B[97m          ############");
        System.out.println("     ####################");
        System.out.println("    ########################");
        System.out.println("  ####  ####  ####  ####  ####");
        System.out.println("################################");
        System.out.println("    ######    ####    ######");
        System.out.println("      ##                ##\n\u001B[32m");
    }

    public static void generateExitText() {
        switch (new Random().nextInt(5) + 1) {
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
            case 5:
                System.out.println("\"It's dangerous to go alone! Take this.\"");
                break;
            default:
                System.out.println("Bye Bye!");


        }
    }

    public static void mainMenu() {
        System.out.println("1. Zie ranglijst");
        System.out.println("2. Geef review over game");
        System.out.println("3. Ga naar uitverkoop");
        System.out.println("4. Korting geven");
        System.out.println("5. Exit");
        int input = 0;
        try {
            input = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            scanner.next();
            //clear the screen
            System.out.print("\033[H\033[2J");
            System.out.flush();
            mainMenu();
        }
        switch (input) {
            case 1:
                rangLijst();
                break;

            case 2:
                scanner.nextLine();
                ReviewHandler.writeReview(gameMap, scanner, gameSearcher, filepath);
                mainMenu();
                break;

            case 3:
                saleSystem.toonUitverkoop(scanner, gameMap);
                break;
            case 4:
                saleSystem.geefKorting(scanner, gameMap, jsonHandler);
                break;
            case 5:
                generateExitText();
                System.exit(0);
                break;
            default:
                System.out.println("Invalid input");
                System.out.flush();
                mainMenu();

        }
    }


    public static void rangLijst() {

        HashSet<String> genres = new HashSet<String>();

        for (String game : gameMap.keySet()) {
            genres.add(gameMap.get(game).getGenre());
        }
        ArrayList<String> genreLijst = new ArrayList<>(genres);
        for (String genre : genreLijst) {
            System.out.println((genreLijst.indexOf(genre) + 1) + ": " + genre);
        }
        System.out.println("*: Alle reviews");
        scanner.nextLine();
        while (true) {
            String keuze = scanner.nextLine();
            try {
                if (Integer.parseInt(keuze) > 0 && Integer.parseInt(keuze) <= genreLijst.size()) {
                    ReviewHandler.readReviews(genreLijst.get(Integer.parseInt(keuze) - 1), gameMap, filepath, scanner);
                    break;

                }
            } catch (Exception e) {
                if (keuze.equals("*")) {
                    reviewHandler.readReviews(keuze, gameMap, filepath, scanner);
                    break;

                } else {
                    System.out.println("Ongeldige invoer");
                }
            }
        }
        //tell them to press enter to go back to the main menu
        System.out.println("Druk op enter om terug te gaan naar het hoofdmenu");
        scanner.nextLine();
        //clear the screen
        System.out.print("\033[H\033[2J");
        System.out.flush();

        mainMenu();
    }


    public static void main(String[] args) {
        spaceInvader();
        gameMap = jsonHandler.readJSON("GamesDB.json", gameMap);
        mainMenu();
    }

}