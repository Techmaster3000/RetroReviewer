import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.File;
import java.io.FileReader;
import java.util.*;

class Game {
    public Boolean onSale;
    private String name;
    private String genre;
    private String platform;
    private double price;
    private int korting;

    public Game(String name, String genre, String platform, double price, int korting) {
        this.name = name;
        this.genre = genre;
        this.platform = platform;
        this.price = price;
        this.onSale = false;
        this.korting = korting;
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

    public double getBasePrice() {
        return price;
    }

    public double getNewPrice() {
        return price - (price * korting / 100);
    }

}

public class Main {
    // Scanner object for user input
    private static final Scanner scanner = new Scanner(System.in);
    static String filepath = System.getProperty("user.dir") + File.separator + "reviews" + File.separator;
    private static HashMap<String, Game> gameMap = null;
    static HashMap<String, Integer> uitverkoopGames = new HashMap<>();

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

    private static void RangLijstSafae() {
        InlezenBestand inlezenBestand = new InlezenBestand();
        inlezenBestand.lezenReview();
        ArrayList<String[]> reviews = inlezenBestand.lezenReview();
        for (String[] s : reviews) {
            System.out.printf("%d %s%n", Integer.valueOf(s[0]), s[1], s[2], s[3]);
        }
//        %s = String
//        %d = Decimaal
//        %.2f = Kommagetal

// reverseOrder() methode
    }
      
    public static String resolveMultipleResults(ArrayList<String> multipleGames) {
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
                } else {
                    System.out.println("Ongeldige keuze, probeer het opnieuw");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ongeldige keuze, probeer het opnieuw");
            }
        }
    }

    public static String gameSearch(String input) {
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
            return resolveMultipleResults(multipleGames);


        } else if (multipleGames.size() == 1) {
            //ask for confirmation
            System.out.println("Bedoelde u: " + gameName + "? (y/n)");
            while (true) {
                String choice = scanner.next();
                if (choice.equals("y")) {
                    return gameName;
                } else if (choice.equals("n")) {
                    return "other*";
                } else {
                    System.out.println("Ongeldige keuze, probeer het opnieuw");
                }
            }
        }
        return gameName;
    }

    public static File reviewFile(String gameNaam, String filename) {

        File file = new File(filename + ".txt");
        int i = 1;
        while (file.exists()) {
            filename = filepath + gameNaam + " review" + i;
            file = new File(filename + ".txt");
            i++;
        }
        return file;
    }

    private static void writeReview() {

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
            String gameSearch = gameSearch(gameNaam);
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
        File file = reviewFile(gameNaam, filename);

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
    public static void toonUitverkoop() {
        System.out.println("Games in de uitverkoop:");
        for (String gameNaam : uitverkoopGames.keySet()) {
            System.out.println(gameNaam + " - originele prijs: " + uitverkoopGames.get(gameNaam) + " - nieuwe prijs: " + uitverkoopGames.get(gameNaam));
        }
        // Druk op enter om terug te gaan naar het hoofdmenu
        System.out.println("Druk op enter om terug te gaan naar het hoofdmenu");
        scanner.nextLine();
        scanner.nextLine();
        // Clear the screen
        System.out.print("\033[H\033[2J");
        System.out.flush();
        mainMenu();
    }
    public static void mainMenu() {
        System.out.println("1. Zie ranglijst");
        System.out.println("2. Geef review over game");
        System.out.println("3. Ga naar uitverkoop");
        System.out.println("4. Exit");
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
                writeReview();
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
            if (genre.equalsIgnoreCase("*") || genreGame.equalsIgnoreCase(genre)) {
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
            System.out.println("Voer het genre opnieuw in:");
            rangLijst();
            Scanner scanner = new Scanner(System.in);
            String newGenre = scanner.nextLine();
            return;
        }

        //print the scores from highest to lowest, print those with the same score in alphabetical order
        gameScores.entrySet().stream()
                .sorted((k1, k2) -> {
                    int compare = k2.getValue().compareTo(k1.getValue());
                    if (compare == 0) {
                        return k1.getKey().compareTo(k2.getKey());
                    } else {
                        return compare;
                    }
                })
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }

    public static void rangLijst() {

        HashSet<String> genres = new HashSet<String>();

        for(String game : gameMap.keySet()){
            genres.add(gameMap.get(game).getGenre());
        }
        ArrayList<String> genreLijst = new ArrayList<>(genres);
        for(String genre : genreLijst){
            System.out.println((genreLijst.indexOf(genre) + 1) + ": " + genre);
        }

        int keuze = scanner.nextInt();
        if(keuze > 0 && keuze <= genreLijst.size()){
            readReviews(genreLijst.get(keuze - 1));

            }


        }


//        System.out.println("Welke genre wilt u zien? (* voor alle genres)");
//        String genre = scanner.next();
//        readReviews(genre);
//        //tell them to press enter to go back to the main menu
//        System.out.println("Druk op enter om terug te gaan naar het hoofdmenu");
//        scanner.nextLine();
//        scanner.nextLine();
//        //clear the screen
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
//        mainMenu();



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
                int korting = Integer.parseInt((String) gameObj.get("sale"));
                Game tempGame = new Game(name, genre, platform, price, korting);
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

    public Boolean yesOrNo(String input) {
        while (true) {
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Ongeldige keuze, probeer het opnieuw");
            }
        }
    }
}