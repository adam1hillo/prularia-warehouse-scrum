package be.vdab.scrumjava202406.bestellingen;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

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

    /*@GetMapping("{id}")
    Artikel findById(@PathVariable long id) {
        return artikelService.findById(id)
                .orElseThrow(() -> new ArtikelNietGevondenException(id));
    }*/

    @GetMapping("{id}")
    ArtikelMetPlaatsenDTO findById(@PathVariable long id) {
        return artikelService.findById(id)
                .map(ArtikelMetPlaatsenDTO::new)
                .orElseThrow(() -> new ArtikelNietGevondenException(id));
    }
}