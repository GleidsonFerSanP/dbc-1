package com.sicred.integration.palta;

import com.sicred.integration.IntegrationBaseTest;
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

        RestAssuredMockMvc.given()
                .webAppContextSetup(webApplicationContext)
                .when()
                .post(BASE_URL)
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());

    }
}
