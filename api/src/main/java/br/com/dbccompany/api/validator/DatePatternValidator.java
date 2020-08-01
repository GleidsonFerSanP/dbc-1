package br.com.dbccompany.api.validator;

import br.com.dbccompany.core.utils.DateUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.util.Date;

public class DatePatternValidator implements ConstraintValidator<DatePattern, String> {

    private String pattern;

    @Override
    public void initialize(DatePattern constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        try {
            DateUtils.textToDate(value, this.pattern);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
