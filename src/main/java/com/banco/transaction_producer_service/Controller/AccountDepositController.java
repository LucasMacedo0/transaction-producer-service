package com.banco.transaction_producer_service.Controller;

import com.banco.transaction_producer_service.domain.DepositRequest;
import com.banco.transaction_producer_service.Service.AccountProducerService;
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

@RestController
@RequiredArgsConstructor
@Tag(name = "AccountDepositController", description = "API responsável por enviar informações de depósito de contas para o tópico Kafka.")
public class AccountDepositController {

    @Autowired
    private final AccountProducerService accountProducerService;

    @Operation(
            summary = "Realiza o depósito na conta",
            description = "Realiza o depósito em uma conta específica. Envia as informações para o respectivo tópico Kafka."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Depósito realizado com sucesso",
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
    @PostMapping("/deposits")
    public ResponseEntity<DepositRequest> depositAccount(@Valid @RequestBody DepositRequest depositRequest){
        accountProducerService.publishAccount(depositRequest);
        return ResponseEntity.ok(depositRequest);
    };
}


