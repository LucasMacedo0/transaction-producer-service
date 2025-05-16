package com.banco.transaction_producer_service.Service.kafka;

import com.banco.transaction_producer_service.Service.redis.DltCacheService;
import com.banco.transaction_producer_service.domain.DltFailureInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaDltRetryListener {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final DltCacheService dltCacheService;

    @Value("${topics.transactions}")
    private String TRANSACTIONS_TOPIC;

    @KafkaListener(topics = "TOPIC_TRANSACTIONS.DLT", groupId = "retry-dlt-group")
    public void listenFromDlt(@Payload Object message, @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        log.info("[DLT Listener] Reprocessando mensagem com chave: {}", key);

        try {
            kafkaTemplate.send(TRANSACTIONS_TOPIC, key, message);
            log.info("[DLT-RETRY] Mensagem reenviada com sucesso ao tópico {}", TRANSACTIONS_TOPIC);

        } catch (Exception e) {
            log.error("[DLT-RETRY] Falha ao reenviar mensagem com key {} ao tópico original. Erro: {}", key, e.getMessage(), e);
            DltFailureInfo info = new DltFailureInfo(key, e.getMessage(), LocalDateTime.now());
            dltCacheService.salvarFalha(info);
        }
    }

}
