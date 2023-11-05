package com.hegde.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan
@ComponentScan
@EnableJpaRepositories
@EnableAutoConfiguration
@SpringBootApplication
public class TodoAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoAppApplication.class, args);
    }
}