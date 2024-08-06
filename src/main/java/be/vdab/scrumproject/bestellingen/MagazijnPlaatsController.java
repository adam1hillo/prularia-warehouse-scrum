package be.vdab.scrumproject.bestellingen;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("magazijnPlaatsen")
public class MagazijnPlaatsController {
    private final MagazijnPlaatsService magazijnPlaatsService;

    public MagazijnPlaatsController(MagazijnPlaatsService magazijnPlaatsService) {
        this.magazijnPlaatsService = magazijnPlaatsService;
    }

    @GetMapping("{id}")
    List<MagazijnPlaats> findAllPlaatsById(@PathVariable long id) {
        return magazijnPlaatsService.findAllPlaatsById(id);
    }
}
