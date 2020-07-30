package br.com.dbccompany.api.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VoteOptionValidator implements ConstraintValidator<VoteOption, String> {
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return "Nao".equalsIgnoreCase(value) || "Sim".equalsIgnoreCase(value);
    }
}
