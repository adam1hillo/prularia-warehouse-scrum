package be.vdab.scrumjava202406.bestellingen;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
class ArtikelService {
    private final ArtikelRepository artikelRepository;

    public ArtikelService(ArtikelRepository artikelRepository) {
        this.artikelRepository = artikelRepository;
    }

    @Transactional
    void updateTotaleVoorraad(long artikelId, NieuweVoorraad voorraad) {
        artikelRepository.updateTotaleVoorraad(artikelId, voorraad);
    }
}

