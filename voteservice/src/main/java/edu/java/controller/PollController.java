package edu.java.controller;

import edu.java.dto.PollDto;
import edu.java.model.CreatePollRequest;
import edu.java.dto.PollPreviewDto;
import edu.java.dto.PollState;
import edu.java.model.PollVoteRequest;
import edu.java.service.PollService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.requests.VoteRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class PollController {
    private final PollService pollService;

    @PostMapping("/poll")
    public void addPoll(@RequestBody CreatePollRequest addPollRequest, @RequestHeader("Tg-Chat-Id") Long userId) {
        pollService.createPoll(addPollRequest, userId);
    }

    @GetMapping("/polls") // serve /polls path
    public List<PollPreviewDto> getPolls(@RequestHeader("Tg-Chat-Id") Long userId) {
        return pollService.getPolls(userId);
    }

    @GetMapping("/poll/{id}")
    public PollDto getPollInfo(
        @PathVariable("id") Integer pollIndex
    ) {
        return pollService.getPollInfo(pollIndex);
    }

    @PutMapping("/poll/{id}")
    public void updatePollState(
        @RequestBody PollState state,
        @RequestHeader("Tg-Chat-Id") Long userId,
        @PathVariable("id") Integer pollIndex
    ) {
        pollService.updatePoll(state, userId, pollIndex);
    }

    @DeleteMapping("/poll/{id}")
    public void deletePoll(
        @RequestHeader("Tg-Chat-Id") Long userId,
        @PathVariable("id") Integer pollIndex
    ) {
        pollService.deletePoll(userId, pollIndex);
    }

    @PostMapping("/poll/{id}/vote")
    public void vote(
        @RequestHeader("Tg-Chat-Id") Long userId,
        @PathVariable("id") Integer pollIndex,
        @RequestBody PollVoteRequest option
    ) {
        pollService.vote(pollIndex, userId, option);
    }
}
