package com.eolLeongTtungTtang.todo.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("얼렁뚱땅 API") // API의 제목
                .description("얼렁뚱땅의 투두 프로젝트 Swagger UI") // API에 대한 설명
                .version("1.0.0"); // API의 버전
    }
}