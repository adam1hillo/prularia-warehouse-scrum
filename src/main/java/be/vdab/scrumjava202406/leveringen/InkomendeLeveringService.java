package be.vdab.scrumjava202406.leveringen;

import be.vdab.scrumjava202406.bestellingen.MagazijnPlaats;
import be.vdab.scrumjava202406.bestellingen.MagazijnPlaatsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)

class InkomendeLeveringService {
    private final InkomendeLeveringRepository inkomendeLeveringRepository;
    private final MagazijnPlaatsRepository magazijnPlaatsRepository;
    // InkomendeLeveringLijnenRepository nodig!
    // ArtikelRepository nodig om de max aantal per locatie te zien?

    public InkomendeLeveringService(InkomendeLeveringRepository inkomendeLeveringRepository,
                                    MagazijnPlaatsRepository magazijnPlaatsRepository) {
        this.inkomendeLeveringRepository = inkomendeLeveringRepository;
        this.magazijnPlaatsRepository = magazijnPlaatsRepository;
    }
    long leveringInvoeren(){
        return 3L;
    }

    @Transactional
    public long nieuweInkomendeLevering(NieuweInkomendeLevering nieuweInkomendeLevering ) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(nieuweInkomendeLevering.leveringsbondatum());
        ZonedDateTime zonedDateTime1 = ZonedDateTime.parse(nieuweInkomendeLevering.leverDatum());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = zonedDateTime.format(formatter);
        String formattedDate1 = zonedDateTime1.format(formatter);

        System.out.println("************");
        System.out.println(nieuweInkomendeLevering.magazijnPlaatsList());
        System.out.println(nieuweInkomendeLevering.afgekeurd());

        Map<Long, MagazijnPlaats> laatsteArtikelMap = new HashMap<>();

        for (MagazijnPlaats plaats : nieuweInkomendeLevering.magazijnPlaatsList()) {
            laatsteArtikelMap.put(plaats.getArtikelId(), plaats);
        }

        // Son öğelere belirli bir değer ekleme
        for (MagazijnPlaats laatstePlaats : laatsteArtikelMap.values()) {
            // Örneğin, aantal değerini 10 arttırıyoruz.
            System.out.println(laatstePlaats);
        }



        // updatePlaces
        /*for (MagazijnPlaats magazijnPlaats : nieuweInkomendeLevering.magazijnPlaatsList()) {
          magazijnPlaatsRepository.updateAantalAndId(magazijnPlaats.getMagazijnPlaatsId(),magazijnPlaats.getArtikelId(),magazijnPlaats.getAantal());
        }*/

        InkomendeLevering inkomendeLevering = new InkomendeLevering(0L,
                nieuweInkomendeLevering.leveranciersId(),
                nieuweInkomendeLevering.leveringsbonNummer(),
                LocalDate.parse(formattedDate,formatter) ,
                LocalDate.parse(formattedDate1,formatter));
        return inkomendeLeveringRepository.create(inkomendeLevering);
    }
}
