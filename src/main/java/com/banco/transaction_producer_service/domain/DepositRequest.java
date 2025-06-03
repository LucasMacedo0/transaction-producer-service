package com.banco.transaction_producer_service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "Representa uma solicitação de depósito em uma conta", required = true)
public class DepositRequest {

    @Schema(description = "Número único da conta", example = "123456789", required = true)
    @NotBlank(message = "Número da conta não pode ser vazio.")
    private String accountNumber;

    @Schema(description = "Código da agência", example = "001",  required = true)
    @NotBlank(message = "Código da agência não pode ser vazio.")
    private String branchCode;

    @Schema(description = "Nome do banco",  required = true)
    @NotBlank(message = "Nome do banco não pode ser vazio.")
    private String bankName;

    @Schema(description = "Tipo de conta (CORRENTE ou POUPANÇA)", example = "CORRENTE",  required = true)
    @NotNull(message = "Tipo de conta não pode ser nulo.")
    @Pattern(regexp = "^(CORRENTE|POUPANÇA)$", message = "Tipo de conta inválido.")
    private String accountType;

    @Schema(description = "Valor do depósito", example = "100.50",  required = true)
    @NotNull(message = "O valor do depósito não pode ser nulo.")
    @Positive(message = "O valor do depósito deve ser maior que zero.")
    private Double amount;

    @JsonIgnore
    private TransactionTypeEnum transactionType;

    @Schema(description = "Proprietário da conta",  required = true)
    @Valid
    @NotNull(message = "proprietário é obrigatorio")
    private User owner;
}
