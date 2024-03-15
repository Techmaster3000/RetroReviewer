import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Write {

    private final String fileName;

    public Write(String fileName) {
        this.fileName = fileName;
    }

    public void writeAllLines(List<String> answers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileName))) {
            for (String answer : answers) {
                writer.write(answer);
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
