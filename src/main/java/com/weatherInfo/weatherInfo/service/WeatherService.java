package com.weatherInfo.weatherInfo.service;

import com.weatherInfo.weatherInfo.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class WeatherService {

    @Autowired
    private CityCordinates cityCordinates;

    public String getWeatherData(String cityName) {

        City cityData = cityCordinates.getCityCordinates(cityName);

        String getWeatherURL = String.format("https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current_weather=true",
                cityData.getLatitude(), cityData.getLongitude());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getWeatherURL))
                .GET()
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        return response.body();
    }
}
