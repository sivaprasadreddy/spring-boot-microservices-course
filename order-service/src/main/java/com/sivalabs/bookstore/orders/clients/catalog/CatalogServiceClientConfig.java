package com.sivalabs.bookstore.orders.clients.catalog;

import com.sivalabs.bookstore.orders.ApplicationProperties;
import java.time.Duration;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
class CatalogServiceClientConfig {
    @Bean
    RestClient restClient(RestClient.Builder builder, ApplicationProperties properties) {
        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactoryBuilder.simple()
                .withCustomizer(customizer -> {
                    customizer.setConnectTimeout(Duration.ofSeconds(5));
                    customizer.setReadTimeout(Duration.ofSeconds(5));
                })
                .build();
        return builder.baseUrl(properties.catalogServiceUrl())
                .requestFactory(requestFactory)
                .build();
    }
}
