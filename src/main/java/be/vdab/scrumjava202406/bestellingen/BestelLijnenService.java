package be.vdab.scrumjava202406.bestellingen;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BestelLijnenService {
    private final BestelLijnenRepository BestellijnenRepository;

    public BestelLijnenService(BestelLijnenRepository BestellijnenRepository) {
        this.BestellijnenRepository = BestellijnenRepository;
    }

    public BestellingOmTePick findBestellingOmTePick() {
        var pickLijst = new ArrayList<BestelIdLijstArtikelOmTePick>();
        List<BestellingLijnenArtikelNaam> artikelList = BestellijnenRepository.bestellingLijnenArtikelNaam();
        if (artikelList.isEmpty()) {
            return new BestellingOmTePick(-1, new ArrayList<>());
        }
        List<MagazijnplaatAantalMetArtikelId> artikelPlaats = BestellijnenRepository.magazijnplaatPerArtikelAndBestelId(artikelList.getFirst().bestelId());

        
        artikelList.forEach(artikel -> {
            // Zoek de opslaglocaties voor het huidige artikel
            var plaatsVanArtikel = artikelPlaats.stream()
                    .filter(magazijnplaatAantalMetArtikelId -> magazijnplaatAantalMetArtikelId.artikelId() == artikel.artikelId())
                    .toList();
            // Vind de juiste locaties voor het aantal artikel nodig voor het bestel
            int aantalenOmTePick = artikel.aantalBesteld();
            for (MagazijnplaatAantalMetArtikelId magazijnplaatAantalMetArtikelId : plaatsVanArtikel) {
                if (magazijnplaatAantalMetArtikelId.aantal() >= aantalenOmTePick) {
                    var t = new BestelIdLijstArtikelOmTePick(magazijnplaatAantalMetArtikelId, artikel, aantalenOmTePick);
                    pickLijst.add(t);
                    break;
                } else if(magazijnplaatAantalMetArtikelId.aantal() != 0){
                    aantalenOmTePick -= magazijnplaatAantalMetArtikelId.aantal();
                    var t = new BestelIdLijstArtikelOmTePick(magazijnplaatAantalMetArtikelId, artikel, aantalenOmTePick);
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
        return new BestellingOmTePick(artikelList.getFirst().bestelId(), pickLijst);
    }


}
