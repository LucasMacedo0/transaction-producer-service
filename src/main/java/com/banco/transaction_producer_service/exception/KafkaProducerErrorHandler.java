package com.banco.transaction_producer_service.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerErrorHandler {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    //retry automatico para cobrir falhas..
    //Fluxo Produzir mensagem -> Kafka Producer tenta 3 vezes -> Se falhar, lança exceção -> o catch envia a mensagem para DLT -> Monitorar DLT para tratativa.
    public void handleFailedMessage(Object object, String messageKey, Exception exception){
        String dltTopic = "TOPIC_TRANSACTIONS.DLT";
        log.error("[DLT] Falha ao enviar mensagem. Redirecionando para o tópico {}. Erro: {}", dltTopic, exception.getMessage(), exception);
        kafkaTemplate.send(dltTopic, messageKey, object);

    }
}
