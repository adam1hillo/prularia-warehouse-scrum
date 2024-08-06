package be.vdab.scrumproject.leveringen;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("leveranciers")


class LeveranciersController {
    private final LeveranciersService leveranciersService;

    public LeveranciersController(LeveranciersService leveranciersService) {
        this.leveranciersService = leveranciersService;
    }
    @GetMapping()
    List<LeverancierIdNaam> findAll(){
        return leveranciersService.findAll();

    }


}
