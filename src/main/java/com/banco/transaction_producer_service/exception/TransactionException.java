package com.banco.transaction_producer_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionException extends RuntimeException{

    private String detail;
    private String title;
    private Integer code;

    public TransactionException(String detail, String title) {
        this.detail = detail;
        this.title = title;
        this.code = 422;
    }
}
