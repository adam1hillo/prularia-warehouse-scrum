package be.vdab.scrumjava202406.bestellingen;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("artikelen")
public class bestelLijnenOverzichtDTOController {
    private final bestelLijnenOverzichtDTOService bestelLijnenOverzichtDTOService;

    public bestelLijnenOverzichtDTOController(bestelLijnenOverzichtDTOService bestelLijnenOverzichtDTOService) {
        this.bestelLijnenOverzichtDTOService = bestelLijnenOverzichtDTOService;
    }
    public record rekEnPlaatsAantalEnArtikelnaam(char rij,int rek,String artikelnaam,int aantal){
        rekEnPlaatsAantalEnArtikelnaam(bestelLijnenOverzichtDTO bestelLijnenOverzichtDTO){
            this(bestelLijnenOverzichtDTO.getRij(), bestelLijnenOverzichtDTO.getRek(), bestelLijnenOverzichtDTO.getNaam(), bestelLijnenOverzichtDTO.getAantal());
        }
    }
    @GetMapping("vanOudsteBestellingen")
    Stream<rekEnPlaatsAantalEnArtikelnaam> findOudsteBestelling(){
         return bestelLijnenOverzichtDTOService.findOudsteBestelling()
                 .stream()
                 .map(rekEnPlaatsAantalEnArtikelnaam::new);
    }
}
