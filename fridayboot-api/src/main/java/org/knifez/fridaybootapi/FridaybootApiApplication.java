package org.knifez.fridaybootapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author KnifeZ
 */
@SpringBootApplication
@ComponentScan("org.knifez.**")
public class FridaybootApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FridaybootApiApplication.class, args);
    }

}
