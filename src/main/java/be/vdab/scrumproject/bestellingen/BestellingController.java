package be.vdab.scrumproject.bestellingen;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    @PatchMapping("updateStatusOnderweg/{bestelId}")
    void updateBestellingStatusToOnderweg(@PathVariable int bestelId){
        bestellingService.updateBestellingStatusToOnderweg(bestelId);
    }
}
