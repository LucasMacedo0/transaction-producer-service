package com.banco.transaction_producer_service.Service;


import com.banco.transaction_producer_service.domain.AccountDTO;

public interface AccountProducerService {

    void publishAccount(AccountDTO accountDTO);

}
