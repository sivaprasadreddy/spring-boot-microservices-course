package com.sivalabs.bookstore.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BookstoreWebappApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreWebappApplication.class, args);
    }
}
