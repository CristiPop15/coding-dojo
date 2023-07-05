package com.assignment.spring.service;

import com.assignment.spring.api.client.OpenWeatherClient;
import com.assignment.spring.api.dto.Main;
import com.assignment.spring.api.dto.Sys;
import com.assignment.spring.api.dto.WeatherDTO;
import com.assignment.spring.api.dto.WeatherResponse;
import com.assignment.spring.repo.WeatherEntity;
import com.assignment.spring.repo.WeatherRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class WeatherServiceTest {

    private OpenWeatherClient client;
    private WeatherRepository repository;
    private ModelMapper mapper;

    private WeatherService weatherService;

    @Before
    public void init() {
        client = Mockito.mock(OpenWeatherClient.class);
        repository = Mockito.mock(WeatherRepository.class);
        mapper = Mockito.mock(ModelMapper.class);

        weatherService = new WeatherService(repository, client, mapper);
    }

    @Test
    public void testGetWeather() {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setName("London");
        weatherResponse.setSys(new Sys());
        weatherResponse.setMain(new Main());

        WeatherDTO weatherDTO = new WeatherDTO();
        weatherDTO.setCity("London");

        when(client.getWeatherForCity("London")).thenReturn(weatherResponse);
        when(mapper.map(any(), any())).thenReturn(weatherDTO);

        WeatherDTO dto = weatherService.getWeather("London");

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(weatherDTO.getCity(), "London");
    }
}
