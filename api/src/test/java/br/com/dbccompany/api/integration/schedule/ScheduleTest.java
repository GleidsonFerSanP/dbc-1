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

@DisplayName("testes de integração da pauta")
public class ScheduleTest extends IntegrationBaseTest {

    private static final String BASE_URL = "/pautas";

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
}
