package com.banco.transaction_producer_service.Service;

import com.banco.transaction_producer_service.domain.TransactionWithAccount;

public interface TransactionProducerService {

    void publishTransaction(TransactionWithAccount transaction);

}
