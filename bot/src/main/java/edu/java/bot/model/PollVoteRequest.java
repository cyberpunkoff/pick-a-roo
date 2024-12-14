package edu.java.bot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PollVoteRequest {
    private Integer option;
}
