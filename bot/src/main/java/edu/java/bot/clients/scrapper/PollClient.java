package edu.java.bot.clients.scrapper;

import edu.java.bot.model.CreatePollRequest;
import edu.java.bot.model.PollDto;
import edu.java.bot.model.PollVoteRequest;

import java.util.List;

public interface PollClient {
    void register(Long tgChatId, RegisterRequest request);

    void createPoll(Long tgChatId, CreatePollRequest request);

    List<PollPreviewResponse> getPolls(Long tgChatId);

    void voteInPoll(Long tgChatId, Integer pollIndex, PollVoteRequest request);

    PollDto getPollInfo(Long tgChatId, Integer pollIndex);
}
