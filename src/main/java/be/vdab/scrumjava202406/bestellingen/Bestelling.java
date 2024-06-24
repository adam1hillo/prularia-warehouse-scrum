package be.vdab.scrumjava202406.bestellingen;

public class Bestelling {
    private long bestelId;
    private int rek;
    private char rij;
    private String naam;
    private int aantal;
    private long artikelId;

    public Bestelling(long bestelId, int rek, char rij, String naam, int aantal, long artikelId) {
        this.bestelId = bestelId;
        this.rek = rek;
        this.rij = rij;
        this.naam = naam;
        this.aantal = aantal;
        this.artikelId = artikelId;
    }

    public long getBestelId() {
        return bestelId;
    }

    public int getRek() {
        return rek;
    }

    public char getRij() {
        return rij;
    }

    public String getNaam() {
        return naam;
    }

    public int getAantal() {
        return aantal;
    }

}
