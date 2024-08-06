package be.vdab.scrumproject.leveringen;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LeveranciersService {
    private final LeveranciersRepository leveranciersRepository;

    public LeveranciersService(LeveranciersRepository leveranciersRepository) {
        this.leveranciersRepository = leveranciersRepository;
    }

    @Transactional
    List<LeverancierIdNaam> findAll() {
        return leveranciersRepository.findAll();
    }
}
