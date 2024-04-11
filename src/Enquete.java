import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Enquete {
    static Scanner scanner = new Scanner(System.in);

    public static void Beginscherm() {
        System.out.println();
        System.out.println("Wilt u een enquête invullen? (Y/n)");
        Boolean choice = yesOrNo(scanner.nextLine());
        if (choice) {
            vragenInlezen();
            Eindscherm();
        } else {
            System.out.println("U kunt door naar het volgende scherm");
        }
    }
    private static String formulateQuestion(String question) {
        question = question.replaceAll("€", "");
        question = question.replaceAll("<", "");
        question = question.replaceAll("]", "");
        return question;

    }

    public static void vragenInlezen() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("vragenlijst.csv"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("antwoorden.CSV", true));
            String line = reader.readLine();
            int prevAnswer = -1;

            while (!line.equals("*END*")) {
                //run LeesConditioneleVragen if the 2nd character of the line is a €
                if (!line.isEmpty()) {

                    if (!line.equals("*") && line.substring(1, 2).equals("€")) {
                        leesConditioneleVragen(reader, writer, prevAnswer, line);
                        line = line.substring(0, 2) + line.substring(3);
                    }
                    switch (line.charAt(0)) {
                        case ']':
                            prevAnswer = leesMultipleChoiceVragen(reader, writer, line);
                            break;
                        case '<':
                            leesOpenVragen(writer, line);
                            break;
                        default:
                            break;
                    }
                }
                    line = reader.readLine();

            }

            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int leesMultipleChoiceVragen(BufferedReader reader, BufferedWriter writer, String line) throws IOException {
        //print the question without the first characters
        System.out.println(formulateQuestion(line));
        if (line.endsWith("(y/n)")) {
            int answer = YesNoQuestion();
            if (answer == 1) {
                writer.write(line + " " + "Ja\n");
            } else {
                writer.write(line + " " + "Nee\n");
            }
            return answer;
        }
        int index = 1;
        ArrayList<String> choices = new ArrayList<>();
        String toWrite = formulateQuestion(line);

        line = reader.readLine();
        //parse int from the first character of the line

        while (!line.equals("*") && Integer.parseInt(line.substring(0, 1)) == index) {
            choices.add(line.substring(3));
            index++;
            line = reader.readLine();
        }
        for (String choice : choices) {
            System.out.print(choices.indexOf(choice) + 1 + ". " + choice + " ");
            index++;
        }
        System.out.println("\nKies een optie:");
        //user input must be between 1 and the amount of choices. handle exceptions
        int antwoord = 0;
        while (true) {
            try {
                antwoord = scanner.nextInt();
                if (antwoord < 1 || antwoord > choices.size()) {
                    throw new InputMismatchException();
                }
                else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ongeldige invoer. Voer een getal in tussen 1 en " + choices.size());
                scanner.nextLine();
            }
        }
        writer.write(toWrite + " " +choices.get(antwoord - 1));
        writer.newLine();
        scanner.nextLine();
        return index - 1;
    }

    private static int YesNoQuestion() {
        Boolean answer = yesOrNo(scanner.nextLine());
        if (answer) {
            return 1;
        } else {
            return 0;
        }

    }

    private static void leesOpenVragen(BufferedWriter writer, String line) throws IOException {
        System.out.println(formulateQuestion(line));

        System.out.print("Geef uw antwoord: ");
        String antwoordOpenVraag = scanner.nextLine();
        while (antwoordOpenVraag.isEmpty()) {
            System.out.println("Ongeldige invoer. Voer een antwoord in.");
            antwoordOpenVraag = scanner.nextLine();
        }
        writer.write(antwoordOpenVraag);
        writer.newLine();
    }

    private static void leesConditioneleVragen(BufferedReader reader, BufferedWriter writer, int prevAnswer, String line) {
        int expectedIndex = Integer.parseInt(line.substring(2, 3));
        if (prevAnswer != expectedIndex) {
            try {
                reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                input = scanner.nextLine();
            }
        }
    }
    //main
    public static void main(String[] args) {
        Beginscherm();
    }

}
