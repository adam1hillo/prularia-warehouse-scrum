package be.vdab.scrumproject.bestellingen;

public class MagazijnIdDataHolder {
    private long magazijnPlaatsId;

    public MagazijnIdDataHolder(long magazijnPlaatsId) {
        this.magazijnPlaatsId = magazijnPlaatsId;
    }

    public long getMagazijnPlaatsId() {
        return magazijnPlaatsId;
    }

    public void setMagazijnPlaatsId(long magazijnPlaatsId) {
        this.magazijnPlaatsId = magazijnPlaatsId;
    }
}
