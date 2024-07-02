package be.vdab.scrumjava202406.bestellingen;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bestelling")
class ArtikelController {
    private final ArtikelService artikelService;

    public ArtikelController(ArtikelService artikelService) {
        this.artikelService = artikelService;
    }

    @PatchMapping("updateVoorraad/{artikelId}/voorraad")
    void updateTotaleVoorraad(@PathVariable long artikelId, @RequestBody @Valid NieuweVoorraad nieuweVoorraad) {
        artikelService.updateTotaleVoorraad(artikelId, nieuweVoorraad);
    }

    @PatchMapping("updateVoorraad/plaats")
    void updateArtikelVoorraadPerPlaats(@RequestBody @Valid List<RijRekNieuweAantal> rijRekNieuweAantalLijst) {
        artikelService.updateArtikelVoorraadPerPlaats(rijRekNieuweAantalLijst);
    }
}

