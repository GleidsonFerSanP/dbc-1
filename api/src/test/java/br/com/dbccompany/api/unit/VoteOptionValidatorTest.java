package br.com.dbccompany.api.unit;

import br.com.dbccompany.api.utils.TestUtils;
import br.com.dbccompany.api.validator.UUIDValidator;
import br.com.dbccompany.api.validator.VoteOptionValidator;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static br.com.dbccompany.api.utils.TestUtils.randomText;
import static br.com.dbccompany.api.utils.TestUtils.randomUUID;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@DisplayName("testes unitários das validações customizadas")
public class VoteOptionValidatorTest {

    @Test
    @DisplayName("testa se é uma VoteOption Sim é válida")
    public void makeVoteOptionSimSuccessTest(){
        var voteOptionValidator = new VoteOptionValidator();
        boolean valid = voteOptionValidator.isValid("Sim", null);
        assertTrue(valid);
    }

    @Test
    @DisplayName("testa se é uma VoteOption Nao é válida")
    public void makeVoteOptionNaoSuccessTest(){
        var voteOptionValidator = new VoteOptionValidator();
        boolean valid = voteOptionValidator.isValid("Nao", null);
        assertTrue(valid);
    }

    @Test
    @DisplayName("testa se é uma VoteOption vazio é inválido")
    public void makeVoteOptionFailToEmptyTextTest(){
        var voteOptionValidator = new VoteOptionValidator();
        boolean valid = voteOptionValidator.isValid("", null);
        assertFalse(valid);
    }

    @Test
    @DisplayName("testa se é uma VoteOption null inválido")
    public void makeVoteOptionFailToNullTextTest(){
        var voteOptionValidator = new VoteOptionValidator();
        boolean valid = voteOptionValidator.isValid(null, null);
        assertFalse(valid);
    }

    @Test
    @DisplayName("testa se é uma VoteOption para qualquer outro texto diferente de [Sim, Nao] é inválido")
    public void makeVoteOptionFailToOthersTextTest(){
        var voteOptionValidator = new VoteOptionValidator();
        boolean valid = voteOptionValidator.isValid(TestUtils.randomText(3), null);
        assertFalse(valid);
    }
}
