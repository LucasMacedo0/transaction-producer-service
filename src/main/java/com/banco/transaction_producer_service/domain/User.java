package com.banco.transaction_producer_service.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "Representa um usuário no sistema", required = true)
public class User {

    @NotBlank(message = "O nome completo é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome completo deve ter entre 3 e 100 caracteres.")
    @Schema(description = "Nome completo do usuário", example = "João da Silva", required = true, minLength = 3, maxLength = 100)
    private String fullName;

    @NotBlank(message = "CPF ou CNPJ não pode ser vazio.")
    @Pattern(regexp = "^(\\d{11}|\\d{14})$", message = "CPF ou CNPJ deve ter 11 ou 14 dígitos.")
    @Schema(description = "CPF ou CNPJ do usuário", example = "12345678901", required = true)
    private String cpfCnpj;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail informado não é válido.")
    @Schema(description = "E-mail do usuário", example = "joao.silva@email.com", required = true)
    private String email;

    @NotBlank(message = "O número de telefone é obrigatório.")
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$", message = "O número de telefone deve estar no formato (XX) XXXX-XXXX ou (XX) XXXXX-XXXX.")
    @Schema(description = "Número de telefone de contato", example = "(11) 98765-4321", required = true)
    private String phoneNumber;

}
