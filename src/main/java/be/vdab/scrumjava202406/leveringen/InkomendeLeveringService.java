package be.vdab.scrumjava202406.leveringen;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)

class InkomendeLeveringService {
    private final InkomendeLeveringRepository inkomendeLeveringRepository;
    // InkomendeLeveringLijnenRepository nodig!
    // ArtikelRepository nodig om de max aantal per locatie te zien?

    public InkomendeLeveringService(InkomendeLeveringRepository inkomendeLeveringRepository) {
        this.inkomendeLeveringRepository = inkomendeLeveringRepository;
    }
    long leveringInvoeren(){
        return 3L;
    }
}
