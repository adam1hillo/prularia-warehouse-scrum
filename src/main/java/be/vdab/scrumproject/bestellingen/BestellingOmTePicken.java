package be.vdab.scrumproject.bestellingen;

import java.util.List;

public record BestellingOmTePicken(long bestelId, List<ArtikelIdNaamRijRekAantalBesteld> artikelLijn) {
}
