import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameSearcher {
    private static Scanner scanner;

    public GameSearcher(Scanner scanner) {
        this.scanner = scanner;
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

    public static String gameSearch(String input, HashMap<String, Game> gameMap) {
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

}
