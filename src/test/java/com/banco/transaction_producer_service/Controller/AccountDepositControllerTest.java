/*
package com.banco.transaction_producer_service.Controller;

import com.banco.transaction_producer_service.Service.AccountProducerService;
import com.banco.transaction_producer_service.fixtures.DepositRequestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AccountDepositController.class)
public class AccountDepositControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountProducerService accountProducerService;

    private DepositRequestTemplate depositRequestTemplate;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public AccountProducerService accountProducerService() {
            return Mockito.mock(AccountProducerService.class);
        }
    }
    @DisplayName("Deve realizar depósito com sucesso na conta e um retorno 200")
    @Test
    void TestControllerDepositAccount(){
        var mock = depositRequestTemplate.depositRequestValid();

    }
}
*/
