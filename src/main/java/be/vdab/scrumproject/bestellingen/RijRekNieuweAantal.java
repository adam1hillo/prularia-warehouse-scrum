package be.vdab.scrumproject.bestellingen;

import be.vdab.scrumproject.util.UppercaseChar;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@Valid
public record RijRekNieuweAantal(@UppercaseChar char rij, @Min(1) @Max(60) int rek, @Positive int aantal) {
}
