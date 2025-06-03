package com.banco.transaction_producer_service.Service.kafka;

import com.banco.transaction_producer_service.domain.DepositRequest;
import com.banco.transaction_producer_service.domain.TransactionWithAccount;
import com.banco.transaction_producer_service.exception.TransactionException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaDltRetryListener {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${topics.transactions}")
    private String TRANSACTIONS_TOPIC;

    private static final int MAX_RETRY = 3;

    @KafkaListener(topics = "TOPIC_TRANSACTIONS.DLT", groupId = "retry-dlt-group")
    public void listenFromDlt(
            @Payload String message, // Recebe como string JSON
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(name = "retryCount", required = false) byte[] retryCountHeader
    ) {
        int retryCount = retryCountHeader != null ? Integer.parseInt(new String(retryCountHeader)) : 0;

        log.info("[DLT Listener] Reprocessando mensagem com chave: {}, tentativa: {}", key, retryCount + 1);

        if (retryCount >= MAX_RETRY) {
            log.warn("[DLT Listener] Tentativas esgotadas para a mensagem com chave: {}. Mensagem será ignorada.", key);
            return;
        }

        try {
            Object parsedObject = parseMessage(message);

            ProducerRecord<String, Object> record = new ProducerRecord<>(TRANSACTIONS_TOPIC, key, parsedObject);
            record.headers().add("retryCount", Integer.toString(retryCount + 1).getBytes());

            kafkaTemplate.send(record);
            log.info("[DLT-RETRY] Mensagem reenviada ao tópico {} com retryCount={}", TRANSACTIONS_TOPIC, retryCount + 1);

        } catch (Exception e) {
            log.error("[DLT Listener] Falha ao processar mensagem com chave: {}. Erro: {}", key, e.getMessage());
        }
    }

    private Object parseMessage(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(message);

        if (jsonNode.has("transactionType")) {
            String tipo = jsonNode.get("transactionType").asText();

            switch (tipo) {
                case "DEPOSITO" -> objectMapper.treeToValue(jsonNode, DepositRequest.class);
                case "TRANSFERENCIA" -> objectMapper.treeToValue(jsonNode, TransactionWithAccount.class);
                default -> throw new TransactionException(
                        "Transaction type inválido",
                        String.format("O tipo de transação '%s' não é suportado. Verifique se o campo 'transactionType' está correto no payload.", tipo),
                        422
                );
            }
        } else {
            throw new TransactionException(
                    "Transaction type não encontrado",
                    "O campo 'transactionType' não foi encontrado no payload da mensagem. Não é possível determinar como processar essa transação.",
                    400
            );
        }
        return null;
    }
}
