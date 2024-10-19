package com.example.weather.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WeatherAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private LocalDate date;
    private String condition;
    private String alertMessage;

    public WeatherAlert(String city, LocalDate date, String condition, String alertMessage) {
        this.city = city;
        this.date = date;
        this.condition = condition;
        this.alertMessage = alertMessage;
    }

}
