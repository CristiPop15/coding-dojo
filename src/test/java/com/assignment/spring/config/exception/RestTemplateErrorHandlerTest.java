package com.assignment.spring.config.exception;

import com.assignment.spring.api.dto.WeatherResponse;
import com.assignment.spring.config.AppConfig;
import com.assignment.spring.config.RestTemplateErrorHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { AppConfig.class, RestTemplateErrorHandler.class })
public class RestTemplateErrorHandlerTest {

    @Autowired
    private RestTemplate restTemplate;
    private MockRestServiceServer server;

    @Before
    public void init() {
        server = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    public void testSuccessApiCall() {

        server.expect(once(), requestTo("/weather/London")).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        restTemplate.getForObject("/weather/London", WeatherResponse.class);

        server.verify();
    }

    @Test
    public void test4xxApiCallWithCustomException() {

        server.expect(once(), requestTo("/weather/London")).andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        Assertions.assertThrows(NotFoundException.class, () -> {
            restTemplate.getForObject("/weather/London", WeatherResponse.class);
        });
    }

    @Test
    public void test5xxApiCallWithCustomException() {
        server.expect(once(), requestTo("/weather/London")).andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        Assertions.assertThrows(ServerErrorException.class, () -> {
            restTemplate.getForObject("/weather/London", WeatherResponse.class);
        });
    }
}
