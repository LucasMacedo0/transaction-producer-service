package com.banco.transaction_producer_service.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TransactionWithAccount {

    @NotEmpty(message = "O número da conta não pode ser vazio.")
    @Size(min = 6, max = 20, message = "O número da conta deve ter entre 6 e 20 caracteres.")
    private String accountNumber;

    // CPF ou CNPJ (adaptado para ambos)
    @NotEmpty(message = "O identificador do cliente (CPF ou CNPJ) não pode ser vazio.")
    private String customerIdentifier; // Pode ser tanto para CPF como CNPJ!

    @NotNull(message = "O valor da transação não pode ser nulo.")
    @Positive(message = "O valor da transação deve ser positivo.")
    private Double amount;

    @NotEmpty(message = "O tipo da transação é obrigatório.")
    private String transactionType;


}
