package be.vdab.scrumjava202406.bestellingen;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    @Transactional
    void updateBestellingStatusToOnderweg(int bestelId){
        Optional<ArtikelRepository.BestelId> lockedBestelId = artikelRepository.findAndLockByBesteId(bestelId);
        if (lockedBestelId.isPresent()) {
            artikelRepository.updateBestellingStatus(bestelId);
        } else {
            throw new ArtikelNietGevondenException(bestelId);
        }
    }
}

