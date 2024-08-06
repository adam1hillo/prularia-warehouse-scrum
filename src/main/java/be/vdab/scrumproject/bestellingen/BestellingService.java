package be.vdab.scrumproject.bestellingen;

import be.vdab.scrumproject.util.BestellingNietGevondenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BestellingService {
    private final BestellingRepository bestellingRepository;

    public BestellingService(BestellingRepository bestellingRepository) {
        this.bestellingRepository = bestellingRepository;
    }

    long aantalKlaarOmGepickt() {
        return bestellingRepository.countBestellingKlaarOmGepickt();
    }


    List<BestellingOverzichtTVScherm> findVijfOudsteBestellingenOverzicht() {
        var lijstOfBestellingenId = bestellingRepository.findVijfOudsteBestellingen();
        List<BestellingOverzichtTVScherm> resultBestellingen = new ArrayList<>();
        for (var bestelId : lijstOfBestellingenId) {
            var bestellingMetAantalEnGewicht = bestellingRepository.aantalArtikelenTotaleGewichtPerBestelling(bestelId);
            resultBestellingen.add(new BestellingOverzichtTVScherm(
                    bestelId,
                    (int) bestellingMetAantalEnGewicht.aantalArtikelen(),
                    BigDecimal.valueOf(bestellingMetAantalEnGewicht.totaleGewicht()).divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP)));
        }
        return resultBestellingen;
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
