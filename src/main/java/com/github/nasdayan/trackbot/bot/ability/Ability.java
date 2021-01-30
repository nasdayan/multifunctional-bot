package com.github.nasdayan.trackbot.bot.ability;

import com.github.nasdayan.trackbot.bot.Bot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Ability {

    /**
     * Определяет, должна ли способность реагировать на событие
     * @param update Событие
     * @return true - если должна, false - если нет
     */
    boolean conditions(Update update);

    /**
     * Реакция на событие
     * @param update Событие
     */
    void actions(Update update, Bot bot);
}
