package com.knifez.fridaybootgenerator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author zhang
 */
public class MybatisPlusGenerator {
    public static void main(String[] args) {
        var path = System.getProperty("user.dir");
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/fridayboot", "fridayboot", "fridayboot")
                .globalConfig(builder -> builder.author("KnifeZ")
                        .enableSwagger()
                        .outputDir(path + "/src/main/java"))
                .packageConfig((scanner, builder) -> builder.parent(getPackage(scanner.apply(""))))
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个表名用,隔开")))
                        .entityBuilder().enableLombok()
                        .controllerBuilder().enableRestStyle())
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    protected static String getPackage(String packageName) {
        return "com.knifez.fridayboot" + packageName;
    }

    /**
     * 处理 all 情况
     *
     * @param tables 表
     * @return {@link List}<{@link String}>
     */
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
