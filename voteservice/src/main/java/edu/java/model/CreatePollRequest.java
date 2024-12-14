package edu.java.model;

import lombok.Data;
import java.util.List;

@Data
public class CreatePollRequest {
    private String title;
    private List<String> options;
}
