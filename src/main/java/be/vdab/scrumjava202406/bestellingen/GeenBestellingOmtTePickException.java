package be.vdab.scrumjava202406.bestellingen;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GeenBestellingOmtTePickException extends RuntimeException {
    public GeenBestellingOmtTePickException() {
        super("Geen bestellingen klaar omt te pick");
    }
}
