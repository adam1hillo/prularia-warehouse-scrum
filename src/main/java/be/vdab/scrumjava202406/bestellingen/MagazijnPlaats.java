package be.vdab.scrumjava202406.bestellingen;

public class MagazijnPlaats {
    private final char rij;
    private final int rek;
    private final int aantal;

    public MagazijnPlaats(char rij, int rek, int aantal) {

        this.rij = rij;
        this.rek = rek;
        this.aantal = aantal;
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
