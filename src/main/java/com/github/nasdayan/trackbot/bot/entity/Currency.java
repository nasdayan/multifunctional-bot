package com.github.nasdayan.trackbot.bot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class Currency {
    private final String usdToRub;
    private final String euroToRub;
}
