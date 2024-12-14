package edu.java.bot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class CreatePollRequest {
    private String title;
    private List<String> options;
}
