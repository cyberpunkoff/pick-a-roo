package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.scrapper.PollClient;
import edu.java.bot.model.UserMessage;
import edu.java.bot.service.PollService;
import java.net.URI;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class PollsCommand extends AbstractCommand {
    private final PollClient pollClient;
    public static final String COMMAND = "/polls";
    public static final String DESCRIPTION = "Получить все доступные выборы тем";
    private final PollService pollService;

    PollsCommand(PollClient pollClient, PollService pollService) {
        super(COMMAND, DESCRIPTION);
        this.pollClient = pollClient;
        this.pollService = pollService;
    }

    @Override
    @SneakyThrows
    public SendMessage handle(Update update) {
        logMessage(update);
        UserMessage message = CommandParser.parseMessage(update.message().text());

        Long chatId = update.message().chat().id();
        if (message.getArguments() != null) {
            return new SendMessage(chatId, "Неправильный формат сообщения");
        }

        var response = pollClient.getPolls(chatId);
        return new SendMessage(chatId, CommandUtils.createPollsPreviewMessage(response));
    }
}
