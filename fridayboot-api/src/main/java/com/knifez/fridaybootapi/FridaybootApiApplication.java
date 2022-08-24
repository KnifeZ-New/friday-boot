package com.knifez.fridaybootapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * fridayboot api应用程序
 *
 * @author zhang
 */
@SpringBootApplication
@ComponentScan("com.knifez.**")
public class FridaybootApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FridaybootApiApplication.class, args);
    }

}
