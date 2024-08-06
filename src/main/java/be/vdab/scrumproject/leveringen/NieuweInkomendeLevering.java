package be.vdab.scrumproject.leveringen;

import be.vdab.scrumproject.bestellingen.MagazijnPlaats;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.List;

record NieuweInkomendeLevering(@Positive long leveranciersId,
                               @NotBlank String leveringsbonNummer,
                               @NotBlank String  leveringsbondatum,
                               @NotBlank String leverDatum,
                               List<ArtikelIdEnAfgekeurd> artikelIdEnAfgekeurdList,
                               List<MagazijnPlaats> magazijnPlaatsList
                               ) {
}
