package be.vdab.scrumjava202406.bestellingen;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class ArtikelNietGevondenException extends RuntimeException {
    ArtikelNietGevondenException(Integer id) {
        super("Artikel niet gevonden. Id: " + id);
    }
}
