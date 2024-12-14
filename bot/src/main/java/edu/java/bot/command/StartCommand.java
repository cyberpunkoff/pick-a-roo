package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.scrapper.PollClient;
import edu.java.bot.clients.scrapper.RegisterRequest;
import edu.java.bot.service.PollService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class StartCommand extends AbstractCommand {
    private final PollService pollService;
    public static final String COMMAND = "/start";
    public static final String DESCRIPTION = "Запуск бота";
    public static final String MESSAGE = "This bot allows you to track updated "
        + "on your links! Use /help to get started.";

    StartCommand(PollService pollService) {
        super(COMMAND, DESCRIPTION);
        this.pollService = pollService;
    }

    @Override
    public SendMessage handle(Update update) {
        logMessage(update);
        try {
            var request = new RegisterRequest(
                update.message().chat().firstName(),
                update.message().chat().lastName()
            );

            pollService.register(update.message().chat().id(), request);
            return new SendMessage(update.message().chat().id(), MESSAGE);
        } catch (WebClientResponseException e) {
            return new SendMessage(update.message().chat().id(), "Вы уже зарегистрированы!");
        }
    }
}
