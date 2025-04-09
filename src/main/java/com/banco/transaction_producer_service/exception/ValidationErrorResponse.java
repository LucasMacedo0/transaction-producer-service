package com.banco.transaction_producer_service.exception;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@Setter
public class ValidationErrorResponse {

    private String title;
    private String detail;
    private int code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;


}
