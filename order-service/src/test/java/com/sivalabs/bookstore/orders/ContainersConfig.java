package com.sivalabs.bookstore.orders;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class ContainersConfig {
    static String KEYCLOAK_IMAGE = "quay.io/keycloak/keycloak:24.0.2";
    static String realmImportFile = "/test-bookstore-realm.json";
    static String realmName = "bookstore";

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"));
    }

    @Bean
    @ServiceConnection
    RabbitMQContainer rabbitContainer() {
        return new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.12.11-alpine"));
    }

    @Bean
    KeycloakContainer keycloak(DynamicPropertyRegistry registry) {
        var keycloak = new KeycloakContainer(KEYCLOAK_IMAGE).withRealmImportFile(realmImportFile);
        registry.add(
                "spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> keycloak.getAuthServerUrl() + "/realms/" + realmName);
        return keycloak;
    }
}
