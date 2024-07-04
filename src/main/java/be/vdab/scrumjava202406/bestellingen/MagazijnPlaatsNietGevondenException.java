package be.vdab.scrumjava202406.bestellingen;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MagazijnPlaatsNietGevondenException extends RuntimeException {
    public MagazijnPlaatsNietGevondenException() {
        super("Magazijnn plaats niet gevonden.");
    }
}
