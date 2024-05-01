package com.sivalabs.bookstore.webapp;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bookstore")
public record ApplicationProperties(String apiGatewayUrl) {}
