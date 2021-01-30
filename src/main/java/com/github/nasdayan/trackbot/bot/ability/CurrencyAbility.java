package com.github.nasdayan.trackbot.bot.ability;

import com.github.nasdayan.trackbot.bot.Bot;
import com.github.nasdayan.trackbot.bot.entity.Currency;
import com.github.nasdayan.trackbot.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CurrencyAbility implements Ability {

    private final CurrencyService currencyService;

    @Override
    public boolean conditions(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().equalsIgnoreCase("rate");
    }

    @Override
    public void actions(Update update, Bot bot) {
        String chatId = update.getMessage().getChatId().toString();
        try {
            final Currency currency = currencyService.getRate();
            bot.sendMsg(chatId, "Курс доллара к рублю: " + currency.getUsdToRub() + ";\n"
                    + "Курс евро к рублю: " + currency.getEuroToRub());
        } catch (IOException e) {
            bot.sendMsg(chatId, "Ошибка показа курса");
        }
    }
}
