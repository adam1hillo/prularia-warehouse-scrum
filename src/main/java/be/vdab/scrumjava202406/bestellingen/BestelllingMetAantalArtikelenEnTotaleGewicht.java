package be.vdab.scrumjava202406.bestellingen;

import java.math.BigDecimal;

public record BestelllingMetAantalArtikelenEnTotaleGewicht(long id, int aantalArtikelen, BigDecimal totaleGewicht) {
}
