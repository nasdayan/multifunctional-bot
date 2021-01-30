package com.github.nasdayan.trackbot.bot.ability;

import com.github.nasdayan.trackbot.bot.Bot;
import com.github.nasdayan.trackbot.exception.CityNotFoundException;
import com.github.nasdayan.trackbot.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.MessageFormat;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeatherAbility implements Ability {

    private final WeatherService weatherService;

    @Override
    public boolean conditions(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().matches("weather\\s[A-Za-zА-Яа-я\\-]+");
    }

    @Override
    public void actions(Update update, Bot bot) {
        String city = update.getMessage().getText().replace("weather ", "");
        String chatId = update.getMessage().getChatId().toString();
        try {
            bot.sendMsg(chatId, MessageFormat.format("Сейчас в городе {0} {1} градус(a, ов)", StringUtils.capitalize(city.toLowerCase()), weatherService.getTemperature(city)));
        } catch (CityNotFoundException e) {
            bot.sendMsg(chatId, "Нет такого города");
        }
    }
}
