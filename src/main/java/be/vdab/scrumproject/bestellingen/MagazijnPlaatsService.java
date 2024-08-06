package be.vdab.scrumproject.bestellingen;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MagazijnPlaatsService {
    private final MagazijnPlaatsRepository magazijnPlaatsRepository;
    private final ArtikelRepository artikelRepository;

    public MagazijnPlaatsService(MagazijnPlaatsRepository magazijnPlaatsRepository,ArtikelRepository artikelRepository) {
        this.magazijnPlaatsRepository = magazijnPlaatsRepository;
        this.artikelRepository = artikelRepository;
    }

    List<MagazijnPlaats> findAllPlaatsById(long id) {
        return magazijnPlaatsRepository.findAllPlaatsById(id);
    }


   /* public NieuweMagazijnPlaats findFirstFreePlace(){
        return magazijnPlaatsRepository.findFirstFreePlace();
    }*/
    public Optional<MagazijnPlaats> checkFreeSpaceCanAddedToPlace(long id){
        Artikel artikel = artikelRepository.findById(id).orElseThrow();
        return magazijnPlaatsRepository.checkFreeSpaceCanAddedToPlace(id,artikel.getMaxAantalInMagazijnPlaats());
    }

    @Transactional
    public void updateAantal(long magazijnPlaatsId, int aantal){
        magazijnPlaatsRepository.updateAantal(magazijnPlaatsId, aantal);
    }
}
