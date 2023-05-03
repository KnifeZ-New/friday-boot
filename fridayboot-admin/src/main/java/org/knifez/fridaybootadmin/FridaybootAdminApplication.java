package org.knifez.fridaybootadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author KnifeZ
 */
@SpringBootApplication
@ComponentScan("org.knifez.fridaybootcore.**")
@MapperScan("org.knifez.fridaybootadmin.mapper")
public class FridaybootAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(FridaybootAdminApplication.class, args);
    }

}
