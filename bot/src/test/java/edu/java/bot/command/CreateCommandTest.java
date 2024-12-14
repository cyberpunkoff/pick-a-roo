package edu.java.bot.command;

import edu.java.bot.service.PollService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CreateCommandTest extends AbstractCommandTest {
    @InjectMocks
    private CreateCommand createCommand;
    @Mock
    private PollService pollService;

    @Test
    void testNoLinksTrackedMessage() {
        when(message.text()).thenReturn("/track");

        String startCommandMessage = createCommand.handle(update).getParameters().get("text").toString();

        assertThat(startCommandMessage).isEqualTo("You haven't added any links yet");
    }
}
