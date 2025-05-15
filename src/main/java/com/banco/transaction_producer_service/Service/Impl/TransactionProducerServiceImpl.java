package com.banco.transaction_producer_service.Service.Impl;

import com.banco.transaction_producer_service.Service.TransactionProducerService;
import com.banco.transaction_producer_service.domain.TransactionTypeEnum;
import com.banco.transaction_producer_service.domain.TransactionWithAccount;
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
public class TransactionProducerServiceImpl implements TransactionProducerService {

    @Autowired
    private KafkaTemplate<String, TransactionWithAccount> kafkaTemplate;

    @Value("${topics.transactions}")
    private String TRANSACTIONS_TOPIC;

    @Override
    public void publishTransaction(TransactionWithAccount transaction) {
        try {
            //Gerar UUID para a mensagem
            String messageKey = UUID.nameUUIDFromBytes(transaction.getAccountNumber().getBytes()).toString();
            transaction.setTransactionType(TransactionTypeEnum.TRANSFERENCIA);

            // Enviar mensagem ao Kafka usando o UUID como chave
            kafkaTemplate.send(TRANSACTIONS_TOPIC, messageKey, transaction);
            log.info("Mensagem enviada ao Tópico: {} com UUID: {}", TRANSACTIONS_TOPIC, messageKey);

        } catch (Exception e) {
            log.error("Erro ao enviar mensagem ao Tópico {}: {}", TRANSACTIONS_TOPIC, e.getMessage());
            throw new TransactionException("Erro na comunicação com o kafka", "Não foi possivel enviar a mensagem de atualização ao tópico Kafka.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
