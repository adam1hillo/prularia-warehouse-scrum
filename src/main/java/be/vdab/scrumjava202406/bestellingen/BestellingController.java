package be.vdab.scrumjava202406.bestellingen;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bestelling")
public class BestellingController {
    private final BestellingService bestellingService;

    public BestellingController(BestellingService bestellingService) {
        this.bestellingService = bestellingService;
    }
    @PatchMapping("updateStatusOnderweg/{bestelId}")
    void updateBestellingStatusToOnderweg(@PathVariable int bestelId){
        bestellingService.updateBestellingStatusToOnderweg(bestelId);
    }
}
