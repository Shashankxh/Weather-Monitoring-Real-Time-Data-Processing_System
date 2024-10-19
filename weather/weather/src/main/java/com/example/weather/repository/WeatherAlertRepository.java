package com.example.weather.repository;

import com.example.weather.model.WeatherAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherAlertRepository extends JpaRepository<WeatherAlert,Long> {
    List<WeatherAlert> findByCity(String city);

}
