import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InlezenBestand {

    public ArrayList<String[]> lezenReview(){
        String[] delen = new String[7];
        ArrayList<String[]> reviews = new ArrayList<>();
        try {
            Path path = Paths.get("/Users/safaeelhammouti/IdeaProjects/RetroReviewer/src/InlezenReview");
            List<String> lines = Files.readAllLines(path);

            for(String line : lines) {
                delen = line.split(" ");
                reviews.add(delen);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public void schrijvenReview(String line){
        try {
            BufferedWriter textBestand = new BufferedWriter(new FileWriter("/Users/safaeelhammouti/IdeaProjects/RetroReviewer/src/InlezenReview", true));
            textBestand.append(line);
            textBestand.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
