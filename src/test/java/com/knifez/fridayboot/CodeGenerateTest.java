package com.knifez.fridayboot;

import cn.hutool.core.lang.Console;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CodeGenerateTest {

    /**
     * 执行 run
     */
    public static void main(String[] args) {
        var path = System.getProperty("user.dir");
        Console.log(path);
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/fridayboot", "fridayboot", "fridayboot")
                // 全局配置
                .globalConfig((builder) -> builder.author("KnifeZ")
                        .enableSwagger()
                        .fileOverride()
                        .outputDir(path + "/src/main/java"))
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent("com.knifez.fridayboot"))
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个表名用,隔开")))
                        .entityBuilder().enableLombok()
                        .controllerBuilder().enableRestStyle())
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
