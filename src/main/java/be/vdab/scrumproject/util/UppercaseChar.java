package be.vdab.scrumproject.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UppercaseCharValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface UppercaseChar {
    String message() default "The character must be uppercase";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

