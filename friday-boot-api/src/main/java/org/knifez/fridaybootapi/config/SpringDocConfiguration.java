package org.knifez.fridaybootapi.config;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author KnifeZ
 */
@Configuration
public class SpringDocConfiguration {
    @Bean()
    public GroupedOpenApi createRestApi() {
        return GroupedOpenApi.builder()
                .group("admin")
                .pathsToMatch("/api/**")
                .packagesToScan("org.knifez.fridaybootapi")
                .addOpenApiCustomiser(authorizationOpenApiCustomiser())
                .build();
    }

    public OpenApiCustomiser authorizationOpenApiCustomiser() {
        String authName = "AUTHORIZATION";
        return openApi -> openApi.schemaRequirement(authName, new SecurityScheme().type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .name(authName))
                .addSecurityItem(new SecurityRequirement().addList(authName));

    }
}
