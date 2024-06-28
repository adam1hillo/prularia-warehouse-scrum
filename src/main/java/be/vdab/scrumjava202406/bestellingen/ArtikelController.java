package be.vdab.scrumjava202406.bestellingen;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bestelling")
class ArtikelController {
    private final ArtikelService artikelService;

    public ArtikelController(ArtikelService artikelService) {
        this.artikelService = artikelService;
    }

    @PatchMapping("updateVoorraad/{artikelId}/{voorraad}")
    void updateTotaleVoorraad(@PathVariable long artikelId,
                   @PathVariable Integer voorraad) {
        artikelService.updateTotaleVoorraad(artikelId, voorraad);
    }
}

