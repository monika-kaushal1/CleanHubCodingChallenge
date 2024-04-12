package com.cleanhub.codingchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CodingchallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodingchallengeApplication.class, args);
    }

}
