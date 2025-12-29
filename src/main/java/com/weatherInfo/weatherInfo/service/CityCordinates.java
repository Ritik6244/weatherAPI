package com.weatherInfo.weatherInfo.service;

import com.weatherInfo.weatherInfo.entity.City;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class CityCordinates {

    public City getCityCordinates(String cityName) {
        City city = new City();
        city.setName(cityName);
        String cordinationAPI = String.format("https://nominatim.openstreetmap.org/search?q=%s&format=json&limit=1", cityName);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(cordinationAPI))
                .GET()
                .build();
        HttpResponse response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        HashMap<String, Double> mappedData = (HashMap<String, Double>) parseCoordinates(response.body().toString());
        city.setLatitude(mappedData.get("latitude"));
        city.setLongitude(mappedData.get("longitude"));
        return city;
    }

    public Map<String, Double> parseCoordinates(String responseBody){
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(responseBody);
        JsonNode location = root.get(0);

        double latitude = location.path("lat").asDouble();
        double longitude = location.path("lon").asDouble();

        HashMap<String, Double> mappedData = new HashMap<>();
        mappedData.put("latitude", latitude);
        mappedData.put("longitude", longitude);

        return mappedData;
    }

}
