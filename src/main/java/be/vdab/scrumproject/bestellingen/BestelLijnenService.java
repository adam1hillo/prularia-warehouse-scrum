package be.vdab.scrumproject.bestellingen;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BestelLijnenService {
    private final BestelLijnenRepository BestellijnenRepository;

    public BestelLijnenService(BestelLijnenRepository BestellijnenRepository) {
        this.BestellijnenRepository = BestellijnenRepository;
    }

    public Optional<BestellingOmTePicken> findBestellingOmTePicken() {
        var pickLijst = new ArrayList<ArtikelIdNaamRijRekAantalBesteld>();
        List<BestelLijnenArtikelNaam> artikelList = BestellijnenRepository.findOudsteBestelLijnenMetArtikelNaamEnId();
        if (artikelList.isEmpty()) {
            return Optional.empty();
        }
        List<MagazijnplaatsAantalMetArtikelId> artikelenPlaatsen = BestellijnenRepository.findAllMagazijnplaatsPerArtikelAndBestelId(artikelList.getFirst().bestelId());

        artikelList.forEach(artikel -> {
            // Zoek de opslaglocaties voor het huidige artikel
            var plaatsVanArtikel = artikelenPlaatsen.stream()
                    .filter(magazijnplaatsAantalMetArtikelId -> magazijnplaatsAantalMetArtikelId.artikelId() == artikel.artikelId())
                    .toList();
            // Vind de juiste locaties voor het aantal artikelen nodig voor de bestelling
            int aantalenOmTePicken = artikel.aantalBesteld();
            for (MagazijnplaatsAantalMetArtikelId magazijnplaatsAantalMetArtikelId : plaatsVanArtikel) {
                if (magazijnplaatsAantalMetArtikelId.aantal() >= aantalenOmTePicken) {
                    var t = new ArtikelIdNaamRijRekAantalBesteld(magazijnplaatsAantalMetArtikelId, artikel, aantalenOmTePicken);
                    pickLijst.add(t);
                    break;
                } else if(magazijnplaatsAantalMetArtikelId.aantal() != 0){
                    aantalenOmTePicken -= magazijnplaatsAantalMetArtikelId.aantal();
                    var t = new ArtikelIdNaamRijRekAantalBesteld(magazijnplaatsAantalMetArtikelId, artikel, aantalenOmTePicken);
                    pickLijst.add(t);
                }
            }
        } );
        //volgorde moet (rij A eerst and rek 1 eerst)
        pickLijst.sort((o1, o2) -> {
            int rijComparison = Character.compare(o1.rij(), o2.rij());
            if (rijComparison == 0) {
                return Integer.compare(o1.rek(), o2.rek());
            } else {
                return rijComparison;
            }
        });
        return Optional.of(new BestellingOmTePicken(artikelList.getFirst().bestelId(), pickLijst));
    }


}
