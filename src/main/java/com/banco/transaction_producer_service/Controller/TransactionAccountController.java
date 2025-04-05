package com.banco.transaction_producer_service.Controller;

import com.banco.transaction_producer_service.Service.TransactionProducerService;
import com.banco.transaction_producer_service.domain.TransactionWithAccount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "TransactionAccountController", description = "API responsável por enviar transações de contas para o tópico Kafka.")
public class TransactionAccountController {


    @Autowired
    private final TransactionProducerService transactionProducerService;

    @Operation(
            summary = "Realiza uma transação para uma conta",
            description = "Este endpoint permite realizar uma única transação vinculada a uma conta específica."
    )
    @PostMapping("/transactions")
    public ResponseEntity<TransactionWithAccount> transactionAccount(@Valid @RequestBody TransactionWithAccount transaction){
        transactionProducerService.publishTransaction(transaction);
        return ResponseEntity.ok(transaction);

    }

}
