package com.banco.transaction_producer_service.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DepositRequest {

    @NotBlank(message = "Número da conta não pode ser vazio.")
    private String accountNumber;   // Número único da conta

    @NotBlank(message = "Código da agência não pode ser vazio.")
    private String branchCode;      // Código da agência

    @NotBlank(message = "Nome do banco não pode ser vazio.")
    private String bankName;        // Nome do banco (Ex.: "Bradesco", "Itaú")

    @NotNull(message = "Tipo de conta não pode ser nulo.")
    @Pattern(regexp = "^(CORRENTE|POUPANÇA)$", message = "Tipo de conta inválido. Deve ser CORRENTE ou POUPANÇA.")
    private String accountType;     // Tipo da conta (CORRENTE, POUPANÇA)

    @NotNull(message = "O valor do depósito não pode ser nulo.")
    @Positive(message = "O valor do depósito deve ser maior que zero.")
    private Double amount;

    @NotBlank(message = "CPF ou CNPJ não pode ser vazio.")
    @Pattern(regexp = "^(\\d{11}|\\d{14})$", message = "CPF ou CNPJ deve ter 11 ou 14 dígitos.")
    private String cpfCnpj;         // CPF (11 dígitos) ou CNPJ (14 dígitos)

    @Valid
    @NotNull(message = "proprietário é obrigatorio")
    private User owner;          // Proprietário da conta
}
