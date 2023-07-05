package com.assignment.spring.api.client;

import com.assignment.spring.api.client.config.OpenWeatherClientConfig;
import com.assignment.spring.api.dto.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class OpenWeatherClient {

    private final RestTemplate restTemplate;
    private final OpenWeatherClientConfig openWeatherClientConfig;

    public OpenWeatherClient(RestTemplate restTemplate, OpenWeatherClientConfig openWeatherClientConfig) {
        this.restTemplate = restTemplate;
        this.openWeatherClientConfig = openWeatherClientConfig;
    }

    public WeatherResponse getWeatherForCity(String city) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(openWeatherClientConfig.getUrl())
                .queryParam("q", city)
                .queryParam("APPID", openWeatherClientConfig.getAppId())
                .encode()
                .toUriString();

        return restTemplate
                .getForEntity(urlTemplate, WeatherResponse.class)
                .getBody();
    }
}
