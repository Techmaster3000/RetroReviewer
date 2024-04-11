import java.io.*;
import java.util.Scanner;

public class Enquete {
    public static void Beginscherm() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Wilt u een enquête invullen? (Y/n)");
        Boolean choice = yesOrNo(scanner.nextLine());
        if (choice) {
            VragenInlezen();
            Eindscherm();
        } else {
            System.out.println("U kunt door naar het volgende scherm");
        }
    }

    public static void VragenInlezen() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("vragenlijst.csv"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("antwoorden.CSV", true));

            leesMultipleChoiceVragen(reader, writer);
            leesOpenVragen(reader, writer);
            leesConditioneleVragen(reader, writer);

            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void leesMultipleChoiceVragen(BufferedReader reader, BufferedWriter writer) throws IOException {
        String line = reader.readLine();
        while (line != null && !line.equals("#")) {
            System.out.println(line);
            line = reader.readLine(); // Lees volgende regel voor de vraag
            if (line != null && !line.equals("#")) {
                System.out.println("Kies een optie:");
                System.out.println(line);

                Scanner scanner = new Scanner(System.in);
                int antwoord = scanner.nextInt();
                writer.write(String.valueOf(antwoord));
                writer.newLine();
                line = reader.readLine();
                line = reader.readLine();
            }
        }
    }

    private static void leesOpenVragen(BufferedReader reader, BufferedWriter writer) throws IOException {
        String line = reader.readLine();
        while (line != null && !line.equals("#")) {
            System.out.println(line);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Geef uw antwoord: ");
            String antwoordOpenVraag = scanner.nextLine();
            writer.write(antwoordOpenVraag);
            writer.newLine();
            line = reader.readLine();
        }
    }

    private static void leesConditioneleVragen(BufferedReader reader, BufferedWriter writer) throws IOException {
        String line = reader.readLine();
        while (line != null && !line.equals("#")) {
            System.out.println(line);
            Scanner scanner = new Scanner(System.in);

            String antwoordCVraag = scanner.nextLine();
            boolean validInput = false;

            while (!validInput) {
                switch (antwoordCVraag.toLowerCase()) {
                    case "ja":
                        validInput = true;
                        writer.write(antwoordCVraag);
                        writer.newLine();

                        line = reader.readLine();
                        break;
                    case "nee":
                        validInput = true;
                        System.out.println(reader.readLine());

                        writer.write(antwoordCVraag);
                        writer.newLine();

                        String antwoordConditioneleVraag = scanner.nextLine();
                        writer.write(antwoordConditioneleVraag);
                        writer.newLine();

                        break;
                    default:
                        System.out.println("Ongeldige invoer. Voer 'Ja' of 'Nee' in.");
                        antwoordCVraag = scanner.nextLine();
                        break;
                }
            }
            line = reader.readLine();
        }
    }


    public static void Eindscherm() {
        System.out.println();
        System.out.println("Bedankt voor het invullen van de enquête!");
        System.out.println("   (^-^)   ");
        System.out.println("  (     )  ");
    }
    public static Boolean yesOrNo(String input) {
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
