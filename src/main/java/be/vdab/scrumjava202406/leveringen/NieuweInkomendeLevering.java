package be.vdab.scrumjava202406.leveringen;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

record NieuweInkomendeLevering(@Positive long leveranciersId, @NotBlank String leveringsbonNummer, @NotBlank String  leveringsbondatum, @NotBlank String leverDatum) {
}
