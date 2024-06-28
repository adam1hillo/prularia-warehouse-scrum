package be.vdab.scrumjava202406.bestellingen;

import jakarta.validation.constraints.PositiveOrZero;

public record NieuweVoorraad(@PositiveOrZero int voorraad) {
}
