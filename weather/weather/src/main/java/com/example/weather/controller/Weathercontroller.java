package com.example.weather.controller;

import com.example.weather.Service.WeatherService;
import com.example.weather.model.WeatherAlert;
import com.example.weather.model.WeatherEntity;
import com.example.weather.repository.WeatherAlertRepository;
import com.example.weather.repository.weatherRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/weather")
public class Weathercontroller {
    @Autowired
    private WeatherService weatherService;

    @Autowired
    private weatherRepository weatherRepository;

    @Autowired
    private WeatherAlertRepository weatherAlertRepository;

    private Double alertThresholdTemp = 35.0; // Default threshold

    @PostMapping("/daily-summary")
    public WeatherEntity getDailySummary(@RequestParam String city, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<WeatherEntity> summaries = weatherRepository.findByCityAndDate(city, date);
        if (!summaries.isEmpty()) {
            return summaries.get(0);
        }

        JSONObject weatherData = weatherService.fetchWeatherData(city);
        double tempInKelvin = weatherData.getJSONObject("main").getDouble("temp");
        double tempInCelsius = weatherService.convertKelvinToCelsius(tempInKelvin);
        double maxTempInCelsius = weatherService.convertKelvinToCelsius(weatherData.getJSONObject("main").getDouble("temp_max"));
        double minTempInCelsius = weatherService.convertKelvinToCelsius(weatherData.getJSONObject("main").getDouble("temp_min"));
        String dominantWeather = weatherService.getDominantWeatherCondition(weatherData.getJSONArray("weather"));

        WeatherEntity summary = new WeatherEntity(city, date, tempInCelsius, maxTempInCelsius, minTempInCelsius, dominantWeather);
        weatherRepository.save(summary);

        weatherService.checkAndTriggerAlert(city, alertThresholdTemp, tempInCelsius, dominantWeather);

        return summary;
    }

    @Scheduled(fixedRateString = "${weather.update.interval}")
    public void fetchAndStoreWeatherDataForCities() {
        String[] cities = {"Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad"};
        for (String city : cities) {
            LocalDate today = LocalDate.now();
            getDailySummary(city, today);
        }
    }

    @PostMapping("/set-alert-threshold")
    public String setAlertThreshold(@RequestParam Double thresholdTemp) {
        this.alertThresholdTemp = thresholdTemp;
        return "Alert threshold set to " + thresholdTemp + "°C.";
    }

    @GetMapping("/alerts")
    public List<WeatherAlert> getAlerts(@RequestParam String city) {

        return weatherAlertRepository.findByCity(city);
    }

    @GetMapping("/summaries")
    public List<WeatherEntity> getWeatherSummaries(@RequestParam String city, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return weatherRepository.findByCityAndDate(city, date);
    }

}
