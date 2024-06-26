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
}
