package com.banco.transaction_producer_service.Controller.redis;

import com.banco.transaction_producer_service.Service.redis.DltCacheService;
import com.banco.transaction_producer_service.domain.DltFailureInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dlt")
@RequiredArgsConstructor
public class DltController {

    private final DltCacheService dltCacheService;

    @GetMapping("/{key}")
    public ResponseEntity<DltFailureInfo> getFalha(@PathVariable String key) {
        DltFailureInfo info = dltCacheService.buscarFalha(key);
        return info != null ? ResponseEntity.ok(info) : ResponseEntity.notFound().build();
    }


}
