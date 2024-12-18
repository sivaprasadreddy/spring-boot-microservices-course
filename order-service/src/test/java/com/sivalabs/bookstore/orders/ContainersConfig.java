package com.sivalabs.bookstore.orders;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;
import org.wiremock.integrations.testcontainers.WireMockContainer;

@TestConfiguration(proxyBeanMethods = false)
public class ContainersConfig {
    static String KEYCLOAK_IMAGE = "quay.io/keycloak/keycloak:26.0";
    static String realmImportFile = "/test-bookstore-realm.json";
    static String realmName = "bookstore";

    static WireMockContainer wiremockServer = new WireMockContainer("wiremock/wiremock:3.5.2-alpine");

    @Bean
    WireMockContainer wiremockServer() {
        wiremockServer.start();
        configureFor(wiremockServer.getHost(), wiremockServer.getPort());
        return wiremockServer;
    }

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:17-alpine"));
    }

    @Bean
    @ServiceConnection
    RabbitMQContainer rabbitContainer() {
        return new RabbitMQContainer(DockerImageName.parse("rabbitmq:4.0.4-alpine"));
    }

    @Bean
    KeycloakContainer keycloak() {
        return new KeycloakContainer(KEYCLOAK_IMAGE).withRealmImportFile(realmImportFile);
    }

    @Bean
    DynamicPropertyRegistrar dynamicPropertyRegistrar(WireMockContainer wiremockServer, KeycloakContainer keycloak) {
        return (registry) -> {
            registry.add("orders.catalog-service-url", wiremockServer::getBaseUrl);
            registry.add(
                    "spring.security.oauth2.resourceserver.jwt.issuer-uri",
                    () -> keycloak.getAuthServerUrl() + "/realms/" + realmName);
        };
    }
}
