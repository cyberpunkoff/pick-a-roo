package edu.java.service;

import edu.java.dto.OptionDto;
import edu.java.dto.PollDto;
import edu.java.model.CreatePollRequest;
import edu.java.dto.PollPreviewDto;
import edu.java.dto.PollState;
import static edu.java.dto.PollState.CLOSED;
import edu.java.entity.OptionEntity;
import edu.java.entity.PollEntity;
import edu.java.entity.VoteEntity;
import edu.java.model.PollVoteRequest;
import edu.java.repository.OptionRepository;
import edu.java.repository.PollRepository;
import edu.java.repository.UserRepository;
import edu.java.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PollService {
    private final PollRepository pollRepository;
    private final VoteRepository voteRepository;
    private final OptionRepository optionRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createPoll(CreatePollRequest createPollRequest, Long userId) {
        var user = userRepository.findById(userId).orElseThrow();

        var poll = new PollEntity();
        poll.setTitle(createPollRequest.getTitle());
        poll.setOwner(user);

        createPollRequest.getOptions().stream().map(OptionEntity::new).forEach(poll::addToOptions);

        pollRepository.save(poll);
        optionRepository.saveAll(poll.getOptions());
    }

    public List<PollPreviewDto> getPolls(Long userId) {
        var polls = pollRepository.findAll();
        List<PollPreviewDto> result = new ArrayList<>();

        for (var poll : polls) {
            result.add(new PollPreviewDto(
                poll.getTitle(),
                poll.getOwner().getId().equals(userId)
            ));
        }

        return result;
    }

    @Transactional
    public void updatePoll(PollState newState, Long userId, Integer pollIndex) {
        if (newState != CLOSED) {
            return;
        }

        var poll = pollRepository.findAllByOrderByIdAsc().get(pollIndex);

        if (!poll.getOwner().getId().equals(userId)) {
            throw new RuntimeException();
        }

        poll.setState(CLOSED);
    }

    public void deletePoll(Long userId, Integer pollIndex) {
        var poll = pollRepository.findAllByOrderByIdAsc().get(pollIndex);

        if (!poll.getOwner().getId().equals(userId)) {
            throw new RuntimeException();
        }

        pollRepository.delete(poll);
    }

    @Transactional
    public void vote(Integer pollIndex, Long userId, PollVoteRequest voteRequest) {
        var poll = pollRepository.findAllByOrderByIdAsc().get(pollIndex);
        var option = optionRepository.findAllByPollOrderByIdAsc(poll).get(voteRequest.getOption());
        var user = userRepository.findById(userId).orElseThrow();

        var foundVote = voteRepository.findByOption(option);
        if (!foundVote.isEmpty()) {
            throw new RuntimeException();
        }

        foundVote = voteRepository.findByOwner(user);
        if (foundVote.stream().filter(e -> e.getOption().getPoll().equals(poll)).findAny().isPresent()) {
            throw new RuntimeException();
        }

        var vote = new VoteEntity();
        vote.setOption(option);
        option.setVote(vote);
        vote.setOwner(user);

        voteRepository.save(vote);
    }

    public PollDto getPollInfo(Integer pollIndex) {
        var poll = pollRepository.findAllByOrderByIdAsc().get(pollIndex);
        var result = new PollDto();
        result.setTitle(poll.getTitle());

        List<OptionDto> options = new ArrayList<>();
        for (var optionEntity : poll.getOptions().stream().sorted(Comparator.comparingInt(OptionEntity::getId))
            .toList()) {
            var optionDto = OptionDto.builder()
                .option(optionEntity.getValue());

            if (optionEntity.getVote() != null) {
                optionDto.isTaken(true);
                optionDto.firstname(optionEntity.getVote().getOwner().getFirstname());
                optionDto.lastname(optionEntity.getVote().getOwner().getSurname());
            } else {
                optionDto.isTaken(false);
            }

            options.add(optionDto.build());
        }
        result.setOptions(options);
        return result;
    }
}
