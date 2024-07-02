package be.vdab.scrumjava202406.leveringen;

import be.vdab.scrumjava202406.bestellingen.MagazijnPlaats;
import be.vdab.scrumjava202406.bestellingen.MagazijnPlaatsNietGevondenException;
import be.vdab.scrumjava202406.bestellingen.MagazijnPlaatsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("leveringen")
public class LeveringController {

    private final MagazijnPlaatsService magazijnPlaatsService;
    private final LeveringService leveringService;

    public LeveringController(MagazijnPlaatsService magazijnPlaatsService, LeveringService leveringService) {
        this.magazijnPlaatsService = magazijnPlaatsService;
        this.leveringService = leveringService;
    }

    @GetMapping("checkFreeSpace/{id}")
    MagazijnPlaats checkFreeSpaceCanAddedToPlace(@PathVariable long id){
        return magazijnPlaatsService.checkFreeSpaceCanAddedToPlace(id)
                .orElseThrow(MagazijnPlaatsNietGevondenException::new);
    }

    @PatchMapping("updateAantal")
    void updateAantal(){
       magazijnPlaatsService.updateAantal(561, 5);
    }

    @GetMapping("algemeelUpdate/{id}/{aantal}")
    List<MagazijnPlaats> algemeelUpdate(@PathVariable long id, @PathVariable int aantal){
       return leveringService.updateAantalAlgemeel(id, aantal);
    }
}
