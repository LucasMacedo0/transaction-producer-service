package com.banco.transaction_producer_service.Service.kafka;

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
            @Payload Object message,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(name = "retryCount", required = false) byte[] retryCountHeader
    ) {
        int retryCount = retryCountHeader != null ? Integer.parseInt(new String(retryCountHeader)) : 0;

        log.info("[DLT Listener] Reprocessando mensagem com chave: {}, tentativa: {}", key, retryCount + 1);

        if (retryCount >= MAX_RETRY) {
            log.warn("[DLT Listener] Tentativas esgotadas para a mensagem com chave: {}. Mensagem será ignorada.", key);
            return;
        }

        ProducerRecord<String, Object> record = new ProducerRecord<>(TRANSACTIONS_TOPIC, key, message);
        record.headers().add("retryCount", Integer.toString(retryCount + 1).getBytes());
        kafkaTemplate.send(record);
        log.info("[DLT-RETRY] Mensagem reenviada ao tópico {} com retryCount={}", TRANSACTIONS_TOPIC, retryCount + 1);
    }


}
