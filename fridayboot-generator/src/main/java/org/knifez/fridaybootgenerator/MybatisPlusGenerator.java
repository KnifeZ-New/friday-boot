package org.knifez.fridaybootgenerator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author KnifeZ
 */
@Slf4j
public class MybatisPlusGenerator {
    /**
     * 包路径
     */
    private static String packagePath;

    private static final String PROJECT_NAME = "fridayboot";
    private static final String DATABASE_URL = "jdbc:mysql://124.222.142.22:3306/fridayboot?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
    private static final String DATABASE_USER = "fridayboot";
    private static final String DATABASE_PASSWORD = "pSLpNRB4k3Df8r3G";

    public static void main(String[] args) {
        FastAutoGenerator.create(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)
                .globalConfig((scanner, builder) ->
                        builder.author("KnifeZ")
                                .enableSpringdoc()
                                .disableOpenDir()
                                .outputDir(getOutputPath(scanner.apply("请输入模块名称"))
                                ))
                .packageConfig((scanner, builder) -> builder.parent(packagePath))
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个表名用,隔开")))
                        .entityBuilder().enableLombok()
                        .controllerBuilder().enableRestStyle())
                .injectionConfig(consumer -> {
                    Map<String, String> customFile = new HashMap<>();
                    // DTO
                    customFile.put("PagedRequest.java", "/templates/entityPagedRequestDTO.java.ftl");
                    consumer.customFile(customFile);
                })
                .templateConfig((scanner, builder) -> builder.controller("/templates/controller.java")
                        .service("/templates/service.java")
                        .disable(TemplateType.XML))
                .templateEngine(new EnhanceFreemarkerTemplateEngine())
                .execute();
    }

    protected static String getOutputPath(String packageName) {
        var path = System.getProperty("user.dir") + "/" + PROJECT_NAME + "-" + packageName + "/src/main/java";
        log.info(path);
        packagePath = "org.knifez." + PROJECT_NAME + packageName.replace("-", "");
        log.info(packagePath);
        return path;
    }

    /**
     * 处理 all 情况
     *
     * @param tables 表
     * @return {@link List}<{@link String}>
     */
    protected static List<String> getTables(String tables) {
        List<String> tableList;
        if ("all".equals(tables)) {
            tableList = new ArrayList<>();
        } else {
            tableList = Arrays.asList(tables.split(","));
        }
        return tableList;
    }
}
