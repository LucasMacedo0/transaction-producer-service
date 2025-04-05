package com.banco.transaction_producer_service.domain;

import jakarta.validation.constraints.*;

public class User {

    @NotBlank(message = "O nome completo é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome completo deve ter entre 3 e 100 caracteres.")
    private String fullName;        // Nome completo

    @NotBlank(message = "O número do documento é obrigatório.")
    @Pattern(regexp = "^(\\d{11}|\\d{14})$", message = "O CPF ou CNPJ deve ter 11 ou 14 dígitos.")
    private String documentNumber;  // CPF ou CNPJ

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail informado não é válido.")
    private String email;           // E-mail para notificações

    @NotBlank(message = "O número de telefone é obrigatório.")
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$", message = "O número de telefone deve estar no formato (XX) XXXX-XXXX ou (XX) XXXXX-XXXX.")
    private String phoneNumber;     // Telefone de contato

}
