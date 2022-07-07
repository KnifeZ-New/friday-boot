package com.knifez.fridayboot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class FridayBootApplicationTests {


    @Test
    void contextLoads() {
        log.info("Junit5 test is ok");

//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String password = bCryptPasswordEncoder.encode("123");
//        log.info(password);
    }

}
