package com.banco.transaction_producer_service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "Representa uma transação contendo dados essenciais para operação.", required = true)
@Data
public class TransactionWithAccount {

    @NotEmpty(message = "O número da conta não pode ser vazio.")
    @Size(min = 6, max = 20, message = "O número da conta deve ter entre 6 e 20 caracteres.")
    @Schema(description = "Número da conta de origem", example = "123456", required = true, minLength = 6, maxLength = 20)
    private String accountNumber;

    @NotEmpty(message = "O identificador do cliente (CPF ou CNPJ) não pode ser vazio.")
    @Schema(description = "Identificador do cliente (CPF ou CNPJ)", example = "123.456.789-00", required = true)
    private String customerIdentifier;

    @NotNull(message = "O valor da transação não pode ser nulo.")
    @Positive(message = "O valor da transação deve ser positivo.")
    @Schema(description = "Valor da transação", example = "150.75", required = true)
    private Double amount;

    @JsonIgnore
    private TransactionTypeEnum transactionType;

    @NotEmpty(message = "O número da conta de destino não pode ser vazio.")
    @Size(min = 6, max = 20, message = "O número da conta de destino deve ter entre 6 e 20 caracteres.")
    @Schema(description = "Número da conta de destino", example = "654321", required = true, minLength = 6, maxLength = 20)
    private String targetAccountNumber;

}
