package edu.java.bot.clients.scrapper;

import lombok.Data;
import lombok.Getter;
import java.util.List;

@Data
@Getter
public class PollPreviewResponse {
    private String title;
    private Boolean isOwner;
}
