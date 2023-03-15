package org.knifez.fridaybootadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
@author KnifeZ
 */
@SpringBootApplication
@MapperScan("org.knifez.fridaybootadmin.mapper")
public class FridayBootAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(FridayBootAdminApplication.class, args);
    }

}
