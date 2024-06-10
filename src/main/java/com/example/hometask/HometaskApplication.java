package com.example.hometask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.example.hometask.configuration.PersistenceConfig;

@SpringBootApplication
@Import(value = {PersistenceConfig.class})
public class HometaskApplication {

    public static void main(String[] args) {

        SpringApplication.run(HometaskApplication.class, args);
    }

}
