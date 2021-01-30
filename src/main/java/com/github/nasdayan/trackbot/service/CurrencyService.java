package com.github.nasdayan.trackbot.service;

import com.github.nasdayan.trackbot.bot.entity.Currency;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class CurrencyService {

    @Value("${currate.token}")
    private String currateToken;

    public Currency getRate() throws IOException {
        final String usdToRubJsonObjectKey = "USDRUB";
        final String euroToRubJsonObjectKey = "EURRUB";
        final URL url = new URL("http://currate.ru/api/?get=rates&pairs=USDRUB,EURRUB&key=" + currateToken);
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try (InputStream inputStream = connection.getInputStream()) {
            String response = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsonObjectMain = jsonObject.getJSONObject("data");
            return Currency.builder()
                    .usdToRub(jsonObjectMain.getString(usdToRubJsonObjectKey))
                    .euroToRub(jsonObjectMain.getString(euroToRubJsonObjectKey))
                    .build();
        }
    }
}
