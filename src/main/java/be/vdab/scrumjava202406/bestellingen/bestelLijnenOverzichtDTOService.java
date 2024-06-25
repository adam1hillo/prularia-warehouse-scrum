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

    public List<bestelLijnenOverzichtDTO> findOudsteBestelling() {
        return null;
    }
    /**
     *  Set/list 1 lijn = 1 artikel AND n antal van artikel nodig voor de bestelling
     *
     *  per artikle in deze lijst moet ik alle de magazijn plaat / aantal par plaats
     *
     *  lijst zal plaat/ antal moet in deze plaat  / artikel
     *
     */

}
