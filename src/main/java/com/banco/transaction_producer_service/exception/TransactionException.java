package com.banco.transaction_producer_service.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TransactionException extends RuntimeException {

    private String detail; // Informação detalhada do erro
    private String title;  // Título ou categoria do erro
    private Integer code;  // Código específico do erro
    private List<String> errors; // (Opcional) Lista de erros detalhados

    // Construtor com todos os itens
    public TransactionException(String detail, String title, Integer code) {
        this.detail = detail;
        this.title = title;
        this.code = code;
    }

    // Construtor simplificado para receber lista de erros
    public TransactionException(String detail, String title, List<String> errors) {
        this.detail = detail;
        this.title = title;
        this.errors = errors;
    }

}
