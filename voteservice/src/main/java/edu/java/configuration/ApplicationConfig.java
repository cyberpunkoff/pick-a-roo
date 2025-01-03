package edu.java.configuration;

import jakarta.validation.constraints.NotNull;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated @ConfigurationProperties(prefix = "app", ignoreUnknownFields = false) public record ApplicationConfig(
    String topic, boolean useQueue) {
}
