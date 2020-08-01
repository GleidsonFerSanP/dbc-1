package br.com.dbccompany.api.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UUIDValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface UUID {
    String message() default "Invalid UUID code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
