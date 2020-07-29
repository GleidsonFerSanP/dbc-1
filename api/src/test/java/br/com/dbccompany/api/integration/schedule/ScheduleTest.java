package br.com.dbccompany.api.integration.schedule;

import br.com.dbccompany.api.integration.IntegrationBaseTest;
import br.com.dbccompany.api.resource.mediatype.V1MediaType;
import br.com.dbccompany.api.resource.request.v1.ScheduleRequest;
import br.com.dbccompany.api.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("testes de integração da pauta")
public class ScheduleTest extends IntegrationBaseTest {

    private static final String BASE_URL = "/schedules";

    @Test
    @DisplayName("falha ao realizar um POST sem body")
    public void createScheduleValidateEmptyBodyFail(){

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
    public void createScheduleValidateMediaTypeBodyFail(){

        var request = ScheduleRequest.builder()
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
    @DisplayName("falha ao criar uma pauta sem titulo")
    public void createScheduleValidateTitleFail(){

        var request = ScheduleRequest.builder()
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
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("cria uma pauta com sucesso")
    public void createScheduleSuccess(){

        var request = ScheduleRequest.builder()
                .title(TestUtils.randomText(10))
                .build();

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .body(request)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());

    }

    @Test
    @DisplayName("busca todas as pautas com sucesso")
    @Sql("/sql/delete-all-schedules.sql")
    @Sql("/sql/schedules-inserts.sql")
    public void getAllScheduleSuccess(){

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL)
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(notNullValue())
                .body("content", notNullValue())
                .body("content[0].code", notNullValue())
                .body("content[0].title", equalTo("title"))
                .body("content[0].expiration", notNullValue())
        ;

    }

    @Test
    @DisplayName("busca uma pauta usando um code inválido")
    public void findByCodeScheduleInvalidCode(){

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL+"/"+TestUtils.randomText(5))
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(notNullValue())
                .body("message", equalTo("this code not is valid"))
                .body("httpStatus", equalTo(HttpStatus.BAD_REQUEST.value()))
                .body("error", equalTo("InvalidCodeException"))
        ;

    }

    @Test
    @DisplayName("busca uma pauta que não existe pelo code")
    public void findByCodeScheduleNotFound(){

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL+"/"+TestUtils.randomUUID())
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("schedule not found by code"))
                .body("httpStatus", equalTo(HttpStatus.NOT_FOUND.value()))
                .body("error", equalTo("NotFoundException"))
        ;
    }

    @Test
    @DisplayName("busca uma pauta com sucesso")
    @Sql("/sql/delete-all-schedules.sql")
    @Sql("/sql/schedule-insert.sql")
    public void findByCodeScheduleSuccess(){

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL.concat("/8de44aec-d624-44d7-b14b-d342fc0bf14e"))
                .then()
                .log().body().and()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(notNullValue())
                .body("code", equalTo("8de44aec-d624-44d7-b14b-d342fc0bf14e"))
                .body("title", equalTo("title"))
                .body("expiration", equalTo("2020-07-29T19:29:50.684+00:00"))
        ;

    }
}
