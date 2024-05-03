package com.sivalabs.bookstore.webapp.clients;

import com.sivalabs.bookstore.webapp.ApplicationProperties;
import com.sivalabs.bookstore.webapp.clients.catalog.CatalogServiceClient;
import com.sivalabs.bookstore.webapp.clients.orders.OrderServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
class ClientsConfig {
    private final ApplicationProperties properties;

    ClientsConfig(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Bean
    CatalogServiceClient catalogServiceClient(RestClient.Builder builder) {
        RestClient restClient = builder.baseUrl(properties.apiGatewayUrl()).build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();
        return factory.createClient(CatalogServiceClient.class);
    }

    @Bean
    OrderServiceClient orderServiceClient(RestClient.Builder builder) {
        RestClient restClient = builder.baseUrl(properties.apiGatewayUrl()).build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();
        return factory.createClient(OrderServiceClient.class);
    }
}
