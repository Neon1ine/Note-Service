package com.example.spring6.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;

/*Данный код представляет хорошо структурированную конфигурацию интеграции в Spring Framework
Он эффективен и масштабируем, однако требует улучшения в части безопасности, обработки ошибок и читаемости*/

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannel textInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel fileWriterChannel() {
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "textInputChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> mainTransformer() {
        return text -> {
           return text;
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler messageHandler() {
        /*Путь до файла лучше вывести в константную переменную и изменить,
        чтобы код работал не только на устройстве с пользователем "Diman"*/
        FileWritingMessageHandler handler =
                new FileWritingMessageHandler(new File("C:/Users/Diman/Desktop/Spring/Spring6/files"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);

        return handler;
    }
}
