package be.vdab.scrumjava202406.bestellingen;

public record BestelIdLijstArtikelOmTePick(long artikelId, char rij, int rek, String naam, int aantal) {
    public BestelIdLijstArtikelOmTePick(MagazijnplaatAantalMetArtikelId magazijnplaatAantalMetArtikelId, BestellingLijnenArtikelNaam bestellingLijnenArtikelNaam, int aantal) {
        this(bestellingLijnenArtikelNaam.artikelId(), magazijnplaatAantalMetArtikelId.rij(), magazijnplaatAantalMetArtikelId.rek(), bestellingLijnenArtikelNaam.naam(), aantal);
    }
}