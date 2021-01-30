package com.github.nasdayan.trackbot.bot.ability;

import com.github.nasdayan.trackbot.bot.Bot;
import com.github.nasdayan.trackbot.service.ZipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RequiredArgsConstructor
@Component
public class ZipAbility implements Ability {

    private final ZipService zipService;

    @Override
    public boolean conditions(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().startsWith("zip ");
    }

    @Override
    public void actions(Update update, Bot bot) {
        byte[] textInFile = update.getMessage().getText().substring(4).getBytes(StandardCharsets.UTF_8);
        String filename = "file.zip";
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
