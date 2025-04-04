package com.banco.transaction_producer_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        // Lista para coletar os erros de validação
        List<String> errorDetails = new ArrayList<>();
        // Itera pelos erros de validação e formata os detalhes
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorDetails.add(String.format("Campo: %s - Erro: %s", error.getField(), error.getDefaultMessage()));
        }
        // Criação da exceção TransactionException com a lista de erros
        TransactionException response = new TransactionException(
                "Verifique os campos inválidos",
                "Erro na Requisição",
                errorDetails
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }
}
