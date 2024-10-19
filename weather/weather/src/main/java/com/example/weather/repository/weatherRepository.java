package com.example.weather.repository;

import com.example.weather.model.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface weatherRepository extends JpaRepository<WeatherEntity,Long> {
    List<WeatherEntity> findByCityAndDate(String city, LocalDate date);
}
