import java.util.ArrayList;
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

        System.out.println("1. Zie ranglijst");
        System.out.println("2. Geef review over game");
        System.out.println("3. Ga naar uitverkoop");
        System.out.println("4. Exit");

        int keuze = scanner.nextInt();
        scanner.nextLine();

        switch (keuze) {

            case 1:
                rangLijst();
                break;

            case 2:
                reviewKlant();
                break;

            case 3:
                uitverkoop();
                break;
            case 4:
                System.out.println("Tot ziens");
                break;
            default:
                System.out.println("Ongeldige keuze");
                break;

        }
    }

    private static void uitverkoop() {
        System.out.println("Deze games zijn in de uitverkoop");


    }

    private static void rangLijst() {
        InlezenBestand inlezenBestand = new InlezenBestand();
        inlezenBestand.lezenReview();
        ArrayList<String[]> reviews = inlezenBestand.lezenReview();
        for (String[] s : reviews) {
            System.out.printf("%d %s%n", Integer.valueOf(s[0]), s[1], s[2], s[3]);
        }
//        %s = String
//        %d = Decimaal
//        %.2f = Kommagetal

// reverseOrder() methode

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

        //verwijderd onzichtbare scanner line
        scanner.nextLine();

        System.out.println("Geef een toelichting op de review: ");
        String toelichting = scanner.nextLine();

        double totaalScore = ((gameplay + graphics + verhaallijn) / 3);

        System.out.println("De review is aangemaakt van de game: " + gameNaam);
        System.out.println("Uw totaalscore is: " + totaalScore);

        String uitkomst = "";

        InlezenBestand lezenbestand = new InlezenBestand();
        int id = lezenbestand.lezenReview().size() + 1;
        uitkomst = "\n" + id + " " + gameNaam + " " +  gameplay + " " +  graphics + " " + verhaallijn  + " " + toelichting;
        lezenbestand.schrijvenReview(uitkomst);
    }
}