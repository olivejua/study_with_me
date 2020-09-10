package com.ksk.project.study_with_me;

import com.ksk.project.study_with_me.config.auth.SessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpSessionListener;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new SessionListener();
    }
}

