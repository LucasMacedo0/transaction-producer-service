package com.banco.transaction_producer_service.fixtures;

import com.banco.transaction_producer_service.domain.DepositRequest;
import com.banco.transaction_producer_service.domain.User;

public class DepositRequestTemplate {

    public DepositRequest depositRequestValid() {
        return DepositRequest.builder()
                .accountNumber("123456789")
                .branchCode("001")
                .bankName("Banco Exemplo")
                .accountType("CONTA_CORRENTE")
                .amount(250.00)
                .owner(User.builder()
                        .fullName("João Silva")
                        .cpfCnpj("12345678901")
                        .email("joao.silva@example.com")
                        .phoneNumber("(11) 91234-5678")
                        .build())
                .build();
    }

}
