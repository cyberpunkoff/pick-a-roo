package edu.java.sender;

import edu.java.dto.PollPreviewDto;

public interface UpdateSender {
    void send(PollPreviewDto update);
}
