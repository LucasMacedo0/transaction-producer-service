package com.banco.transaction_producer_service.Service.Impl;

import com.banco.transaction_producer_service.Service.AccountProducerService;
import com.banco.transaction_producer_service.domain.DepositRequest;
import com.banco.transaction_producer_service.exception.KafkaProducerErrorHandler;
import com.banco.transaction_producer_service.exception.TransactionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountProducerServiceImpl implements AccountProducerService {

    @Autowired
    private KafkaTemplate<String, DepositRequest> kafkaTemplate;

    @Autowired
    private KafkaProducerErrorHandler kafkaProducerErrorHandler;

    @Value("${topics.transactions}")
    private String TRANSACTIONS_TOPIC;

    @Retryable(
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000)
    )
    @Override
    public void publishAccount(DepositRequest depositRequest) {
        String messageKey = UUID.nameUUIDFromBytes(depositRequest.getAccountNumber().getBytes()).toString();
        try {
            kafkaTemplate.send(TRANSACTIONS_TOPIC, messageKey, depositRequest);
            log.info("Mensagem enviada ao Tópico: {} com UUID: {}", TRANSACTIONS_TOPIC, messageKey);

        } catch (Exception e) {
            log.error("Erro ao enviar mensagem ao Tópico {}:", TRANSACTIONS_TOPIC);
            kafkaProducerErrorHandler.handleFailedMessage(TRANSACTIONS_TOPIC, messageKey, e);
            log.error("Erro tratado e mensagem redirecionada para DLT.");
        }
    }

}
