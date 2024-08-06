package be.vdab.scrumproject.bestellingen;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArtikelNietGevondenException extends RuntimeException {
    public ArtikelNietGevondenException(long id) {
        super("Artikel niet gevonden. Id verkeerd: " + id);
    }

    public ArtikelNietGevondenException(String eanLastFive) {
        super("Artikel niet gevonden. Ean verkeerd: " + eanLastFive);
    }

}
