package com.assignment.spring.service;

import com.assignment.spring.api.client.OpenWeatherClient;
import com.assignment.spring.api.dto.WeatherDTO;
import com.assignment.spring.api.dto.WeatherResponse;
import com.assignment.spring.repo.WeatherEntity;
import com.assignment.spring.repo.WeatherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final OpenWeatherClient openWeatherClient;
    private final ModelMapper mapper;

    public WeatherService(WeatherRepository weatherRepository,
                          OpenWeatherClient openWeatherClient,
                          ModelMapper mapper) {
        this.weatherRepository = weatherRepository;
        this.openWeatherClient = openWeatherClient;
        this.mapper = mapper;
    }

    public WeatherDTO getWeather(String city) {
        WeatherResponse response = openWeatherClient.getWeatherForCity(city);
        WeatherEntity entity = saveWeather(response);
        return mapResponse(entity);
    }

    private WeatherEntity saveWeather(WeatherResponse response) {
        WeatherEntity entity = new WeatherEntity();
        entity.setCity(response.getName());
        entity.setCountry(response.getSys() == null ? null : response.getSys().getCountry());
        entity.setTemperature(response.getMain() == null ? null : response.getMain().getTemp());

        return weatherRepository.save(entity);
    }

    private WeatherDTO mapResponse(WeatherEntity entity) {
        return mapper.map(entity, WeatherDTO.class);
    }

}
