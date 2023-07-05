package com.assignment.spring.api.client;

import com.assignment.spring.api.client.config.OpenWeatherClientConfig;
import com.assignment.spring.api.dto.Main;
import com.assignment.spring.api.dto.Sys;
import com.assignment.spring.api.dto.WeatherResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.mockito.Mockito.when;

public class OpenWeatherClientTest {

    private final String DUMMY_URL = "http://api.openweathermap.org/data/2.5/weather";
    private final String DUMMY_API_ID = "dummy api id";
    private RestTemplate restTemplate;
    private OpenWeatherClientConfig config;
    private OpenWeatherClient client;

    @Before
    public void init() {
        restTemplate = Mockito.mock(RestTemplate.class);
        config = new OpenWeatherClientConfig(DUMMY_URL, DUMMY_API_ID);

        client = new OpenWeatherClient(restTemplate, config);
    }

    @Test
    public void getWeatherForCityTest() {
        String city = "London";
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(DUMMY_URL)
                .queryParam("q", city)
                .queryParam("APPID", DUMMY_API_ID)
                .encode()
                .toUriString();

        WeatherResponse body = new WeatherResponse();
        body.setMain(new Main());
        body.setSys(new Sys());
        body.setName(city);

        when(restTemplate.getForEntity(urlTemplate, WeatherResponse.class)).thenReturn(ResponseEntity.ok(body));

        WeatherResponse weatherResponse = client.getWeatherForCity(city);

        Assertions.assertNotNull(weatherResponse);
    }
}
