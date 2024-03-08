import java.util.Scanner;
import java.util.Random;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static void SpaceInvader() {
        System.out.println("          ############");
        System.out.println("     ####################");
        System.out.println("    ########################");
        System.out.println("  ####  ####  ####  ####  ####");
        System.out.println("################################");
        System.out.println("    ######    ####    ######");
        System.out.println("      ##                ##\n");



    }
    public static void GenerateExitText() {
        switch(new Random().nextInt(4) + 1) {
            case 1:
                System.out.println("Game Over!");
                break;
            case 2:
                System.out.println("\"You can now play as Luigi!\"");
                break;
            case 3:
                System.out.println("\"Snake? Snaaaaaaake!\"");
                break;
            case 4:
                System.out.println("\"Sayonara, Shadow the Hedgehog!\"");
                break;
            default:
                System.out.println("Bye Bye!");

        }
    }
    public static void MainMenu() {
        System.out.println("RetroReviewer");
        System.out.println("1. View Game Review");
        System.out.println("2. Create Game Review");
        System.out.println("3. Exit RetroReviewer");
        int input = scanner.nextInt();
        switch (input) {
            case 1:
                System.out.println("View Game Review");
                break;
            case 2:
                System.out.println("Create Game Review");
                break;
            case 3:
                //exit the program
                GenerateExitText();
                System.exit(0);

                break;
            default:
                System.out.println("Invalid input");
                System.out.flush();
                MainMenu();

        }
    }
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        SpaceInvader();
        MainMenu();


        SpaceInvader();
    }
}