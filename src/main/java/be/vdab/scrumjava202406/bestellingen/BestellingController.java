package be.vdab.scrumjava202406.bestellingen;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("bestellingen")
public class BestellingController {
    private final BestellingService bestellingService;

    public BestellingController(BestellingService bestellingService) {
        this.bestellingService = bestellingService;
    }
    public record rekEnPlaatsAantalEnArtikelnaam(char rij,int rek,String artikelnaam,int aantal){
        rekEnPlaatsAantalEnArtikelnaam(Bestelling bestelling){
            this(bestelling.getRij(),bestelling.getRek(),bestelling.getNaam(), bestelling.getAantal());
        }
    }
    @GetMapping()
    Stream<rekEnPlaatsAantalEnArtikelnaam> findOudsteBestelling(){
         return bestellingService.findOudsteBestelling()
                 .stream()
                 .map(rekEnPlaatsAantalEnArtikelnaam::new);
    }
}
