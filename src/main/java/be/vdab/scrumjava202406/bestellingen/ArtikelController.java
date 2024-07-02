package be.vdab.scrumjava202406.bestellingen;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("artikelen")
public class ArtikelController {
    private final ArtikelService artikelService;
    private static MagazijnPlaatsService magazijnPlaatsService = null;

    public ArtikelController(ArtikelService artikelService,MagazijnPlaatsService magazijnPlaatsService) {
        this.artikelService = artikelService;
        this.magazijnPlaatsService = magazijnPlaatsService;
    }

    private record ArtikelMetPlaatsenDTO(long artikelId, String naam, BigDecimal prijs, long gewichtInGram, int voorraad, int maximumVoorraad, int maxAantalInMagazijnPlaats, List<MagazijnPlaats> magazijnPlaatsen) {
        ArtikelMetPlaatsenDTO(Artikel artikel){
            this(artikel.getArtikelId(), artikel.getNaam(), artikel.getPrijs(), artikel.getGewichtInGram(),
                    artikel.getVoorraad(), artikel.getMaximumVoorraad(), artikel.getMaxAantalInMagazijnPlaats(),  magazijnPlaatsService.findAllPlaatsById(artikel.getArtikelId())
                    );
        }
    }
    @GetMapping("{id}")
    ArtikelMetPlaatsenDTO findById(@PathVariable long id) {
        return artikelService.findById(id)
                .map(ArtikelMetPlaatsenDTO::new)
                .orElseThrow(() -> new ArtikelNietGevondenException(id));
    }
    @PatchMapping("updateVoorraad/{artikelId}/aantal")
    void updateTotaleVoorraad(@PathVariable long artikelId, @RequestBody @Valid AanpassingVoorraadMetAantal aantal) {
        artikelService.updateTotaleVoorraad(artikelId, aantal);
    }
    @PatchMapping("updateVoorraad/plaats")
    void updateArtikelVoorraadPerPlaats(@RequestBody @Valid List<RijRekNieuweAantal> rijRekNieuweAantalLijst) {
        artikelService.updateArtikelVoorraadPerPlaats(rijRekNieuweAantalLijst);
    }
}
