import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ReviewHandler {
    public static File reviewFile(String filepath, String gameNaam, String filename) {

        File file = new File(filename + ".txt");
        int i = 1;
        while (file.exists()) {
            filename = filepath + gameNaam + " review" + i;
            file = new File(filename + ".txt");
            i++;
        }
        return file;
    }
    public static Boolean readReviews(String genre, HashMap<String, Game> gameMap, String filepath, Scanner scanner) {
        File folder = new File(filepath);
        File allFiles[] = folder.listFiles();
        String gameNaam;
        HashMap<String, Double> gameScores = new HashMap<>();
        for (File file : allFiles) {
            QuestionReader reader = new QuestionReader(filepath + file.getName());
            List<String> answers = reader.readAllLines();
            if (!answers.isEmpty()) {
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
        }
        if (gameScores.isEmpty()) {
            System.out.println("Geen reviews gevonden voor dit genre");
            System.out.println("Voer het genre opnieuw in:");
            Main.rangLijst();
            String nieuweGenre = scanner.nextLine();

        }
        System.out.printf("%-50s %-40s %-30s\n", "Game Naam", "Gemiddelde score", "Prijs");
        System.out.println("--------------------------------------------------------------------------------------------------------");

        gameScores.entrySet().stream()
                .sorted((k1, k2) -> {
                    int compare = k2.getValue().compareTo(k1.getValue());
                    if (compare == 0) {
                        return k1.getKey().compareTo(k2.getKey());
                    } else {
                        return compare;
                    }
                })
                .forEach(entry -> {
                    Game game = gameMap.get(entry.getKey());
                    if (game != null) {
                        double prijs;
                        if (game.getNewPrice() < game.getBasePrice()) {
                            prijs = game.getNewPrice();
                        } else {
                            prijs = game.getBasePrice();
                        }
                        System.out.printf("%-50s %-40.1f %-30.2f  \n", entry.getKey(), entry.getValue(), prijs);
                    }
                });


        System.out.println("Druk op enter om terug te gaan naar het hoofdmenu");
        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        return true;
    }


    public static void writeReview(HashMap<String, Game> gameMap, Scanner scanner, GameSearcher gameSearcher, String filepath) {

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
            String gameSearch = gameSearcher.gameSearch(gameNaam, gameMap);
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
        File file = reviewFile(filepath, gameNaam, filename);

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
        scanner.nextLine();
        Enquete enquete = new Enquete();
        enquete.Beginscherm();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //clear the screen
        System.out.flush();
//        scanner.next();
        Main.mainMenu();


    }


}
