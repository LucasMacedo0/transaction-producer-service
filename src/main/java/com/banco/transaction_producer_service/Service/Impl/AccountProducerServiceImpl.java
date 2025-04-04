package com.banco.transaction_producer_service.Service.Impl;

import com.banco.transaction_producer_service.domain.AccountDTO;
import com.banco.transaction_producer_service.Service.AccountProducerService;
import com.banco.transaction_producer_service.exception.TransactionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountProducerServiceImpl implements AccountProducerService {

    @Autowired
    private final KafkaTemplate<String, AccountDTO> kafkaTemplate;

    @Value("${transactions.topic}")
    private final String TRANSACTIONS_TOPIC;

    @Override
    public void publishAccount(AccountDTO accountDTO) {
        try {
            // Gerar UUID para a mensagem
            String messageKey = UUID.nameUUIDFromBytes(accountDTO.getAccountNumber().getBytes()).toString();
            // Enviar mensagem ao Kafka usando o UUID como chave
            kafkaTemplate.send(TRANSACTIONS_TOPIC, messageKey, accountDTO);
            log.info("Mensagem enviada ao Tópico: {} com UUID: {}", TRANSACTIONS_TOPIC, messageKey);
        } catch (Exception e) {
            log.error("Erro ao enviar mensagem ao Tópico {}: {}", TRANSACTIONS_TOPIC, e.getMessage());
            throw new TransactionException("Erro na comunicação com o kafka", "Não foi possivel enviar a mensagem de atualização ao tópico Kafka.", HttpStatus.INTERNAL_SERVER_ERROR.value());

        }
    }

}
