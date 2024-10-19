package com.example.weather.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Weather_data")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private LocalDate date;
    private double averageTemp;
    private double maxTemp;
    private double minTemp;
    private String dominantWeather;
    public WeatherEntity(String city, LocalDate date, double averageTemp, double maxTemp, double minTemp, String dominantWeather) {
        this.city = city;
        this.date = date;
        this.averageTemp = averageTemp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.dominantWeather = dominantWeather;
    }
}
