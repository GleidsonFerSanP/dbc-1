package br.com.dbccompany.api.integration.vote;

import br.com.dbccompany.api.integration.IntegrationBaseTest;
import br.com.dbccompany.api.resource.mediatype.V1MediaType;
import br.com.dbccompany.api.resource.request.v1.VoteRequest;
import br.com.dbccompany.core.utils.TestUtils;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static br.com.dbccompany.core.utils.DateUtils.mockCalendar;
import static org.hamcrest.Matchers.*;

@DisplayName("testes de integração da votação")
public class VoteTest extends IntegrationBaseTest {

    private static final String BASE_URL = "/votes";

    @Test
    @DisplayName("falha ao realizar um POST sem body")
    public void createVotesValidateEmptyBodyFail(){

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("falha ao realizar um POST sem definir um media type")
    public void createVotesValidateMediaTypeBodyFail(){

        var request = VoteRequest.builder()
                .build();

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());

    }

    @Test
    @DisplayName("falha ao criar um voto sem scheduleCode")
    public void createVotesValidateScheduleCodeFail() {

        var request = VoteRequest.builder()
                .option("Sim")
                .cpf("00253361001")
                .build();

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(notNullValue())
                .body("errors", notNullValue())
                .body(containsString("Invalid UUID code [scheduleCode]"))
                .body(containsString("this field cannot be empty [scheduleCode]"))
                .body("errors[0].httpStatus", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("errors[0].error", equalTo("ConstraintViolationException"))
        ;
    }

    @Test
    @DisplayName("falha ao criar um voto para uma pauta que não existe")
    public void createVoteToScheduleNotExistsFail() {
        var scheduleCode = "8de44aec-d624-44d7-b14b-d342fc012347";
        var request = VoteRequest.builder()
                .scheduleCode(scheduleCode)
                .birthday("29/07/1979")
                .cpf("26829510074")
                .option("Sim")
                .build();

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(notNullValue())
                .body("message", equalTo("schedule not found by code ".concat(scheduleCode)))
                .body("httpStatus", equalTo(HttpStatus.NOT_FOUND.value()))
                .body("error", equalTo("NotFoundException"))
        ;
    }

    @Test
    @DisplayName("falha ao criar um voto para uma pauta que expirou o periodo de votação")
    @Sql("/sql/schedule-insert-with-expiration-date.sql")
    public void createVoteToScheduleExpiredVotesFail() {
        var scheduleCode = "8de44aec-d624-44d7-b14b-d342fc0bf156";
        var request = VoteRequest.builder()
                .scheduleCode(scheduleCode)
                .cpf("26829510074")
                .birthday("29/07/1979")
                .option("Sim")
                .build();

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(notNullValue())
                .body("message", equalTo("this schedule expired"))
                .body("httpStatus", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("error", equalTo("ScheduleExpiredException"))
        ;
    }

    @Test
    @DisplayName("falha ao criar um voto para uma pauta que não foi aberta votação")
    @Sql("/sql/schedule-insert-3.sql")
    public void createVoteToScheduleNotOpenVotesFail() {
        var scheduleCode = "8de44aec-d624-44d7-b14b-d342fc0bf14c";
        var request = VoteRequest.builder()
                .scheduleCode(scheduleCode)
                .cpf("26829510074")
                .birthday("29/07/1979")
                .option("Sim")
                .build();

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(notNullValue())
                .body("message", equalTo("this schedule not be open to voting"))
                .body("httpStatus", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("error", equalTo("ScheduleNotOpenException"))
        ;
    }

    @Test
    @DisplayName("falha ao criar uma voto sem a opção votada")
    public void createVotesValidateOptionFail() {

        var request = VoteRequest.builder()
                .cpf("00253361001")
                .scheduleCode(TestUtils.randomUUID())
                .build();

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(notNullValue())
                .body("errors", notNullValue())
                .body(containsString("options valids are [Sim, Nao] [option]"))
                .body(containsString("option is required [Sim, Nao] [option]"))
                .body("errors[0].httpStatus", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("errors[0].error", equalTo("ConstraintViolationException"))
        ;
    }

    @Test
    @DisplayName("falha ao criar uma voto com um cpf maior q 11 caracteres")
    public void createVotesValidateCpfSizeGreaterElevenCharsFail() {

        var request = VoteRequest.builder()
                .cpf("002.533.610-01")
                .option("Sim")
                .birthday("29/07/1979")
                .scheduleCode(TestUtils.randomUUID())
                .build();

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(notNullValue())
                .body("errors", notNullValue())
                .body(containsString("this field size required 11 characters [cpf]"))
                .body("errors[0].httpStatus", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("errors[0].error", equalTo("ConstraintViolationException"))
        ;
    }

    @Test
    @DisplayName("falha ao criar uma voto com um cpf menor que 11 caracteres")
    public void createVotesValidateCpfSizeSmallerElevenCharsFail() {

        var request = VoteRequest.builder()
                .cpf("0023301")
                .option("Sim")
                .birthday("29/07/1979")
                .scheduleCode(TestUtils.randomUUID())
                .build();

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(notNullValue())
                .body("errors", notNullValue())
                .body(containsString("this field size required 11 characters [cpf]"))
                .body(containsString("invalid Brazilian individual taxpayer registry number (CPF) [cpf]"))
                .body("errors[0].httpStatus", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("errors[0].error", equalTo("ConstraintViolationException"))
        ;
    }

    @Test
    @DisplayName("falha ao criar uma voto sem um CPF")
    public void createVotesValidateWithoutCpfFail() {

        var request = VoteRequest.builder()
                .scheduleCode(TestUtils.randomUUID())
                .option("Nao")
                .birthday("29/07/1979")
                .build();

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(notNullValue())
                .body("errors", notNullValue())
                .body("errors[0].message", equalTo("cpf is required [cpf]"))
                .body("errors[0].httpStatus", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("errors[0].error", equalTo("ConstraintViolationException"))
        ;
    }

    @Test
    @DisplayName("falha ao criar uma voto sem uma data de nascimento")
    public void createVotesValidateWithoutBirthdayFail() {

        var request = VoteRequest.builder()
                .scheduleCode(TestUtils.randomUUID())
                .option("Nao")
                .cpf("55213866026")
                .build();

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(notNullValue())
                .body("errors", notNullValue())
                .body(containsString("this field accept format date DD/MM/YYYY [birthday]"))
                .body(containsString("this field cannot be empty, format DD/MM/YYYY [birthday]"))
                .body("errors[0].httpStatus", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("errors[0].error", equalTo("ConstraintViolationException"))
        ;
    }

    @Test
    @DisplayName("falha ao criar uma voto com uma data de nascimento no formato diferente do exigido")
    public void createVotesValidateWithBirthdayInvalidPatternFormatFail() {

        var request = VoteRequest.builder()
                .scheduleCode(TestUtils.randomUUID())
                .option("Nao")
                .birthday("29-07-1979")
                .cpf("55213866026")
                .build();

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(notNullValue())
                .body("errors", notNullValue())
                .body(containsString("this field accept format date DD/MM/YYYY [birthday]"))
                .body("errors[0].httpStatus", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("errors[0].error", equalTo("ConstraintViolationException"))
        ;
    }

    @Test
    @DisplayName("falha ao criar uma voto sem um CPF válido")
    public void createVotesValidateCpfFail() {

        var request = VoteRequest.builder()
                .scheduleCode(TestUtils.randomUUID())
                .cpf("12365478912")
                .option("Nao")
                .birthday("29/07/1979")
                .build();

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(notNullValue())
                .body("errors", notNullValue())
                .body("errors[0].message", equalTo("invalid Brazilian individual taxpayer registry number (CPF) [cpf]"))
                .body("errors[0].httpStatus", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("errors[0].error", equalTo("ConstraintViolationException"))
        ;
    }

    @Test
    @DisplayName("falha ao criar um voto sem um scheduleCode válido")
    public void createVotesInvalidScheduleCodeFail() {

        var request = VoteRequest.builder()
                .scheduleCode(TestUtils.randomText(5))
                .cpf("00253361001")
                .birthday("29/07/1979")
                .option("Nao")
                .build();

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(notNullValue())
                .body("errors", notNullValue())
                .body("errors[0].message", equalTo("Invalid UUID code [scheduleCode]"))
                .body("errors[0].httpStatus", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("errors[0].error", equalTo("ConstraintViolationException"))
        ;
    }

    @Test
    @DisplayName("falha ao criar um voto para um cpf válido mas não encontrado na receita federal")
    public void createVotesCpfNotFoundOnReceitaFederalFail() {

        var request = VoteRequest.builder()
                .scheduleCode(TestUtils.randomUUID())
                .cpf("13772988091")
                .birthday("29/07/1979")
                .option("Nao")
                .build();

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(notNullValue())
                .body("message", equalTo("this cpf not exists on brazilian receita federal"))
                .body("httpStatus", equalTo(HttpStatus.NOT_FOUND.value()))
                .body("error", equalTo("UserNotFoundException"))
        ;
    }

    @Test
    @DisplayName("falha ao criar um voto com uma opção de voto inválido")
    public void createVoteInvalidOptionFail() {

        var request = VoteRequest.builder()
                .scheduleCode(TestUtils.randomUUID())
                .cpf("00253361001")
                .birthday("29/07/1979")
                .option("AAA")
                .build();

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(notNullValue())
                .body("errors", notNullValue())
                .body("errors[0].message", equalTo("options valids are [Sim, Nao] [option]"))
                .body("errors[0].httpStatus", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("errors[0].error", equalTo("ConstraintViolationException"))
        ;
    }

    @Test
    @DisplayName("falha ao criar um voto para uma pauta da qual já votou")
    @Sql("/sql/vote-insert-to-cpf-00253361001.sql")
    public void ifThereAreAlreadyVotesOfThisCfpForThisSchedulePreviouslyFails() {
        var request = VoteRequest.builder()
                .scheduleCode("8de44aec-d624-44d7-b14b-d342fc0bf142")
                .cpf("26829510074")
                .birthday("29/07/1979")
                .option("Sim")
                .build();

        mockCalendar(2020,07,29,21);

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON)
                .body(request)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .body(notNullValue())
                .body("message", equalTo("this vote already exists"))
                .body("httpStatus", equalTo(HttpStatus.CONFLICT.value()))
                .body("error", equalTo("VoteAlreadyExistsException"))
        ;
    }

    @Test
    @DisplayName("vota em uma pauta com sucesso")
    @Sql("/sql/schedule-insert-7.sql")
    public void createVotesSuccess(){

        var request = VoteRequest.builder()
                .scheduleCode("8de44aec-d624-44d7-b14b-d342fc0bf147")
                .cpf("26829510074")
                .birthday("29/07/1979")
                .option("Sim")
                .build();

        mockCalendar(2020,07,29,10);

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .body(request)
                .contentType(V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("scheduleCode", equalTo("8de44aec-d624-44d7-b14b-d342fc0bf147"))
                .body("optionSelected", equalTo("Sim"))
                .body("voteDate", notNullValue())
        ;

    }

    @Test
    @DisplayName("busca todas os votos de uma pauta com sucesso")
    @Sql("/sql/votes-insert-to-a-schedule.sql")
    public void getAllVotesFromAScheduleSuccess(){

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .queryParam("scheduleCode", "8de44aec-d624-44d7-b14b-d342fc0bf1d9")
                .contentType(V1MediaType.APPLICATION_VND_SICRED_APP_V_1_JSON)
                .when()
                .get(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(notNullValue())
                .body("content", notNullValue())
                .body("content[0].scheduleCode", notNullValue())
                .body("content[0].optionSelected", notNullValue())
                .body("content[0].voteDate", notNullValue())
        ;

    }

}
