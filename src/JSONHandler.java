import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class JSONHandler {
    public static String getJSON(String filename) {
        String jsonText = "";
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(filename));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonText += line + "\n";
            }

            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonText;
    }
    public static void updateJson(HashMap<String, Game> gameMap) {
        JSONArray gamesList = new JSONArray();

        for (Map.Entry<String, Game> entry : gameMap.entrySet()) {
            Game game = entry.getValue();
            // Voor elke game, creëer een nieuw JSONObject en vul het met de game details
            JSONObject gameDetails = new JSONObject();
            gameDetails.put("name", game.getName());
            gameDetails.put("genre", game.getGenre());
            gameDetails.put("platform", game.getPlatform());
            gameDetails.put("price", String.format("%.2f", game.getBasePrice()));
            gameDetails.put("sale", Integer.toString(game.korting));

            gamesList.add(gameDetails);
        }

        // Creëer een hoofd JSONObject dat de games lijst bevat
        JSONObject root = new JSONObject();
        root.put("games", gamesList);

        try (FileWriter file = new FileWriter("GamesDB.json")) {
            String content = root.toJSONString();
            file.write(content);
        } catch (IOException e) {
            System.out.println("Er is een fout opgetreden bij het schrijven naar het JSON-bestand.");
            e.printStackTrace();
        }
    }

    //create a method that reads a JSON file and prints the contents to the console
    public static HashMap<String, Game> readJSON(String filename, HashMap<String, Game> gameMap) {
        JSONParser parser = new JSONParser();
        JSONArray games = null;

        try {
            Object obj = parser.parse(new FileReader(filename));
            if (obj instanceof JSONObject jsonObject) {
                games = (JSONArray) jsonObject.get("games");
                // process games array
            } else if (obj instanceof JSONArray) {
                games = (JSONArray) obj;
                // process games array
            } else {
                throw new Exception("Invalid JSON file");


            }
            gameMap = new HashMap<>();
            for (Object game : games) {
                JSONObject gameObj = (JSONObject) game;
                String name = (String) gameObj.get("name");
                String genre = (String) gameObj.get("genre");
                String platform = (String) gameObj.get("platform");
                double price = Double.parseDouble(((String) gameObj.get("price")).replace(",", "."));
                int korting = Integer.parseInt((String) gameObj.get("sale"));
                Game tempGame = new Game(name, genre, platform, price, korting);
                gameMap.put(name, tempGame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameMap;
    }
}