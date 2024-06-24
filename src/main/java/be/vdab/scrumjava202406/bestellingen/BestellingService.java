package be.vdab.scrumjava202406.bestellingen;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BestellingService {
    private final BestellingRepository bestellingRepository;

    public BestellingService(BestellingRepository bestellingRepository) {
        this.bestellingRepository = bestellingRepository;
    }

    List<BestellingOverzicht> findVijfOudsteBestellingenOverzicht() {
        var lijstOfBestellingenId = bestellingRepository.findVijfOudsteBestellingen();
        List<BestellingOverzicht> resultBestellingen = new ArrayList<>();
        for (var bestelId : lijstOfBestellingenId) {
            var bestellingOverzicht = bestellingRepository.totaleGewichtBestelling(bestelId);
            resultBestellingen.add(new BestellingOverzicht(
                    bestelId,
                    (int) bestellingOverzicht.aantalArtikelen(),
                    BigDecimal.valueOf(bestellingOverzicht.totaleGewicht()).divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP)));
        }
        return resultBestellingen;
    }
}
