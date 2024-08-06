package be.vdab.scrumproject.bestellingen;

public record ArtikelIdNaamRijRekAantalBesteld(long artikelId, char rij, int rek, String naam, int aantal) {
    public ArtikelIdNaamRijRekAantalBesteld(MagazijnplaatsAantalMetArtikelId magazijnplaatsAantalMetArtikelId, BestelLijnenArtikelNaam bestelLijnenArtikelNaam, int aantal) {
        this(bestelLijnenArtikelNaam.artikelId(), magazijnplaatsAantalMetArtikelId.rij(), magazijnplaatsAantalMetArtikelId.rek(), bestelLijnenArtikelNaam.naam(), aantal);
    }
}
