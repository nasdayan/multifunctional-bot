package com.github.nasdayan.trackbot;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TrackBotApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.bannerMode(Banner.Mode.OFF)
				.sources(TrackBotApplication.class)
				.run(args);
	}

}
