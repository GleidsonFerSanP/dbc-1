package br.com.dbccompany.api.unit;

import br.com.dbccompany.api.utils.TestUtils;
import br.com.dbccompany.api.validator.UUIDValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.test.context.ContextConfiguration;

import javax.validation.ConstraintValidatorContext;

import static br.com.dbccompany.api.utils.TestUtils.randomText;
import static br.com.dbccompany.api.utils.TestUtils.randomUUID;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@DisplayName("testes unitários das validações customizadas")
public class CustomValidatorTest {

    @Test
    @DisplayName("testa se é um UUID válido")
    public void makeUUIDValidatorSuccessTest(){
        var uuidValidator = new UUIDValidator();
        boolean valid = uuidValidator.isValid(randomUUID(), null);
        assertTrue(valid);
    }

    @Test
    @DisplayName("testa se é um UUID inválido")
    public void makeUUIDValidatorFailTest(){
        var uuidValidator = new UUIDValidator();
        boolean valid = uuidValidator.isValid(randomText(15), null);
        assertFalse(valid);
    }

    @Test
    @DisplayName("testa se é um UUID inválido")
    public void makeUUIDValidatorFailToEmptyTextTest(){
        var uuidValidator = new UUIDValidator();
        boolean valid = uuidValidator.isValid("", null);
        assertFalse(valid);
    }

    @Test
    @DisplayName("testa se é um UUID inválido")
    public void makeUUIDValidatorFailToNullTextTest(){
        var uuidValidator = new UUIDValidator();
        boolean valid = uuidValidator.isValid(null, null);
        assertFalse(valid);
    }
}
