package be.vdab.scrumjava202406.bestellingen;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
class BestelService {
    private final BestelRepository bestelRepository;

    public BestelService(BestelRepository bestelRepository) {
        this.bestelRepository = bestelRepository;
    }

    @Transactional
    void updateTotaleVoorraad(Integer artikelId, Integer voorraad) {
        bestelRepository.updateTotaleVoorraad(artikelId, voorraad);
    }
}

