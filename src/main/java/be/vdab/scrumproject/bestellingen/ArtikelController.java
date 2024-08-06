package be.vdab.scrumproject.bestellingen;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("artikelen")
public class ArtikelController {
    private final ArtikelService artikelService;
    private static MagazijnPlaatsService magazijnPlaatsService = null;


    public ArtikelController(ArtikelService artikelService, MagazijnPlaatsService magazijnPlaatsService) {
        this.artikelService = artikelService;
        this.magazijnPlaatsService = magazijnPlaatsService;
    }

    private record ArtikelMetPlaatsenDTO(long artikelId, String naam, BigDecimal prijs, long gewichtInGram,
                                         int voorraad, int maximumVoorraad, int maxAantalInMagazijnPlaats,
                                         List<MagazijnPlaats> magazijnPlaatsen) {
        ArtikelMetPlaatsenDTO(Artikel artikel) {
            this(artikel.getArtikelId(), artikel.getNaam(), artikel.getPrijs(), artikel.getGewichtInGram(),
                    artikel.getVoorraad(), artikel.getMaximumVoorraad(), artikel.getMaxAantalInMagazijnPlaats(), magazijnPlaatsService.findAllPlaatsById(artikel.getArtikelId())
            );
        }
    }

    private record ArtikelNaamEan(String naam, long artikelId, String ean) {
        ArtikelNaamEan(Artikel artikel) {
            this(artikel.getNaam(), artikel.getArtikelId(), artikel.getEan());
        }
    }


    @GetMapping("{id}")
    ArtikelMetPlaatsenDTO findById(@PathVariable long id) {
        return artikelService.findById(id)
                .map(ArtikelMetPlaatsenDTO::new)
                .orElseThrow(() -> new ArtikelNietGevondenException(id));
    }

    @GetMapping("metEanLastFive/{eanLastFive}")
    ArtikelNaamEan findArtikelNaamByEanLastFive(@PathVariable String eanLastFive) {
        return artikelService.findByEanLastFive(eanLastFive)
                .map(ArtikelNaamEan::new)
                .orElseThrow(() -> new ArtikelNietGevondenException(eanLastFive));
    }


    @PostMapping("findAllPlaceForDelivery")
    public List<MagazijnPlaats> findAllPlaceForDelivery(@RequestBody List<ArtikelPlaatsRequest> artikelPlaatsRequests) {
        return artikelService.findAllPlaceForDelivery(artikelPlaatsRequests);
    }


    @PatchMapping("updateVoorraad/{artikelId}/aantal")
    void updateTotaleVoorraad(@PathVariable long artikelId, @RequestBody @Valid AanpassingVoorraadMetAantal aantal) {
        artikelService.updateTotaleVoorraad(artikelId, aantal);
    }

    @PatchMapping("updateVoorraad/plaats")
    void updateArtikelVoorraadPerPlaats(@RequestBody @Valid List<RijRekNieuweAantal> rijRekNieuweAantalLijst) {
        artikelService.updateArtikelVoorraadPerPlaats(rijRekNieuweAantalLijst);
    }


    /*@GetMapping("checkFreeSpace/{id}")
    MagazijnPlaats checkFreeSpaceCanAddedToPlace(@PathVariable long id){
        return magazijnPlaatsService.checkFreeSpaceCanAddedToPlace(id)
                .orElseThrow(MagazijnPlaatsNietGevondenException::new);
    }

    @PatchMapping("updateAantal")
    void updateAantal(){
        magazijnPlaatsService.updateAantal(561, 5);
    }*/

   /* @GetMapping("findAvailablePlace/{id}/{aantal}")
    List<MagazijnPlaats> algemeelUpdate(@PathVariable long id, @PathVariable int aantal){
        return artikelService.updateAantalAlgemeel(id, aantal);
    }*/

    /*@GetMapping("{id}")
    Artikel findById(@PathVariable long id) {
        return artikelService.findById(id)
                .orElseThrow(() -> new ArtikelNietGevondenException(id));
    }*/

    /*@PatchMapping("updateAllPlaceForDelivery")
    public List<MagazijnPlaats> updateAllPlaceForDelivery(@RequestBody List<MagazijnPlaats> magazijnPlaatsen){
        return artikelService.updateAllPlaceForDelivery(magazijnPlaatsen);
    }*/
}
