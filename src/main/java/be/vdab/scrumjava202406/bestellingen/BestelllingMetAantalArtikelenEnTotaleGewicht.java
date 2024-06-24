package be.vdab.scrumjava202406.bestellingen;

import java.math.BigDecimal;

record BestelllingMetAantalArtikelenEnTotaleGewicht(long id, int aantalArtikelen, BigDecimal totaleGewicht) {
}
