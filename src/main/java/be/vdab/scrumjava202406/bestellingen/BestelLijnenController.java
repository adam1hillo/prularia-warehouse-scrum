package be.vdab.scrumjava202406.bestellingen;

import be.vdab.scrumjava202406.util.GeenBestellingGevondenException;
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
    BestellingOmTePicken findOudsteBestelling() {
        return bestelLijnenService.findBestellingOmTePicken().orElseThrow(GeenBestellingGevondenException::new);
    }
}
