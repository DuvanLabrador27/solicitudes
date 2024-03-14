package com.semillero.solicitudes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openApiConfig() {
        Contact contact = new Contact();
        contact.setEmail("carlosduvanlh@gmail.com");
        contact.setName("Duvan Labrador");
        contact.setUrl("https://github.com/DuvanLabrador27/solicitudes");

        Info info = new Info()
                .title("Request Vacation API")
                .version("1.0")
                .contact(contact)
                .description("This is a API for request a vacation");

        return new OpenAPI().info(info);
    }
}
