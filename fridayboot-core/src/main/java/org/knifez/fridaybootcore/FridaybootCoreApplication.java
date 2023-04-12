package org.knifez.fridaybootcore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author KnifeZ
 */
@SpringBootApplication
@MapperScan("org.knifez.fridaybootcore.mapper")
public class FridaybootCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(FridaybootCoreApplication.class, args);
    }

}
