package br.com.dbccompany.api.integration;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0, stubs="classpath:/stubs/**/*.json")
@ActiveProfiles("test")
public abstract class IntegrationBaseTest {

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @BeforeAll
    public static void beforeAll(){
        RestAssuredMockMvc.with().contentType(ContentType.JSON);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    public void before(){
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }
}
