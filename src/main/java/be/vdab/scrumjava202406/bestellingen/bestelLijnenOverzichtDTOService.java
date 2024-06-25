package be.vdab.scrumjava202406.bestellingen;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class bestelLijnenOverzichtDTOService {
    private final bestelLijnenOverzichtDTORepository bestelLijnenOverzichtDTORepository;

    public bestelLijnenOverzichtDTOService(bestelLijnenOverzichtDTORepository bestelLijnenOverzichtDTORepository) {
        this.bestelLijnenOverzichtDTORepository = bestelLijnenOverzichtDTORepository;
    }
    public List<bestelLijnenOverzichtDTO> findOudsteBestelling(){
       return bestelLijnenOverzichtDTORepository.findOudsteBestelling();
    }
}
