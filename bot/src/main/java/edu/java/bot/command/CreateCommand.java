package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.model.CreatePollRequest;
import edu.java.bot.model.UserMessage;
import edu.java.bot.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateCommand extends AbstractCommand {
    public static final String COMMAND = "/create";
    public static final String DESCRIPTION = "Создание нового выбора темы";
    private final PollService pollService;

    @Autowired CreateCommand(PollService pollService) {
        super(COMMAND, DESCRIPTION);
        this.pollService = pollService;
    }

    @Override
    public SendMessage handle(Update update) {
        logMessage(update);
        UserMessage message = CommandParser.parseMessage(update.message().text());

        Long chatId = update.message().chat().id();
        if (message.getArguments() == null) {
            return new SendMessage(chatId, "Неправильный формат сообщения!");
        }

        String[] parts = update.message().text().split("\n");
        String title = Arrays.stream(parts[0].split(" ")).skip(1).collect(Collectors.joining(" "));
        List<String> options = Arrays.stream(parts).skip(1).toList();

        var request = new CreatePollRequest(title, options);

        try {
            pollService.createPoll(chatId, request);
            return new SendMessage(chatId, "Новый опрос был создан успешно!");
        } catch (Exception e) {
            return new SendMessage(chatId, "Ошибка при создании опроса. Повторите попытку позднее.");
        }
    }
}
