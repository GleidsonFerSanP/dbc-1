package br.com.dbccompany.api.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UUIDValidator implements ConstraintValidator<UUID, String> {
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        try {
            java.util.UUID.fromString(value);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
