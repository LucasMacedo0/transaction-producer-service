package com.banco.transaction_producer_service.Service.redis;

import com.banco.transaction_producer_service.domain.DltFailureInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DltCacheService {

    @CachePut(value = "dlt-failures", key = "#info.messageKey")
    public DltFailureInfo salvarFalha(DltFailureInfo info) {
        return info; // Redis armazena automaticamente
    }

    @Cacheable(value = "dlt-failures", key = "#messageKey")
    public DltFailureInfo buscarFalha(String messageKey) {
        return null; // O Redis fornece os dados
    }

}