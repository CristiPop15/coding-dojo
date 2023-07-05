package com.assignment.spring.api.controller;

import com.assignment.spring.api.dto.WeatherDTO;
import com.assignment.spring.service.WeatherService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather/{city}")
    public WeatherDTO weather(@PathVariable @NotBlank @Size(max = 15, min = 3) String city) {
        return weatherService.getWeather(city);
    }
}
