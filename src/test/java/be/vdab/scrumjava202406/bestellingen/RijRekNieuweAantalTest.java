package be.vdab.scrumjava202406.bestellingen;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RijRekNieuweAantalTest {
    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenCharIsUppercase_thenNoConstraintViolations() {
        RijRekNieuweAantal validObject = new RijRekNieuweAantal('A', 4, 3);
        Set<ConstraintViolation<RijRekNieuweAantal>> violations = validator.validate(validObject);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void whenCharIsLowercase_thenConstraintViolations() {
        RijRekNieuweAantal invalidObject = new RijRekNieuweAantal('b', 4, 3);
        Set<ConstraintViolation<RijRekNieuweAantal>> violations = validator.validate(invalidObject);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("The character must be uppercase")));
    }

    @Test
    public void whenRekIsOutOfRange_thenConstraintViolations() {
        RijRekNieuweAantal invalidObject = new RijRekNieuweAantal('A', 0, 3);
        Set<ConstraintViolation<RijRekNieuweAantal>> violations = validator.validate(invalidObject);
        assertFalse(violations.isEmpty());
//        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must be greater than or equal to 1") || v.getMessage().contains("moet groter of gelijk aan 1 zijn")));
    }

    @Test
    public void whenAantalIsNegative_thenConstraintViolations() {
        RijRekNieuweAantal invalidObject = new RijRekNieuweAantal('A', 4, -1);
        Set<ConstraintViolation<RijRekNieuweAantal>> violations = validator.validate(invalidObject);
        assertFalse(violations.isEmpty());
//        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must be greater than 0") || v.getMessage().contains("moet groter dan 0 zijn")));
    }
}
