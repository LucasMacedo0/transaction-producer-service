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

    private String detail;
    private String title;
    private Integer code;
    private List<String> errors;


    public TransactionException(String detail, String title, Integer code) {
        this.detail = detail;
        this.title = title;
        this.code = code;
    }


    public TransactionException(String detail, String title, List<String> errors) {
        this.detail = detail;
        this.title = title;
        this.errors = errors;
    }

}
