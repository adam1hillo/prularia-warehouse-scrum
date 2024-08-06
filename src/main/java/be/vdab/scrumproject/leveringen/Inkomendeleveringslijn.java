package be.vdab.scrumproject.leveringen;

public class Inkomendeleveringslijn {
    private final long inkomendeLeveringsId;
    private final long artikelId;
    private final long aantalGoedgekeurd;
    private final long aantalTeruggestuurd;
    private final long magazijnPlaatsId;

    public Inkomendeleveringslijn(long inkomendeLeveringsId, long artikelId, long aantalGoedgekeurd, long aantalTeruggestuurd, long magazijnPlaatsId) {
        this.inkomendeLeveringsId = inkomendeLeveringsId;
        this.artikelId = artikelId;
        this.aantalGoedgekeurd = aantalGoedgekeurd;
        this.aantalTeruggestuurd = aantalTeruggestuurd;
        this.magazijnPlaatsId = magazijnPlaatsId;
    }

    public long getInkomendeLeveringsId() {
        return inkomendeLeveringsId;
    }

    public long getArtikelId() {
        return artikelId;
    }

    public long getAantalGoedgekeurd() {
        return aantalGoedgekeurd;
    }

    public long getAantalTeruggestuurd() {
        return aantalTeruggestuurd;
    }

    public long getMagazijnPlaatsId() {
        return magazijnPlaatsId;
    }
}
