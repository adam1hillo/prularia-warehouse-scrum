package be.vdab.scrumjava202406.bestellingen;

public class ArtikelPlaatsRequest {
    private long artikelId;
    private int aantal;

    public ArtikelPlaatsRequest(long artikelid, int aantal) {
        this.artikelId = artikelid;
        this.aantal = aantal;
    }

    public long getArtikelId() {
        return artikelId;
    }

    public void setArtikelId(long artikelId) {
        this.artikelId = artikelId;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }

    @Override
    public String toString() {
        return "ArtikelPlaatsRequest{" +
                "artikelid=" + artikelId +
                ", aantal=" + aantal +
                '}';
    }
}
