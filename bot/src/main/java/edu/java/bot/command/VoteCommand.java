package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.scrapper.PollClient;
import edu.java.bot.model.PollVoteRequest;
import edu.java.bot.model.UserMessage;
import edu.java.bot.service.PollService;
import org.apache.kafka.common.requests.VoteRequest;
import org.springframework.stereotype.Component;

@Component
public class VoteCommand extends AbstractCommand {
    private final PollClient pollClient;
    public static final String COMMAND = "/vote";
    public static final String DESCRIPTION = "Выбрать тему в опросе. Использование: /vote <pollId> <themeId>.";

    private final PollService pollService;

    VoteCommand(PollClient pollClient, PollService pollService) {
        super(COMMAND, DESCRIPTION);
        this.pollClient = pollClient;
        this.pollService = pollService;
    }

    @Override
    public SendMessage handle(Update update) {
        logMessage(update);
        UserMessage message = CommandParser.parseMessage(update.message().text());
        Long userId = update.message().chat().id();

        if (message.getArguments() == null || message.getArguments().length != 2) {
            return new SendMessage(
                update.message().chat().id(),
                "Неправильный формат сообщения! Пожалуйста, используйте: /vote <pollId> <themeId>."
            );
        }

        int pollIndex;
        int optionIndex;
        try {
            pollIndex = Integer.parseInt(message.getArguments()[0]) - 1;
            optionIndex = Integer.parseInt(message.getArguments()[1]) - 1;
        } catch (NumberFormatException e) {
            return new SendMessage(userId, "Неправильное значение числа");
        }

        var request = new PollVoteRequest(optionIndex);

        try {
            pollService.voteInPoll(userId, pollIndex, request);
            return new SendMessage(userId, "Записали ваш голос!");
        } catch (Exception e) {
            return new SendMessage(userId, "Возможно, вы уже проголосовали в этом опросе. Или тема занята.");
        }

    }
}
