package com.example.demo.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI getOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("HMS APIs")
                        .description("Hospital Management System's Documentation")
        )
        .servers(
                List.of(
                new Server().url("http://localhost:7060").description("Hospital Management System"),
                new Server().url("http://localhost:8085").description("New Server"))
        )
        .tags(
                List.of(
                        new Tag().name("Doctor APIs"),
                        new Tag().name("Patient APIs"),
                        new Tag().name("Doctor Availability APIs"),
                        new Tag().name("Appointment APIs"),
                        new Tag().name("User APIs"),
                        new Tag().name("Appointment's Pagination APIs"),
                        new Tag().name("Patient Pagination APIs"),
                        new Tag().name("Doctor Pagination APIs"),
                        new Tag().name("User Pagination APIs")
                )
        );
    }
}