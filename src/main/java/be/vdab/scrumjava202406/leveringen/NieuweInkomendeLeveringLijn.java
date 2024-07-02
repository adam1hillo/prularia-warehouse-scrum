package be.vdab.scrumjava202406.leveringen;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record NieuweInkomendeLeveringLijn(@Positive long inkomendeLeveringsId, @Positive long artikelId, @PositiveOrZero int aantalGoedgekeurd, @PositiveOrZero int aantalTeruggestuurd, @Positive long magazijnPlaatsId) {
}
