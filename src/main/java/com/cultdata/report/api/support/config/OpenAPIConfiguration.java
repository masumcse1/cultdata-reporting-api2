package com.cultdata.report.api.support.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Value("${campaign.servers.domain:localhost:8084}")
    private String domain;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .openapi("3.0.1")
                .info(new Info().title("CultData Report Api").version("1.0"))
                .servers(createServers());
    }

    private List<Server> createServers() {
        return List.of(
                new Server().url("/").description("Default Server"),
                new Server().url("https://" + domain).description("Production Server"),
                new Server().url("http://localhost:8084").description("Local Server")
        );
    }
}