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

    @PatchMapping("bestelling/updateVoorraad/{artikelId}")
    void updateTotaleVoorraad(@PathVariable Integer artikelId,
                   @RequestBody @PositiveOrZero @NotBlank Integer voorraad) {
        bestelService.updateTotaleVoorraad(artikelId, voorraad);
    }
    @PatchMapping("bestelling/updateStatusOnderweg/{bestelId}")
    void updateBestellingStatusToOnderweg(@PathVariable int bestelId){
        bestelService.updateBestellingStatusToOnderweg(bestelId);
    }
}

