package edu.java.model;

import lombok.Data;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Data
@Setter
public class PollVoteRequest {
    private Integer option;
}
