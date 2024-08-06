package be.vdab.scrumproject.bestellingen;

import java.math.BigDecimal;

record BestellingOverzichtTVScherm(long id, int aantalArtikelen, BigDecimal totaleGewicht) {
}