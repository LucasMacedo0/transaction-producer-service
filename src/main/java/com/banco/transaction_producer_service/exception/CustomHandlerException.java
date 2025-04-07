package com.banco.transaction_producer_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
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

        var transactionException = getTransactionException(ex, message, "Requisição com método inválido", HttpStatus.METHOD_NOT_ALLOWED);

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(transactionException);
    }

    // Tratamento para corpo de requisição mal formatado (ex: JSON inválido)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "O corpo da requisição está inválido. Verifique se o JSON está correto.";

        var transactionException = getTransactionException(ex, message, "Erro ao Enviar Requisição", HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionException);

    }

    // Tratamento genérico para exceções não previstas (evita stack trace)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        String message = "Ocorreu um erro inesperado";
        var transactionException = getTransactionException(ex, message, "Erro Interno", HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(transactionException);
    }

   //Metodo utilitário reutilizado para construir a resposta de erro (TransactionException)
    private static TransactionException getTransactionException(Exception ex, String message, String title, HttpStatus status) {
        log.error("[{} - {}] {}", title, status.value(), message, ex);
        return TransactionException.builder()
                .title(title)
                .code(status.value())
                .detail(message)
                .build();
    }


}
