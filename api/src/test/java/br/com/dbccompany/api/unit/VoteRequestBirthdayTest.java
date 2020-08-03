package br.com.dbccompany.api.unit;

import br.com.dbccompany.api.resource.request.v1.VoteRequest;
import br.com.dbccompany.api.validator.DatePatternValidator;
import br.com.dbccompany.api.validator.UUIDValidator;
import br.com.dbccompany.core.utils.TestUtils;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import javax.validation.*;

import java.util.Set;

import static br.com.dbccompany.core.utils.TestUtils.randomText;
import static br.com.dbccompany.core.utils.TestUtils.randomUUID;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("testes unitários das validações customizadas")
public class VoteRequestBirthdayTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterClass
    public static void close() {
        validatorFactory.close();
    }

    @Test
    @DisplayName("testa um string inválida para data e falha")
    public void validateAInvalidStringPatternToDateFail(){

        final var voteRequest = VoteRequest.builder()
                .cpf("55213866026")
                .option("Sim")
                .scheduleCode(TestUtils.randomUUID())
                .birthday("A")
                .build();

        final Set<ConstraintViolation<VoteRequest>> violations
                = validator.validate(voteRequest);

        assertEquals( 1, violations.size());
    }

    @Test
    @DisplayName("testa uma string válida para um data, porém diferente do pattern definido e falha")
    public void validateAValidPatternToDateButDifferentPatternDefinedAndFail(){

        final var voteRequest = VoteRequest.builder()
                .cpf("55213866026")
                .option("Sim")
                .scheduleCode(TestUtils.randomUUID())
                .birthday("29-07-2020")
                .build();

        final Set<ConstraintViolation<VoteRequest>> violations
                = validator.validate(voteRequest);

        assertEquals( 1, violations.size());
    }

    @Test
    @DisplayName("testa um valor null para o birthday e falha")
    public void validateBirthdayNullFail(){

        final var voteRequest = VoteRequest.builder()
                .cpf("55213866026")
                .option("Sim")
                .scheduleCode(TestUtils.randomUUID())
                .birthday(null)
                .build();

        final Set<ConstraintViolation<VoteRequest>> violations
                = validator.validate(voteRequest);

        assertEquals( 2, violations.size());
    }

    @Test
    @DisplayName("testa um valor vazio para o birthday e falha")
    public void validateBirthdayEmptyTextFail(){

        final var voteRequest = VoteRequest.builder()
                .cpf("55213866026")
                .option("Sim")
                .scheduleCode(TestUtils.randomUUID())
                .birthday("")
                .build();

        final Set<ConstraintViolation<VoteRequest>> violations
                = validator.validate(voteRequest);

        assertEquals( 2, violations.size());
    }

    @Test
    @DisplayName("testa uma string válida para um data, com o pattern definido com sucesso")
    public void validateAValidPatternToDateWithSuccess(){

        final var voteRequest = VoteRequest.builder()
                .cpf("55213866026")
                .option("Sim")
                .scheduleCode(TestUtils.randomUUID())
                .birthday("29/07/1979")
                .build();

        final Set<ConstraintViolation<VoteRequest>> violations
                = validator.validate(voteRequest);

        assertEquals( 0, violations.size());
    }
}
