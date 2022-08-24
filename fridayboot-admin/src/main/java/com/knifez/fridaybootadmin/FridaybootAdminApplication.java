package com.knifez.fridaybootadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhang
 */
@SpringBootApplication
@MapperScan("com.knifez.fridaybootadmin.mapper")
public class FridaybootAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(FridaybootAdminApplication.class, args);
    }

}
