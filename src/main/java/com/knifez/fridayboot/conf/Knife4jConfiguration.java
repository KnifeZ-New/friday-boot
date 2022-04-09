package com.knifez.fridayboot.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KnifeZ
 */
@Configuration
@EnableOpenApi
public class Knife4jConfiguration {
    @Bean(value = "defaultApi")
    public Docket defaultApi() {

        RequestParameterBuilder parameterBuilder = new RequestParameterBuilder();
        List<RequestParameter> parameterList = new ArrayList<>();
        parameterList.add(parameterBuilder.name("Authorization")
                .description("令牌")
                .in(ParameterType.HEADER)
                .required(false).build());
        return new Docket(DocumentationType.OAS_30)
                .globalRequestParameters(parameterList)
                .apiInfo(new ApiInfoBuilder()
                        .title("Friday boot APIs")
                        .description("Friday boot 接口文档")
                        .version("1.0.0")
                        .build())
                .groupName("v1.x")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.knifez.fridayboot.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
