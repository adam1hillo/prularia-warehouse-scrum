package be.vdab.scrumjava202406.bestellingen;

import be.vdab.scrumjava202406.util.BestellingNietGevondenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BestellingService {
    private final BestellingRepository bestellingRepository;

    public BestellingService(BestellingRepository bestellingRepository) {
        this.bestellingRepository = bestellingRepository;
    }

    @Transactional
    void updateBestellingStatusToOnderweg(int bestelId){
        Optional<BestelIdDTO> lockedBestelId = bestellingRepository.findAndLockByBestelId(bestelId);
        if (lockedBestelId.isPresent()) {
            bestellingRepository.updateBestellingStatus(bestelId);
        } else {
            throw new BestellingNietGevondenException(bestelId);
        }
    }
}
