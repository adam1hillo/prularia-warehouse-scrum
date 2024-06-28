package be.vdab.scrumjava202406.bestellingen;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bestelling")
class ArtikelController {
    private final ArtikelService bestelService;

    public ArtikelController(ArtikelService bestelService) {
        this.bestelService = bestelService;
    }

    @PatchMapping("updateVoorraad/{artikelId}/{voorraad}")
    void updateTotaleVoorraad(@PathVariable long artikelId,
                   @PathVariable Integer voorraad) {
        bestelService.updateTotaleVoorraad(artikelId, voorraad);
    }
    @PatchMapping("updateStatusOnderweg/{bestelId}")
    void updateBestellingStatusToOnderweg(@PathVariable int bestelId){
        bestelService.updateBestellingStatusToOnderweg(bestelId);
    }
}

