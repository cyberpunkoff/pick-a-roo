package edu.java.dto;

import lombok.Data;
import java.util.List;

@Data
public class PollDto {
    private String title;
    private List<OptionDto> options;
}
