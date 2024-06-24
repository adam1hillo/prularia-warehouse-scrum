package be.vdab.scrumjava202406.bestellingen;

import java.math.BigDecimal;

public class Artikel {
    private final long artikelId;
    private final String naam;
    private final BigDecimal prijs;
    private final long gewichtInGram;
    private final int voorraad;
    private final int maximumVoorraad;
    private final int maxAantalInMagazijnPlaats;

    public Artikel(long artikelId, String naam, BigDecimal prijs, long gewichtInGram, int voorraad, int maximumVoorraad, int maxAantalInMagazijnPlaats) {
        this.artikelId = artikelId;
        this.naam = naam;
        this.prijs = prijs;
        this.gewichtInGram = gewichtInGram;
        this.voorraad = voorraad;
        this.maximumVoorraad = maximumVoorraad;
        this.maxAantalInMagazijnPlaats = maxAantalInMagazijnPlaats;
    }

    public long getArtikelId() {
        return artikelId;
    }

    public String getNaam() {
        return naam;
    }

    public BigDecimal getPrijs() {
        return prijs;
    }

    public long getGewichtInGram() {
        return gewichtInGram;
    }

    public int getVoorraad() {
        return voorraad;
    }

    public int getMaximumVoorraad() {
        return maximumVoorraad;
    }

    public int getMaxAantalInMagazijnPlaats() {
        return maxAantalInMagazijnPlaats;
    }
}
