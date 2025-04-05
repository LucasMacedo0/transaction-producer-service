package com.banco.transaction_producer_service.Service;


import com.banco.transaction_producer_service.domain.DepositRequest;

public interface AccountProducerService {

    void publishAccount(DepositRequest accountDTO);

}
