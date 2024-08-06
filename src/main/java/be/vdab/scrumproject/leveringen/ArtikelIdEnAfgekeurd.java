package be.vdab.scrumproject.leveringen;

public class ArtikelIdEnAfgekeurd {
    private long artikelId;
    private int afgekeurd;
    private int goedgekeurd;

    public ArtikelIdEnAfgekeurd(long artikelId, int afgekeurd, int goedgekeurd) {
        this.artikelId = artikelId;
        this.afgekeurd = afgekeurd;
        this.goedgekeurd = goedgekeurd;
    }

    public long getArtikelId() {
        return artikelId;
    }

    public void setArtikelId(long artikelId) {
        this.artikelId = artikelId;
    }

    public int getAfgekeurd() {
        return afgekeurd;
    }

    public void setAfgekeurd(int afgekeurd) {
        this.afgekeurd = afgekeurd;
    }

    public int getGoedgekeurd() {
        return goedgekeurd;
    }

    public void setGoedgekeurd(int goedgekeurd) {
        this.goedgekeurd = goedgekeurd;
    }

    @Override
    public String toString() {
        return "ArtikelIdEnAfgekeurd{" +
                "artikelId=" + artikelId +
                ", afgekeurd=" + afgekeurd +
                ", goedgekeurd=" + goedgekeurd +
                '}';
    }
}
