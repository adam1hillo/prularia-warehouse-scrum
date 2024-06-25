package be.vdab.scrumjava202406.bestellingen;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BestellingController {
    private final BestellingService bestellingService;

    BestellingController(BestellingService bestellingService) {
        this.bestellingService = bestellingService;
    }

    @GetMapping("bestellingen/aantal")
    long findCount() {
        return bestellingService.aantalKlaarOmGepickt();
    }
}
