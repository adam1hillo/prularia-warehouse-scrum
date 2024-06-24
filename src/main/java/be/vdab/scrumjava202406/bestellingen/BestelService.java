package be.vdab.scrumjava202406.bestellingen;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
class BestelService {
    private final BestelRepository bestelRepository;

    public BestelService(BestelRepository bestelRepository) {
        this.bestelRepository = bestelRepository;
    }

    @Transactional
    void updateFilm(Integer id, Integer voorraad) {
        bestelRepository.updateTotaleVoorraad(id, voorraad);
    }
}

