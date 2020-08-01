package br.com.dbccompany.api.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = VoteOptionValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface VoteOption {
    String message() default "Invalid option selected";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
