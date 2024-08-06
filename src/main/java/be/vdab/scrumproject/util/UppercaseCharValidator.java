package be.vdab.scrumproject.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UppercaseCharValidator implements ConstraintValidator<UppercaseChar, Character> {
    @Override
    public void initialize(UppercaseChar constraintAnnotation) {
    }

    @Override
    public boolean isValid(Character value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return Character.isUpperCase(value);
    }
}
