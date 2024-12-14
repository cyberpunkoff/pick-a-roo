package edu.java.bot.clients.scrapper;

import edu.java.bot.clients.retry.RetryFilterFactory;
import edu.java.bot.configuration.RetryConfiguration;
import edu.java.bot.model.PollDto;
import edu.java.bot.model.PollVoteRequest;
import edu.java.bot.model.CreatePollRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Component
public class WebClientPollClient implements PollClient {
    private final static String DEFAULT_BASE_URL = "http://127.0.0.1:8080";
    private final static String POLL_ENDPOINT = "/poll";
    private final static String POLLS_ENDPOINT = "/polls";
    private final static String SPECIFIC_POLL_ENDPOINT = "/poll/{id}";
    private final static String POLL_VOTE_ENDPOINT = "/poll/{id}/vote";
    private final static String REGISTER_ENDPOINT = "/user";
    private final static String TG_CHAT_HEADER = "Tg-Chat-Id";
    private final WebClient webClient;

    @Autowired
    public WebClientPollClient(RetryConfiguration retryConfiguration) {
        this(retryConfiguration, DEFAULT_BASE_URL);
    }

    public WebClientPollClient(RetryConfiguration retryConfiguration, String baseUrl) {
        this.webClient = WebClient.builder()
            .filter(RetryFilterFactory.createFilter(RetryFilterFactory.createRetry(
                "scrapper",
                retryConfiguration
            )))
            .baseUrl(baseUrl).build();
    }

    @Override
    public void register(Long tgChatId, RegisterRequest request) {
        webClient.post()
            .uri(REGISTER_ENDPOINT)
            .header(TG_CHAT_HEADER, tgChatId.toString())
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }

    @Override
    public void createPoll(Long tgChatId, CreatePollRequest request) {
        webClient.post()
            .uri(POLL_ENDPOINT)
            .header(TG_CHAT_HEADER, tgChatId.toString())
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }

    @Override
    public List<PollPreviewResponse> getPolls(Long tgChatId) {
        return webClient.get()
            .uri(POLLS_ENDPOINT)
            .header(TG_CHAT_HEADER, tgChatId.toString())
            .retrieve()
            .bodyToFlux(PollPreviewResponse.class)
            .collectList()
            .block();
    }

    @Override
    public PollDto getPollInfo(Long tgChatId, Integer pollIndex) {
        return webClient.get()
            .uri(SPECIFIC_POLL_ENDPOINT, pollIndex)
            .header(TG_CHAT_HEADER, tgChatId.toString())
            .retrieve()
            .bodyToMono(PollDto.class)
            .block();
    }

    @Override
    public void voteInPoll(Long tgChatId, Integer pollIndex, PollVoteRequest request) {
        webClient.post()
            .uri(POLL_VOTE_ENDPOINT, pollIndex)
            .header(TG_CHAT_HEADER, tgChatId.toString())
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }
}
