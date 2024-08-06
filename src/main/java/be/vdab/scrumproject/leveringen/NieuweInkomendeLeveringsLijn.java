package be.vdab.scrumproject.leveringen;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record NieuweInkomendeLeveringsLijn(
        @Positive
        long inkomendeLeveringsId,
        @Positive
        int eanLastFive,
        @PositiveOrZero
        int aantalGoedgekeurd,
        @PositiveOrZero
        int aantalTeruggestuurd,
        @Positive
        long magazijnPlaatsId) {
}
