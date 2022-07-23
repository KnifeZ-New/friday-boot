package com.knifez.fridaybootapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.knifez.*.mapper")
@ComponentScan("com.knifez.**")
public class FridaybootApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FridaybootApiApplication.class, args);
    }

}
