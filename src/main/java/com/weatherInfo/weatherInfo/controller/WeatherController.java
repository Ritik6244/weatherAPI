package com.weatherInfo.weatherInfo.controller;

import com.weatherInfo.weatherInfo.entity.City;
import com.weatherInfo.weatherInfo.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @GetMapping("/fetchWeather/{cityName}")
    public ResponseEntity<?> getWeather(@PathVariable String cityName){
        String details = weatherService.getWeatherData(cityName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(details);
    }

    @GetMapping("/check")
    public ResponseEntity<?> requestChecker(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Your controller is working perfectly...");
    }
}
