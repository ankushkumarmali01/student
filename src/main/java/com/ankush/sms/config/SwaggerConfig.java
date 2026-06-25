package com.ankush.sms.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI studentManagementOpenAPI() {

        final String securitySchemeName = "Bearer Authentication";

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Student Management System REST API")
                                .version("1.0.0")
                                .description("""
                                    Student Management System developed using Spring Boot.

                                    Features:
                                    • JWT Authentication
                                    • Role-Based Authorization (ADMIN & STUDENT)
                                    • Student Admission & Profile Management
                                    • Course & Topic Management
                                    • Course Assignment
                                    • Student Search
                                    • Global Exception Handling
                                    • Request Validation
                                    • Swagger/OpenAPI Documentation
                                    """)
                                .contact(
                                        new Contact()
                                                .name("Assessment Submission")
                                                .email("ankushkumarmali01@gmail.com")
                                )
                                .license(
                                        new License()
                                                .name("For Assessment Purpose Only")
                                )
                )

                .addSecurityItem(
                        new SecurityRequirement().addList(securitySchemeName)
                )

                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securitySchemeName,
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}
