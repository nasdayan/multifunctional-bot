package com.github.nasdayan.trackbot.bot.ability;

import com.github.nasdayan.trackbot.bot.Bot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class PongAbility implements Ability {

    @Override
    public boolean conditions(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().equals("ping");
    }

    @Override
    public void actions(Update update, Bot bot) {
        bot.sendMsg(update.getMessage().getChatId().toString(), "pong");
    }
}
