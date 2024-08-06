package be.vdab.scrumproject.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BestellingNietGevondenException extends RuntimeException{
    public BestellingNietGevondenException(long id) {
        super("Bestelling niet gevonden: " + id);
    }
}
