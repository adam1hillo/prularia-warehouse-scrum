package be.vdab.scrumproject.leveringen;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("leveringen")
public class InkomendeLeveringController {
    private final InkomendeLeveringService inkomendeLeveringService;

    public InkomendeLeveringController(InkomendeLeveringService inkomendeLeveringService) {
        this.inkomendeLeveringService = inkomendeLeveringService;
    }


    @PostMapping("create")
        //deze method geeft de inkomendeLeveringsId terug van de nieuw aangemaakte levering
    long nieuweInkomendeLevering(
            @RequestBody @Valid NieuweInkomendeLevering nieuweInkomendeLevering ) {
        return inkomendeLeveringService.nieuweInkomendeLevering(nieuweInkomendeLevering);

    }
 /*
    @PostMapping("{inkomendeLeveringsId}")
    void nieuweInkomendeLeveringsLijn(
            @PathVariable long inkomendeLeveringsId,
            @RequestBody @Valid NieuweInkomendeLeveringsLijn nieuweInkomendeLeveringsLijn) {
        inkomendeLeveringService.nieuweInkomendeLeveringsLijn(inkomendeLeveringsId, nieuweInkomendeLeveringsLijn);
    }
         */


}
