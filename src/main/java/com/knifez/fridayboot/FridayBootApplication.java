package com.knifez.fridayboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author KnifeZ
 */
@SpringBootApplication
@MapperScan("com.knifez.fridayboot.mapper")
public class FridayBootApplication {
	public static void main(String[] args) {
		SpringApplication.run(FridayBootApplication.class, args);
	}

}
