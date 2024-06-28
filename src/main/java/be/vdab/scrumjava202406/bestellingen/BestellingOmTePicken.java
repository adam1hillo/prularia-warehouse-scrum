package be.vdab.scrumjava202406.bestellingen;

import java.util.List;

public record BestellingOmTePicken(long bestelId, List<ArtikelIdNaamRijRekAantalBesteld> artikelLijn) {
}
