package edu.java.bot.command;

import edu.java.bot.service.PollService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class PollCommandTest extends AbstractCommandTest {
    @InjectMocks
    private PollCommand pollCommand;
    @Mock
    private PollService pollService;

    @Test
    void testUntrackIncorrectIndex() {
        when(message.text()).thenReturn("/untrack invalid");

        String startCommandMessage = pollCommand.handle(update).getParameters().get("text").toString();

        assertThat(startCommandMessage).isEqualTo("Invalid index");
    }

    @Test
    void testUntrackNonExistentIndex() {
        when(message.text()).thenReturn("/untrack 5");

        String startCommandMessage = pollCommand.handle(update).getParameters().get("text").toString();

        assertThat(startCommandMessage).isEqualTo("Index not found");
    }
}
