package edu.java.bot.command;

import edu.java.bot.service.PollService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class PollsCommandTest extends AbstractCommandTest {
    @InjectMocks
    private PollsCommand pollsCommand;
    @Mock
    private PollService pollService;

    @Test
    void testTrackInvalidUrl() {
        when(message.text()).thenReturn("/track invalid url");

        String startCommandMessage = pollsCommand.handle(update).getParameters().get("text").toString();

        assertThat(startCommandMessage).isEqualTo("Invalid format! Please use /track <url>");
    }

    @Test
    void testTrackValidUrl() {
        when(message.text()).thenReturn("/track https://github.com/octocat/Hello-World");

        String startCommandMessage = pollsCommand.handle(update).getParameters().get("text").toString();

        assertThat(startCommandMessage).isEqualTo("Added your link to list!");
    }
}
