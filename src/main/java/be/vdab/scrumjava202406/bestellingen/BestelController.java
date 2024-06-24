package be.vdab.scrumjava202406.bestellingen;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
class BestelController {
    private final BestelService bestelService;

    public BestelController(BestelService bestelService) {
        this.bestelService = bestelService;
    }

    @PostMapping("bestelling/updateVoorraad/{artikelId}")
    void updateTotaleVoorraad(@PathVariable Integer artikelId,
                   @RequestBody @PositiveOrZero Integer voorraad) {
        bestelService.updateTotaleVoorraad(artikelId, voorraad);
    }
}

