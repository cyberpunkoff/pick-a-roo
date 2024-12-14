package edu.java.dto;

import lombok.Builder;
import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

@Data
@Builder
public class OptionDto {
    private String option;
    private String firstname;
    private String lastname;
    private Boolean isTaken;
}
