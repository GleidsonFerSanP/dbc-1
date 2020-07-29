package br.com.dbccompany.api.integration.palta;

import br.com.dbccompany.api.integration.IntegrationBaseTest;
import br.com.dbccompany.api.request.v1.PaltaRequest;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpStatus;

@DisplayName("testes de integração da palta")
public class PaltaTest extends IntegrationBaseTest {

    private static final String BASE_URL = "/paltas";

    @Test
    @DisplayName("cria uma palta com sucesso")
    public void createPaltaSuccess(){

        var request = PaltaRequest.builder()
                .build();

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .when()
                .post(BASE_URL, request)
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());

    }
}
