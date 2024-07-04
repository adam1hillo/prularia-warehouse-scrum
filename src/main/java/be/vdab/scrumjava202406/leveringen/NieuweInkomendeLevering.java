package be.vdab.scrumjava202406.leveringen;

import be.vdab.scrumjava202406.bestellingen.MagazijnPlaats;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.List;

record NieuweInkomendeLevering(@Positive long leveranciersId,
                               @NotBlank String leveringsbonNummer,
                               @NotBlank String  leveringsbondatum,
                               @NotBlank String leverDatum,
                               List<ArtikelIdEnAfgekeurd> artikelIdEnAfgekeurdList,
                               List<MagazijnPlaats> magazijnPlaatsList
                               ) {
}
