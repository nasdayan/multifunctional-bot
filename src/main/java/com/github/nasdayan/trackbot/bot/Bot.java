package com.github.nasdayan.trackbot.bot;

import com.github.nasdayan.trackbot.bot.ability.Ability;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

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
}
