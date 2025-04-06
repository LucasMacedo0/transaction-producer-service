package com.banco.transaction_producer_service.Service;

import com.banco.transaction_producer_service.domain.TransactionWithAccount;
import jakarta.validation.Valid;

import java.util.List;

public interface TransactionProducerService {

    void publishTransaction(TransactionWithAccount transaction);

}
