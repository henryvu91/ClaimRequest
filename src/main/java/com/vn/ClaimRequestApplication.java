package com.vn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ClaimRequestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClaimRequestApplication.class, args);
    }
}


