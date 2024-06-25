package be.vdab.scrumjava202406.bestellingen;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.bind.annotation.*;

@RestController
class BestelController {
    private final BestelService bestelService;

    public BestelController(BestelService bestelService) {
        this.bestelService = bestelService;
    }

    @PatchMapping("bestelling/updateVoorraad/{artikelId}/{voorraad}")
    void updateTotaleVoorraad(@PathVariable Integer artikelId,
                   @PathVariable Integer voorraad) {
        bestelService.updateTotaleVoorraad(artikelId, voorraad);
    }
}

