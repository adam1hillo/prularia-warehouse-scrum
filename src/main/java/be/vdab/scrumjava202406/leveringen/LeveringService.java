package be.vdab.scrumjava202406.leveringen;

import be.vdab.scrumjava202406.bestellingen.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LeveringService {
    private final MagazijnPlaatsRepository magazijnPlaatsRepository;
    private final ArtikelRepository artikelRepository;

    public LeveringService(MagazijnPlaatsRepository magazijnPlaatsRepository,ArtikelRepository artikelRepository) {
        this.magazijnPlaatsRepository = magazijnPlaatsRepository;
        this.artikelRepository = artikelRepository;
    }

    List<MagazijnPlaats> findAllPlaatsById(long id) {
        return magazijnPlaatsRepository.findAllPlaatsById(id);
    }

    public NieuweMagazijnPlaats findFirstFreePlace(long magazijnPlaatsId){
        return magazijnPlaatsRepository.findFirstFreePlace(magazijnPlaatsId);
    }
    public Optional<MagazijnPlaats> checkFreeSpaceCanAddedToPlace(long id){
        Artikel artikel = artikelRepository.findById(id).orElseThrow();
        return magazijnPlaatsRepository.checkFreeSpaceCanAddedToPlace(id,artikel.getMaxAantalInMagazijnPlaats());
    }

    @Transactional
    public void updateAantal(long magazijnPlaatsId, int aantal){
        magazijnPlaatsRepository.updateAantal(magazijnPlaatsId, aantal);
    }

    @Transactional
    public List<MagazijnPlaats> updateAantalAlgemeel(long artikelId, int aantal) {
        List<MagazijnPlaats> listVanMagazijnPlaatsen = new ArrayList<>();
        Artikel artikel = artikelRepository.findById(artikelId).orElseThrow();
        System.out.println(artikel.getMaxAantalInMagazijnPlaats());
        System.out.println(artikelId);
        System.out.println(aantal);

       checkFreeSpaceCanAddedToPlace(artikelId).ifPresentOrElse(
                (magazijnPlaats)
                        -> {
                    System.out.println("Value is present, its: " + magazijnPlaats);
                    if(artikel.getMaxAantalInMagazijnPlaats() - magazijnPlaats.getAantal() >= aantal){
                        // 25 - 15 >= 5
                        System.out.println("direct plaatsen terug geven");
                        MagazijnPlaats newMagazijnPlaats = new MagazijnPlaats(
                                magazijnPlaats.getMagazijnPlaatsId(),
                                magazijnPlaats.getArtikelId(),
                                magazijnPlaats.getRij(),
                                magazijnPlaats.getRek(),
                                magazijnPlaats.getAantal() + aantal
                                );
                         listVanMagazijnPlaatsen.add(newMagazijnPlaats);
                        System.out.println(newMagazijnPlaats);
                    }else{
                        System.out.println("for loop");
                        MagazijnPlaats newMagazijnPlaats = new MagazijnPlaats(
                                magazijnPlaats.getMagazijnPlaatsId(),
                                magazijnPlaats.getArtikelId(),
                                magazijnPlaats.getRij(),
                                magazijnPlaats.getRek(),
                                artikel.getMaxAantalInMagazijnPlaats()
                        );
                        listVanMagazijnPlaatsen.add(newMagazijnPlaats);
                        int remainingTotaal = aantal - (artikel.getMaxAantalInMagazijnPlaats()-magazijnPlaats.getAantal());
                        System.out.println("remainig");
                        System.out.println(remainingTotaal);
                        System.out.println(remainingTotaal / artikel.getMaxAantalInMagazijnPlaats());
                        long firstAvailablePlaceId = magazijnPlaatsRepository.findFirstFreePlaceId();
                        System.out.println(firstAvailablePlaceId);
                        for (int i = 0; i < (remainingTotaal / artikel.getMaxAantalInMagazijnPlaats()) + 1 ; i++) {

                            System.out.println(i);
                            NieuweMagazijnPlaats nieuweMagazijnPlaats = findFirstFreePlace(firstAvailablePlaceId + i);
                            if (i == remainingTotaal / artikel.getMaxAantalInMagazijnPlaats()) {
                                listVanMagazijnPlaatsen.add(new MagazijnPlaats(
                                        nieuweMagazijnPlaats.getMagazijnPlaatsId(),
                                        magazijnPlaats.getArtikelId(),
                                        nieuweMagazijnPlaats.getRij(),
                                        nieuweMagazijnPlaats.getRek(),
                                        aantal -
                                                (remainingTotaal / artikel.getMaxAantalInMagazijnPlaats() * artikel.getMaxAantalInMagazijnPlaats())
                                                -
                                                (artikel.getMaxAantalInMagazijnPlaats() - magazijnPlaats.getAantal())
                                ));
                            }else{
                                listVanMagazijnPlaatsen.add(new MagazijnPlaats(
                                        nieuweMagazijnPlaats.getMagazijnPlaatsId(),
                                        magazijnPlaats.getArtikelId(),
                                        nieuweMagazijnPlaats.getRij(),
                                        nieuweMagazijnPlaats.getRek(),
                                        artikel.getMaxAantalInMagazijnPlaats()
                                ));
                            }
                        }

                    }

                    },
                ()
                        -> {
                    System.out.println("Value is empty");
                }

                );

        System.out.println(listVanMagazijnPlaatsen);
        return listVanMagazijnPlaatsen;

    }
}
