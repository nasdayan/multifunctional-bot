package com.github.nasdayan.trackbot.config;

import com.github.nasdayan.trackbot.bot.ability.Ability;
import com.github.nasdayan.trackbot.bot.Bot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

@Configuration
@PropertySource("classpath:application.properties")
public class BotConfiguration {
    @Value("${bot.token}")
    private String token;
    @Value("${bot.name}")
    private String name;

    @Bean
    public Bot bot(List<Ability> abilities) {
        return new Bot(token, name, abilities);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(Bot bot) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
        return telegramBotsApi;
    }
}
