package be.vdab.scrumjava202406.bestellingen;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BestellingNietGevondenException extends RuntimeException {
    BestellingNietGevondenException(long id) {
        super("Bestelling niet gevonden. Id: " + id);
    }
}


