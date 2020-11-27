package com.github.nasdayan.trackbot.bot.ability;

import com.github.nasdayan.trackbot.bot.Bot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "pong");
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения");
        }
    }
}
