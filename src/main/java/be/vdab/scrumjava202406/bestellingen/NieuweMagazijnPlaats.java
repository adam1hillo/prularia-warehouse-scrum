package be.vdab.scrumjava202406.bestellingen;

public class NieuweMagazijnPlaats {
    private final long magazijnPlaatsId;
    private final char rij;
    private final int rek;
    private final int aantal;

    public NieuweMagazijnPlaats(long magazijnPlaatsId, char rij, int rek, int aantal) {
        this.magazijnPlaatsId = magazijnPlaatsId;
        this.rij = rij;
        this.rek = rek;
        this.aantal = aantal;
    }

    public long getMagazijnPlaatsId() {
        return magazijnPlaatsId;
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

    @Override
    public String toString() {
        return "MagazijnPlaats{" +
                "magazijnPlaatsId=" + magazijnPlaatsId +
                ", rij=" + rij +
                ", rek=" + rek +
                ", aantal=" + aantal +
                '}';
    }
}
