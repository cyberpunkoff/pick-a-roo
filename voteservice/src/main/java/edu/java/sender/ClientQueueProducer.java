package edu.java.sender;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.NewPollEvent;
import edu.java.dto.PollPreviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class ClientQueueProducer implements UpdateSender {
    private final KafkaTemplate<String, PollPreviewDto> kafkaTemplate;
    private final ApplicationConfig config;

    @Override
    public void send(PollPreviewDto update) {
        kafkaTemplate.send(config.topic(), update);
    }
}
