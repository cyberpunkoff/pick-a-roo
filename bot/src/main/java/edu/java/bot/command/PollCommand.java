package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.scrapper.PollClient;
import edu.java.bot.model.UserMessage;
import edu.java.bot.service.PollService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PollCommand extends AbstractCommand {
    public static final String COMMAND = "/poll";
    public static final String DESCRIPTION = "Получить информацию о конкретном выборе темы. Использование /poll <id>.";

    private final PollService pollService;

    PollCommand(PollClient pollClient, PollService pollService) {
        super(COMMAND, DESCRIPTION);
        this.pollService = pollService;
    }

    @Override
    public SendMessage handle(Update update) {
        logMessage(update);
        UserMessage message = CommandParser.parseMessage(update.message().text());
        Long userId = update.message().chat().id();

        if (message.getArguments() == null || message.getArguments().length != 1) {
            return new SendMessage(
                update.message().chat().id(),
                "Неправильный формат сообщения! Используйте /poll <id>."
            );
        }

        int pollId;
        try {
            pollId = Integer.parseInt(message.getArguments()[0]) - 1;
        } catch (NumberFormatException e) {
            return new SendMessage(userId, "Неправильный индекс");
        }

        try {
            var response = pollService.getPollInfo(userId, pollId);
            return new SendMessage(userId, CommandUtils.createPollsInfoMessage(response));
        } catch (Exception e) {
            log.error("Ошибка при выполнении запроса", e);
            return new SendMessage(userId, "Ошибка при получении ответа. Повторите попытку позднее.");
        }
    }
}
