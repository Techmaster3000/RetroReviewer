import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class QuestionReader {

    private final String fileName;

    public QuestionReader(String fileName) {
        this.fileName = fileName;
    }

    public List<String> readAllLines() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.fileName))) {
            return reader.lines().toList();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }
}