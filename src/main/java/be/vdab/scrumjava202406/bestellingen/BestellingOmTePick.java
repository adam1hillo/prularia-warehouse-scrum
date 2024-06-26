package be.vdab.scrumjava202406.bestellingen;

import java.util.List;

public record BestellingOmTePick(long bestelId, List<BestelIdLijstArtikelOmTePick> artikelLijn) {
}
