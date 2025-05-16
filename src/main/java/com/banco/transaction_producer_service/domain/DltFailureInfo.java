package com.banco.transaction_producer_service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DltFailureInfo {
    private String messageKey;
    private String motivoErro;
    private LocalDateTime dataHora;
}