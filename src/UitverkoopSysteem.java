import java.util.HashMap;
import java.util.Scanner;

public class UitverkoopSysteem {
    public static void toonUitverkoop(Scanner scanner, HashMap<String, Game> gameMap) {
        HashMap<String, Integer> uitverkoopGames = new HashMap<>();
        System.out.println("Games in de uitverkoop:");
        for (String gameNaam : gameMap.keySet()) {
            if (gameMap.get(gameNaam).onSale) {
                System.out.println(gameNaam + " - originele prijs: " + gameMap.get(gameNaam).getBasePrice() + " - nieuwe prijs: " + gameMap.get(gameNaam).getNewPrice());
                uitverkoopGames.put(gameNaam, (int) gameMap.get(gameNaam).getNewPrice());
            }
        }
        // Druk op enter om terug te gaan naar het hoofdmenu
        System.out.println("Druk op enter om terug te gaan naar het hoofdmenu");
        scanner.nextLine();
        scanner.nextLine();
        // Clear the screen
        System.out.print("\033[H\033[2J");
        System.out.flush();
        Main.mainMenu();
    }

    public static void geefKorting(Scanner scanner, HashMap<String, Game> gameMap, JSONHandler jsonHandler){
        scanner.nextLine(); // Consume newline left-over
        String gameNaam = null;
        Boolean found = false;
        while (!found) {
            System.out.println("Geef de naam van de game waar u korting voor wilt geven");
            //handle empty input

            gameNaam = scanner.nextLine();
            while (gameNaam.isEmpty()) {
                gameNaam = scanner.nextLine();
            }
            gameNaam = gameNaam.replaceAll(":", " -");
            String gameSearch = GameSearcher.gameSearch(gameNaam, gameMap);
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
        if (gameMap.containsKey(gameNaam)) {
            System.out.println("Voer de korting in als percentage (zonder het % teken):");
            int korting = scanner.nextInt();
            scanner.nextLine();
            if (korting < 0 || korting > 100) {
                System.out.println("Korting moet tussen 0 en 100 liggen.");
                return;
            }
            Game geselecteerdeGame = gameMap.get(gameNaam);
            geselecteerdeGame.korting = korting; // Pas korting toe
            geselecteerdeGame.onSale = korting > 0; // Update onSale status
            jsonHandler.updateJson(gameMap);
//            .put("sale", korting);

            System.out.println("Korting van " + korting + "% is toegepast op " + gameNaam);
        } else {
            System.out.println("Game niet gevonden. Zorg ervoor dat u de exacte naam invoert.");
        }
        System.out.println();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.flush();
        Main.mainMenu();
    }
}
