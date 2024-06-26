package be.vdab.scrumjava202406.bestellingen;

public class MagazijnPlaats {
    private final long magazijnPlaatsId;
    private final long artikelId;
    private final char rij;
    private final int rek;
    private final int aantal;

    public MagazijnPlaats(long magazijnPlaatsId,long artikelId,char rij, int rek, int aantal) {
        this.magazijnPlaatsId = magazijnPlaatsId;
        this.artikelId = artikelId;
        this.rij = rij;
        this.rek = rek;
        this.aantal = aantal;
    }

    public long getMagazijnPlaatsId() {
        return magazijnPlaatsId;
    }

    public long getArtikelId() {
        return artikelId;
    }

    public char getRij() {
        return rij;
    }

    public int getRek() {
        return rek;
    }

    public int getAantal() {
        return aantal;
    }
}
