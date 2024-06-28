package be.vdab.scrumjava202406.bestellingen;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MagazijnPlaatsService {
    private final MagazijnPlaatsRepository magazijnPlaatsRepository;

    public MagazijnPlaatsService(MagazijnPlaatsRepository magazijnPlaatsRepository) {
        this.magazijnPlaatsRepository = magazijnPlaatsRepository;
    }

    List<MagazijnPlaats> findAllPlaatsById(long id) {
        return magazijnPlaatsRepository.findAllPlaatsById(id);
    }
}
