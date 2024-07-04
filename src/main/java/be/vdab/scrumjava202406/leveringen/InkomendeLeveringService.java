package be.vdab.scrumjava202406.leveringen;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    @Transactional
    public long nieuweInkomendeLevering(NieuweInkomendeLevering nieuweInkomendeLevering) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        InkomendeLevering inkomendeLevering = new InkomendeLevering(0L,
                nieuweInkomendeLevering.leveranciersId(),
                nieuweInkomendeLevering.leveringsbonNummer(),
                LocalDate.parse(nieuweInkomendeLevering.leveringsbondatum(),formatter) ,
                LocalDate.parse(nieuweInkomendeLevering.leverDatum(),formatter));
        return inkomendeLeveringRepository.create(inkomendeLevering);
    }
}
