package com.github.nasdayan.trackbot.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class HttpConfiguration {
    @Bean
    public HttpClient httpClient() {
        return HttpClients.createDefault();
    }

    @Bean
    @Scope(value = "prototype")
    public HttpPost httpPost() {
        return new HttpPost("https://file.io");
    }
}
