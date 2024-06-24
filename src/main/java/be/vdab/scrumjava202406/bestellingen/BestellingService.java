package be.vdab.scrumjava202406.bestellingen;

import org.springframework.stereotype.Service;

@Service
public class BestellingService {
    private final BestellingRepository bestellingRepository;

    public BestellingService(BestellingRepository bestellingRepository) {
        this.bestellingRepository = bestellingRepository;
    }

    long aantalKlaarOmGepickt() {
        return bestellingRepository.countBestellingKlaarOmGepickt();
    }
}
