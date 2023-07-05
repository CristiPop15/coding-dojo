package com.assignment.spring.api.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenWeatherClientConfig {
    private final String url;
    private final String appId;

    public OpenWeatherClientConfig(@Value("${client.rest.weather.url}") String url,
                                   @Value("${weather.app.id}")String appId) {
        this.url = url;
        this.appId = appId;
    }

    public String getUrl() {
        return url;
    }

    public String getAppId() {
        return appId;
    }
}
