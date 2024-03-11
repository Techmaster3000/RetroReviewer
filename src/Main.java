import java.util.Scanner;

class Main {

    public static void SpaceInvader() {
        System.out.println("                    ############");
        System.out.println("                ####################");
        System.out.println("              ########################");
        System.out.println("            ####  ####  ####  ####  ####");
        System.out.println("          ################################");
        System.out.println("              ######    ####    ######");
        System.out.println("                ##                ##");
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        SpaceInvader();
        System.out.println("This is the start of RetroReviewer!");

        System.out.println("1. Geef review over game");
        System.out.println("2. Exit");

        int keuze = scanner.nextInt();
        scanner.nextLine();

        switch (keuze) {

            case 1:
                reviewKlant();
                break;
            case 2:
                System.out.println("Tot ziens");
                break;
            default:
                System.out.println("Ongeldige keuze");
                break;

        }
    }

    private static void reviewKlant() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Geef de naam van de game");
        String gameNaam = scanner.nextLine();

        System.out.println("Beoordeel de gameplay van 1 tot 10: ");
        int gameplay = scanner.nextInt();

        System.out.println("Beoordeel de graphics van 1 tot 10: ");
        int graphics = scanner.nextInt();

        System.out.println("Beoordeel de verhaallijn van 1 tot 10: ");
        int verhaallijn = scanner.nextInt();

        System.out.println("Geef een toelichting op de review: ");
        String toelichting = scanner.nextLine();

        double totaalScore = ((gameplay + graphics + verhaallijn) / 3);

        System.out.println("De review is aangemaakt van de game: " + gameNaam);
        System.out.println("Uw totaalscore is: " + totaalScore);


    }
}