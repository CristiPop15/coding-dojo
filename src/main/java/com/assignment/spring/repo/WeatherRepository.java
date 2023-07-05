package com.assignment.spring.repo;

import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository
        extends CrudRepository<WeatherEntity, Integer> {
}
