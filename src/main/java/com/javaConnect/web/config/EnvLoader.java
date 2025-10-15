package com.javaConnect.web.config;


import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;


@Configuration
public class EnvLoader {
    @PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.configure()
                               .filename("db.env")  // specify your custom file
                               .load();
        dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
    }
}
