package be.vdab.scrumjava202406.bestellingen;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bestellingen")
public class BestellingController {
    private final BestellingService bestellingService;

    BestellingController(BestellingService bestellingService) {
        this.bestellingService = bestellingService;
    }

    @GetMapping("vijfoudstebestellingen")
    List<BestellingOverzichtTVScherm> bestellingOverzicht() {
        return bestellingService.findVijfOudsteBestellingenOverzicht();
    }

    @GetMapping("aantal")
    long findCount() {
        return bestellingService.aantalKlaarOmGepickt();
    }
}
