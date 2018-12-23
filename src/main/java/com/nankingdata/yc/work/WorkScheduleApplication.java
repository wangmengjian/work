package com.nankingdata.yc.work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@Configuration
public class WorkScheduleApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkScheduleApplication.class, args);
    }
}
