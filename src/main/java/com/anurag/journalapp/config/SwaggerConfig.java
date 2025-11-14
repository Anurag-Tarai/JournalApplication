package com.anurag.journalapp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("📝 Journal Application — Secure Personal Journaling")
                        .description("""
                                A secure and robust backend for a personal journaling application, enabling users to create, manage, and reflect on their daily entries with confidence.
                                This platform features JWT-based authentication for security, cloud storage with MongoDB Atlas, and performance optimization through Redis caching.

                                ### Features
                                ---
                                - **Secure User Authentication:** Full sign-up, sign-in, and password management with JWT.
                                - **Cloud Data Storage:** Journal entries are securely stored in MongoDB Atlas.
                                - **Performance Caching:** Redis is used to cache frequently accessed entries for faster retrieval.
                                - **Email Verification:** SMTP-based email verification for new user sign-ups.
                                - **Safe Deletion:** Soft-delete functionality with an archival system.
                                - **Efficient Data Retrieval:** Paginated responses for journal entries (20 per page).

                                ### </> Tech Stack
                                ---
                                - Spring Boot, MongoDB Atlas, Redis, AWS EC2, SMTP

                                ### 🔗 Links
                                ---
                                - [Backend Repository](https://github.com/anurag-tarai/JournalApplication)

                                ### 👤 Contact
                                ---
                                **Author:** Anurag Tarai | 📧 [anurag.tarai01@gmail.com](mailto:anurag.tarai01@gmail.com) | 🔗 [LinkedIn](https://linkedin.com/in/anurag-tarai)
                                """)
                        .version("1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .components(new Components().addSecuritySchemes("BearerAuth",
                        new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
