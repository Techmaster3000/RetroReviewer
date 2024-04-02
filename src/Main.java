import java.io.File;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.FileReader;

class SearchException extends Exception {
    public SearchException() {
    }

    public SearchException(String message) {
        super(message);
    }
}

class Game {
    private String name;
    private String genre;
    private String platform;
    private double price;

    public Game(String name, String genre, String platform, double price) {
        this.name = name;
        this.genre = genre;
        this.platform = platform;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getPlatform() {
        return platform;
    }

    public double getPrice() {
        return price;
    }

}

public class Main {
    // Scanner object for user input
    private static final Scanner scanner = new Scanner(System.in);
    private static HashMap<String, Game> gameMap = null;

    // Filepath for the reviews
    static String localDir = System.getProperty("user.dir");
    static String filepath = localDir + File.separator + "reviews" + File.separator;


    public static void spaceInvader() {
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
    public static void generateExitText() {
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
    //write a function that checks if a string is equal to the first part of a single key in the hashmap and then returns the key, returns false if multiple keys or none are found
    public static String completeInput(String input) {
        String gameName = null;
        ArrayList<String> multipleGames = new ArrayList<>();

        for (String key : gameMap.keySet()) {
            if (key.startsWith(input)) {
                multipleGames.add(key);
                gameName = key;
            }
            if (key.equals(input)) {
                return key;
            }
        }
        if (multipleGames.size() > 1) {
            System.out.println("Meerdere games gevonden, kies de juiste game: ");
            for (String game : multipleGames) {
                System.out.println((multipleGames.indexOf(game) + 1) + ". " + game);
            }
            System.out.println((multipleGames.size() + 1) + ". Geen van bovenstaande");



            while (true) {
                try {
                    int choice = scanner.nextInt();
                    if (choice > 0 && choice <= multipleGames.size()) {
                        return multipleGames.get(choice - 1);
                    } else if (choice == multipleGames.size() + 1) {
                        return "other*";
                    }
                    else {
                        System.out.println("Ongeldige keuze, probeer het opnieuw");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Ongeldige keuze, probeer het opnieuw");
                }
            }


        }
        return gameName;
    }

    private static void reviewKlant() {

        String gameNaam = null;
        Boolean found = false;
        while (!found) {
            System.out.println("Geef de naam van de game");
            //handle empty input

            gameNaam = scanner.nextLine();
            while (gameNaam.isEmpty()) {
                gameNaam = scanner.nextLine();
            }
            gameNaam = gameNaam.replaceAll(":", " -");
            String gameSearch = completeInput(gameNaam);
            if (gameSearch == null) {
                System.out.println("Geen game gevonden, probeer het opnieuw");
            } else if (gameSearch.equals("other*")) {
                found = false;
                scanner.nextLine();

            } else {
                gameNaam = gameMap.get(gameSearch).getName();
                found = true;
            }
        }
        String genre = gameMap.get(gameNaam).getGenre();

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
        toelichting = new StringBuilder(toelichting.toString().replace("#", ""));

        double totaalScore = ((gameplay + graphics + verhaallijn) / 3);

        System.out.println("Review voor: " + gameNaam);
        System.out.println("Genre: " + genre);
        System.out.println("Gameplay: " + gameplay);
        System.out.println("Graphics: " + graphics);
        System.out.println("Verhaallijn: " + verhaallijn);
        System.out.println("Uw totaalscore is: " + totaalScore);
        System.out.println("Toelichting: " + toelichting);

        String filename = filepath + gameNaam + " review";
        File file = new File(filename + ".txt");
        int i = 1;
        while (file.exists()) {
            filename = filepath + gameNaam + " review" + i;
            file = new File(filename + ".txt");
            i++;
        }

        List<String> answers = List.of(gameNaam, "Genre: " + genre, "Gameplay: " + gameplay, "Graphics: " + graphics, "Verhaallijn: " + verhaallijn, "Totaalscore: " + totaalScore, "Toelichting: " + toelichting);
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
        //delay 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //clear the screen
        System.out.flush();
        scanner.next();
        mainMenu();

    }


    public static void mainMenu() {
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
            mainMenu();
        }
        switch (input) {
            case 1:
                rangLijst();
                break;

            case 2:
                scanner.nextLine();
                reviewKlant();
                break;

            case 3:

                break;
            case 4:
                generateExitText();
                System.exit(0);

                break;
            default:
                System.out.println("Invalid input");
                System.out.flush();
                mainMenu();

        }
    }

    //create a method to read the reviews from the file and create an average score
    public static void readReviews(String genre) {
        File folder = new File(filepath);
        File allFiles[] = folder.listFiles();
        String gameNaam;
        HashMap<String, Double> gameScores = new HashMap<>();
        for (File file : allFiles) {
            QuestionReader reader = new QuestionReader(filepath + file.getName());
            List<String> answers = reader.readAllLines();
            //save the first line to a string
            gameNaam = answers.get(0);
            String genreGame = answers.get(1).substring(7);

            double totaalScore = Math.round(Double.parseDouble(answers.get(5).substring(12)) * 10) / 10.0;
            if (genre.equals("*") || genreGame.equals(genre)) {
                if (gameScores.containsKey(gameNaam)) {
                    double newScore = (gameScores.get(gameNaam) + totaalScore) / 2;
                    gameScores.put(gameNaam, (double) Math.round((newScore * 10) / 10.0));
                } else {
                    gameScores.put(gameNaam, totaalScore);
                }
            }
        }
        if (gameScores.isEmpty()) {
            System.out.println("Geen reviews gevonden voor dit genre");
            return;
        }

        //print the scores from highest to lowest
        gameScores.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                .forEach(k -> System.out.println(k.getKey() + ": " + k.getValue()));
    }

    public static void rangLijst() {
        //ask for which genre the user wants to see the scores
        System.out.println("Welke genre wilt u zien? (* voor alle genres)");
        String genre = scanner.next();
        readReviews(genre);
        //tell them to press enter to go back to the main menu
        System.out.println("Druk op enter om terug te gaan naar het hoofdmenu");
        scanner.nextLine();
        scanner.nextLine();
        //clear the screen
        System.out.print("\033[H\033[2J");
        System.out.flush();
        mainMenu();


    }

    //create a method that reads a JSON file and prints the contents to the console
    public static void readJSON(String filename) {
        JSONParser parser = new JSONParser();
        JSONArray games = null;

        try {
            Object obj = parser.parse(new FileReader(filename));
            if (obj instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) obj;
                games = (JSONArray) jsonObject.get("games");
                // process games array
            } else if (obj instanceof JSONArray) {
                games = (JSONArray) obj;
                // process games array
            } else {
                //terminate the program\
                throw new Exception("Invalid JSON file");


            }
            gameMap = new HashMap<>();
            for (Object game : games) {
                JSONObject gameObj = (JSONObject) game;
                String name = (String) gameObj.get("name");
                String genre = (String) gameObj.get("genre");
                String platform = (String) gameObj.get("platform");
                double price = Double.parseDouble((String) gameObj.get("price"));
                Game tempGame = new Game(name, genre, platform, price);
                gameMap.put(name, tempGame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        spaceInvader();
        readJSON("GamesDB.json");
        mainMenu();
    }
}
