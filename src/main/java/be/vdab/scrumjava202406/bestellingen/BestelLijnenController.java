package be.vdab.scrumjava202406.bestellingen;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("artikelen")
public class BestelLijnenController {
    private final BestelLijnenService bestelLijnenService;

    public BestelLijnenController(BestelLijnenService bestelLijnenService) {
        this.bestelLijnenService = bestelLijnenService;
    }

    @GetMapping("vanOudsteBestellingen")
    BestellingOmTePick findOudsteBestelling() {
        var result = bestelLijnenService.findBestellingOmTePick();
        if (result.bestelId() == -1) {
            throw new GeenBestellingOmtTePickException();
        }
        return result;
    }
}
