package be.vdab.scrumjava202406.leveringen;

import be.vdab.scrumjava202406.bestellingen.ArtikelRepository;
import be.vdab.scrumjava202406.bestellingen.MagazijnPlaats;
import be.vdab.scrumjava202406.bestellingen.MagazijnPlaatsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)

class InkomendeLeveringService {
    private final InkomendeLeveringRepository inkomendeLeveringRepository;
    private final MagazijnPlaatsRepository magazijnPlaatsRepository;
    // InkomendeLeveringLijnenRepository nodig!
    // ArtikelRepository nodig om de max aantal per locatie te zien?

    public InkomendeLeveringService(InkomendeLeveringRepository inkomendeLeveringRepository,
                                    MagazijnPlaatsRepository magazijnPlaatsRepository,
                                    InkomendeleveringslijnRepository inkomendeleveringslijnRepository,
                                    ArtikelRepository artikelRepository) {
        this.inkomendeLeveringRepository = inkomendeLeveringRepository;
        this.magazijnPlaatsRepository = magazijnPlaatsRepository;
        this.inkomendeleveringslijnRepository = inkomendeleveringslijnRepository;
        this.artikelRepository = artikelRepository;
    }
    long leveringInvoeren(){
        return 3L;
    }

    public static Integer getAfgekeurdByArtikelId(List<ArtikelIdEnAfgekeurd> lijst, long artikelId) {
        for (ArtikelIdEnAfgekeurd item : lijst) {
            if (item.getArtikelId() == artikelId) {
                return item.getAfgekeurd();
            }
        }
        return null; // Return null if the artikelId is not found
    }

    @Transactional
    public long nieuweInkomendeLevering(NieuweInkomendeLevering nieuweInkomendeLevering ) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(nieuweInkomendeLevering.leveringsbondatum());
        ZonedDateTime zonedDateTime1 = ZonedDateTime.parse(nieuweInkomendeLevering.leverDatum());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = zonedDateTime.format(formatter);
        String formattedDate1 = zonedDateTime1.format(formatter);

        InkomendeLevering inkomendeLevering = new InkomendeLevering(0L,
                nieuweInkomendeLevering.leveranciersId(),
                nieuweInkomendeLevering.leveringsbonNummer(),
                LocalDate.parse(formattedDate,formatter) ,
                LocalDate.parse(formattedDate1,formatter));

        long inkomendeLeveringId = inkomendeLeveringRepository.create(inkomendeLevering);

        System.out.println("************");
        System.out.println(nieuweInkomendeLevering.magazijnPlaatsList());
        System.out.println(nieuweInkomendeLevering.artikelIdEnAfgekeurdList());


        for (ArtikelIdEnAfgekeurd artikelIdEnAfgekeurd : nieuweInkomendeLevering.artikelIdEnAfgekeurdList()) {
            artikelRepository.updateVoorraad(artikelIdEnAfgekeurd.getArtikelId(), artikelIdEnAfgekeurd.getGoedgekeurd());
        }


        // updatePlaces
        for (MagazijnPlaats magazijnPlaats : nieuweInkomendeLevering.magazijnPlaatsList()) {
          magazijnPlaatsRepository.updateAantalAndId(magazijnPlaats.getMagazijnPlaatsId(),magazijnPlaats.getArtikelId(),magazijnPlaats.getAantal());
          inkomendeleveringslijnRepository.create(new Inkomendeleveringslijn(
                  inkomendeLeveringId,
                  magazijnPlaats.getArtikelId(),
                  magazijnPlaats.getAantal(),
                  0,
                  magazijnPlaats.getMagazijnPlaatsId()
          ));
        }

        Map<Long, MagazijnPlaats> laatsteArtikelMap = new HashMap<>();

        for (MagazijnPlaats plaats : nieuweInkomendeLevering.magazijnPlaatsList()) {
            laatsteArtikelMap.put(plaats.getArtikelId(), plaats);
        }

        for (MagazijnPlaats laatstePlaats : laatsteArtikelMap.values()) {
            System.out.println(laatstePlaats);
            inkomendeleveringslijnRepository.update(new Inkomendeleveringslijn(
                    inkomendeLeveringId,
                    laatstePlaats.getArtikelId(),
                    laatstePlaats.getAantal(),
                    getAfgekeurdByArtikelId(nieuweInkomendeLevering.artikelIdEnAfgekeurdList(), laatstePlaats.getArtikelId()),
                    laatstePlaats.getMagazijnPlaatsId()
            ));
        }

        return inkomendeLeveringId;
    }
}
