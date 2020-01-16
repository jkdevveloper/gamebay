package com.jkdev.gamebay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.jkdev.gamebay.*")
@EnableJpaRepositories
public class GamebayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamebayApplication.class, args);
    }

}
