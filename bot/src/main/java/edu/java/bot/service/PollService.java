package edu.java.bot.service;

import edu.java.bot.clients.scrapper.PollClient;
import edu.java.bot.clients.scrapper.PollPreviewResponse;
import edu.java.bot.clients.scrapper.RegisterRequest;
import edu.java.bot.model.CreatePollRequest;
import edu.java.bot.model.PollDto;
import edu.java.bot.model.PollVoteRequest;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PollService {
    private final PollClient pollClient;

    public void register(Long chatId, RegisterRequest request) {
        pollClient.register(chatId, request);
    }

    public PollDto getPollInfo(Long chatId, Integer pollIndex) {
        return pollClient.getPollInfo(chatId, pollIndex);
    }

    public void createPoll(Long chatId, CreatePollRequest request) {
        pollClient.createPoll(chatId, request);
    }

    public List<PollPreviewResponse> getPolls(Long chatId, CreatePollRequest request) {
        return pollClient.getPolls(chatId);
    }

    public void voteInPoll(Long tgChatId, Integer pollIndex, PollVoteRequest request) {
        pollClient.voteInPoll(tgChatId, pollIndex, request);
    }
}
