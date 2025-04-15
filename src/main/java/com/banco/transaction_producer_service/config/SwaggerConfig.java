package com.banco.transaction_producer_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Produtora de Depósitos e Transações",
                description = "API responsável por enviar depósitos e transações para o Kafka.",
                contact = @Contact(
                        name = "Lucas Dev", url = "https://www.linkedin.com/in/lucas-macedo-2a90171ba/" ),
                license = @License(name = "Apache 2.0", url = "http://springdoc.org")
        )
)
public class SwaggerConfig {
}