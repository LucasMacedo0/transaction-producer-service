package com.banco.transaction_producer_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Sistema Bancário - API Produtora",
                version = "1.0",
                description = "API responsável por enviar depósitos e transações para o Kafka.",
                contact = @Contact(name = "Lucas Dev", email = "lm818352@gmail.com"),
                license = @License(name = "Apache 2.0", url = "http://springdoc.org")
        )
)
public class SwaggerConfig {
}