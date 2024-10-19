package com.example.weather.Service;

import com.example.weather.model.WeatherAlert;
import com.example.weather.repository.WeatherAlertRepository;
import com.example.weather.repository.weatherRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private weatherRepository weatherRepository;

    @Autowired
    private WeatherAlertRepository weatherAlertRepository;

    public JSONObject fetchWeatherData(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        return new JSONObject(response);
    }

    public double convertKelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    public String getDominantWeatherCondition(JSONArray weatherArray) {
        return weatherArray.getJSONObject(0).getString("main");
    }

    public void checkAndTriggerAlert(String city, double thresholdTemp, double currentTemp, String dominantWeather) {
        List<WeatherAlert> existingAlerts = weatherAlertRepository.findByCity(city);

        // Alerting logic - trigger alert if current temp exceeds threshold for 2 consecutive updates
        if (existingAlerts.size() >= 2 && existingAlerts.get(existingAlerts.size() - 1).getCondition().equals("High Temperature")) {
            return;
        }

        if (currentTemp > thresholdTemp) {
            WeatherAlert alert = new WeatherAlert(city, LocalDate.now(), "High Temperature",
                    "Temperature exceeds " + thresholdTemp + "°C for " + city + " with current temp: " + currentTemp + "°C.");
            weatherAlertRepository.save(alert);
        }
    }
}