package be.vdab.scrumjava202406.bestellingen;

import java.math.BigDecimal;

record BestellingOverzichtTVScherm(long id, int aantalArtikelen, BigDecimal totaleGewicht) {
}