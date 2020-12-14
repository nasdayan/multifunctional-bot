package com.github.nasdayan.trackbot.bot;

import com.github.nasdayan.trackbot.bot.ability.Ability;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
    private final String token;
    private final String userName;
    private final List<Ability> abilities;

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        for (Ability ability : abilities) {
            if (ability.conditions(update)) {
                ability.actions(update, this);
            }
        }
    }

    public void sendMsg(String chatId, String msg) {
        SendMessage sendMessage = new SendMessage(chatId, msg);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения");
        }
    }
}
