package edu.java.model;

import edu.java.dto.PollState;
import lombok.Data;

@Data
public class UpdatePollRequest {
    private Integer index;
    private PollState pollState;
    private Long userId;
}
