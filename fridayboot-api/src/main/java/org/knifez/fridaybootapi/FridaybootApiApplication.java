package org.knifez.fridaybootapi;

import cn.dev33.satoken.SaManager;
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
        System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
    }

}
