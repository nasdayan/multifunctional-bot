package com.github.nasdayan.trackbot.service;

import com.github.nasdayan.trackbot.exception.CityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


@Service
@Slf4j
public class WeatherService {

    private final String url;

    public WeatherService(@Value("${owm.token}") final String owmToken) {
        url = "http://api.openweathermap.org/data/2.5/weather?lang=ru&units=metric&appid=" + owmToken + "&q=";
    }

    public int getTemperature(String city) {
        try {
            final URL url = new URL(this.url + city);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            try (InputStream inputStream = connection.getInputStream()) {
                String response = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                return jsonObjectMain.getInt("temp");
            }
        } catch (FileNotFoundException e) {
            throw new CityNotFoundException();
        } catch (IOException e) {
            log.error("Ошибка получения погоды");
            throw new RuntimeException(e);
        }
    }
}
