package com.banco.transaction_producer_service.exception;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(description = "Modelo de resposta para erros de validação e exceções")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponse {

    @Schema(description = "Título do erro", example = "Erro na Requisição")
    private String title;

    @Schema(description = "Descrição detalhada do erro", example = "Verifique os campos inválidos")
    private String detail;

    @Schema(description = "Código HTTP do erro", example = "422")
    private int code;

    @Schema(description = "Lista de erros específicos nos campos da requisição")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> errors;
}