package be.vdab.scrumjava202406.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GeenBestellingGevondenException extends RuntimeException {
    public GeenBestellingGevondenException() {
        super("Geen bestelling gevonden");
    }
}
