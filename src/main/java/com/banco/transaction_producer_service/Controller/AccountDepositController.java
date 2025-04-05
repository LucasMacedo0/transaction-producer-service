package com.banco.transaction_producer_service.Controller;

import com.banco.transaction_producer_service.domain.DepositRequest;
import com.banco.transaction_producer_service.Service.AccountProducerService;
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

@RestController
@RequiredArgsConstructor
@Tag(name = "AccountDepositController", description = "API responsável por enviar informações de depósito de contas para o tópico Kafka.")
public class AccountDepositController {

    @Autowired
    private final AccountProducerService accountProducerService;

    @Operation(
            summary = "Realiza o depósito na conta",
            description = "Este endpoint processa o depósito de uma conta, enviando as informações para o tópico Kafka. A requisição deve conter os dados da conta e será validada antes de ser publicada no Kafka."
    )
    //TODO refatorar o APIResponse
   /* @ApiResponse(responseCode = "200", description = "Depósito realizado com sucesso.")
    @ApiResponse(responseCode = "400", description = "Pedido inválido. Verifique os dados da requisição.")*/
    @PostMapping("/deposits")
    public ResponseEntity<DepositRequest> depositAccount(@Valid @RequestBody DepositRequest depositRequest){
        accountProducerService.publishAccount(depositRequest);
        return ResponseEntity.ok(depositRequest);
    };
}


