package be.vdab.scrumjava202406.bestellingen;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bestelling")
class ArtikelController {
    private final ArtikelService bestelService;

    public ArtikelController(ArtikelService bestelService) {
        this.bestelService = bestelService;
    }

    @PatchMapping("updateVoorraad/{artikelId}/voorraad")
    void updateTotaleVoorraad(@PathVariable long artikelId,
                              @RequestBody @Valid NieuweVoorraad nieuweVoorraad) {
        bestelService.updateTotaleVoorraad(artikelId, nieuweVoorraad);
    }
    @PatchMapping("updateStatusOnderweg/{bestelId}")
    void updateBestellingStatusToOnderweg(@PathVariable int bestelId){
        bestelService.updateBestellingStatusToOnderweg(bestelId);
    }
}

