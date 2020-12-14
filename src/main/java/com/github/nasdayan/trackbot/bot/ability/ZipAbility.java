package com.github.nasdayan.trackbot.bot.ability;

import com.github.nasdayan.trackbot.bot.Bot;
import com.github.nasdayan.trackbot.service.ZipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RequiredArgsConstructor
@Component
public class ZipAbility implements Ability {
    private final ZipService zipService;
    private final static Random RANDOM = new Random();
    @Override
    public boolean conditions(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().startsWith("zip ");
    }

    @Override
    public void actions(Update update, Bot bot) {
        byte[] textInFile = update.getMessage().getText().substring(4).getBytes(StandardCharsets.UTF_8);
//        Date date = new Date();
        LocalDate localDate = LocalDate.now();
//        Random random = new Random();
        int monthToHex = localDate.getMonth().getValue() - 1;
        int day = localDate.getDayOfMonth();
        int fourRandomSymbols = RANDOM.nextInt(10000);
        String filename = String.format("W%1$02X%2$02d%3$04d.zip", monthToHex, day, fourRandomSymbols);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
            ZipEntry zipEntry = new ZipEntry("zippedFile.txt");
            zipEntry.setSize(textInFile.length);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(textInFile);
            zipOutputStream.closeEntry();
            zipOutputStream.finish();
            byteArrayOutputStream.close();
            zipOutputStream.close();
            bot.sendMsg(update.getMessage().getChatId().toString(), zipService.uploadFile(byteArrayOutputStream.toByteArray(), filename));
        } catch (IOException e) {
            bot.sendMsg(update.getMessage().getChatId().toString(), "Ошибка с файлом");
        }
    }
}
