public class Game {
    public Boolean onSale;
    private String name;
    private String genre;
    private String platform;
    private double price;
    public int korting;

    public Game(String name, String genre, String platform, double price, int korting) {
        this.name = name;
        this.genre = genre;
        this.platform = platform;
        this.price = price;
        this.korting = korting;
        this.onSale = korting > 0;
    }

    public String getName() {
        return name;
    }
    public String getGenre() {
        return genre;
    }

    public String getPlatform() {
        return platform;
    }

    public double getBasePrice() {
        return price;
    }

    // rond de nieuwe prijs af op 2 decimalen
    public double getNewPrice() {
        return Math.round((price - (price * korting / 100)) * 100.0) / 100.0;
    }

}
