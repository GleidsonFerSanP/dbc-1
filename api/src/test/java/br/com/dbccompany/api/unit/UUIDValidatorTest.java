package br.com.dbccompany.api.unit;

import br.com.dbccompany.api.validator.UUIDValidator;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static br.com.dbccompany.core.utils.TestUtils.randomText;
import static br.com.dbccompany.core.utils.TestUtils.randomUUID;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@DisplayName("testes unitários das validações customizadas")
public class UUIDValidatorTest {

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
    @DisplayName("testa se é um UUID vazio")
    public void makeUUIDValidatorFailToEmptyTextTest(){
        var uuidValidator = new UUIDValidator();
        boolean valid = uuidValidator.isValid("", null);
        assertFalse(valid);
    }

    @Test
    @DisplayName("testa se é um UUID null")
    public void makeUUIDValidatorFailToNullTextTest(){
        var uuidValidator = new UUIDValidator();
        boolean valid = uuidValidator.isValid(null, null);
        assertFalse(valid);
    }
}
