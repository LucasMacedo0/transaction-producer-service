package com.banco.transaction_producer_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class CustomHandlerException {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        String message = "Erro na Requisição";

        List<String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("Campo: %s - Erro: %s", error.getField(), error.getDefaultMessage()))
                .toList();

        var transactionException = getTransactionException(ex, message, "Verifique os campos inválidos", HttpStatus.UNPROCESSABLE_ENTITY, fieldErrors);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(transactionException);
    }

    // Tratamento de exceções personalizadas da aplicação (TransactionException)
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<Object> handleTransactionException(TransactionException ex) {
        Map<String, Object> response = new HashMap<>();

        response.put("title", ex.getTitle());
        response.put("detail", ex.getDetail());
        response.put("code", ex.getCode());

        return ResponseEntity.status(ex.getCode()).body(response);
    }

    // Tratamento para requisições com métodos HTTP não permitidos no endpoint
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        String message = String.format("Método HTTP %s não permitido para esse endpoint", ex.getMethod());

        var transactionException = getTransactionException(ex, message, "Requisição com método inválido", HttpStatus.METHOD_NOT_ALLOWED, null);

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(transactionException);
    }

    // Tratamento para corpo de requisição mal formatado (ex: JSON inválido)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "O corpo da requisição está inválido. Verifique se o JSON está correto.";

        var transactionException = getTransactionException(ex, message, "Erro ao Enviar Requisição", HttpStatus.BAD_REQUEST, null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionException);

    }

    // Tratamento genérico para exceções não previstas (evita stack trace)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        String message = "Ocorreu um erro inesperado";
        var transactionException = getTransactionException(ex, message, "Erro Interno", HttpStatus.INTERNAL_SERVER_ERROR, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(transactionException);
    }

    //Metodo utilitário reutilizado para construir a resposta de erro (ValidationErrorResponse)
    private static ValidationErrorResponse getTransactionException(Exception ex, String message, String title, HttpStatus status, List<String> fieldErrors) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(title, message, status.value(), new ArrayList<>());
        log.error("[{} - {}] {}", title, status.value(), message, ex);
        return ValidationErrorResponse.builder()
                .title(title)
                .code(status.value())
                .detail(message)
                .errors(fieldErrors)
                .build();
    }


}
