package be.vdab.scrumjava202406.bestellingen;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bestelling")
class ArtikelController {
    private final ArtikelService artikelService;

    public ArtikelController(ArtikelService artikelService) {
        this.artikelService = artikelService;
    }

    @PatchMapping("updateVoorraad/{artikelId}/voorraad")
    void updateTotaleVoorraad(@PathVariable long artikelId,
                              @RequestBody @Valid NieuweVoorraad nieuweVoorraad) {
        artikelService.updateTotaleVoorraad(artikelId, nieuweVoorraad);
    }
}

