package com.banco.transaction_producer_service.Controller;

import com.banco.transaction_producer_service.Service.TransactionProducerService;
import com.banco.transaction_producer_service.domain.DepositRequest;
import com.banco.transaction_producer_service.domain.TransactionWithAccount;
import com.banco.transaction_producer_service.exception.ValidationErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
            description = "Realiza uma transação de transferência entre contas específicas. Publica as informações no tópico Kafka para processamento."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação realizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DepositRequest.class))),
            @ApiResponse(responseCode = "400", description = "Erro na requisição. Campos obrigatórios não preenchidos ou JSON inválido.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação das regras de negócio.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "405", description = "Método HTTP não suportado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno inesperado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class)))
    })
    @PostMapping("/transactions")
    public ResponseEntity<TransactionWithAccount> transactionAccount(@Valid @RequestBody TransactionWithAccount transaction){
        transactionProducerService.publishTransaction(transaction);
        return ResponseEntity.ok(transaction);

    }

}
