package com.semillero.solicitudes.config;

import com.semillero.solicitudes.utils.Constants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openApiConfig() {

        Contact contact = new Contact();
        contact.setEmail(Constants.SWAGGER_CONTACT_EMAIL_MESSAGE);
        contact.setName(Constants.SWAGGER_CONTACT_NAME_MESSAGE);
        contact.setUrl(Constants.SWAGGER_CONTACT_URL_MESSAGE);

        License license = new License();
        license.setName(Constants.SWAGGER_LICENSE_NAME_MESSAGE);
        license.setUrl(Constants.SWAGGER_LICENSE_URL_MESSAGE);

        Info info = new Info()
                .title(Constants.SWAGGER_TITLE_MESSAGE)
                .description(Constants.SWAGGER_DESCRIPTION_MESSAGE)
                .version(Constants.SWAGGER_VERSION_MESSAGE)
                .contact(contact)
                .license(license)
                .termsOfService(Constants.SWAGGER_TERMS_OF_SERVICE_MESSAGE);

        return new OpenAPI().info(info);
    }
}
