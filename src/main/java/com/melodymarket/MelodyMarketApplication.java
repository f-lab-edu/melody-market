package com.melodymarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/keys.properties")
public class MelodyMarketApplication {


    public static void main(String[] args) {
        SpringApplication.run(MelodyMarketApplication.class, args);
    }
}
