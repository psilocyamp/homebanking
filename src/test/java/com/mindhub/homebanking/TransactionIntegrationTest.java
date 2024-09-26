//package com.mindhub.homebanking;
//
//import com.mindhub.homebanking.models.Transaction;
//import com.mindhub.homebanking.repositories.TransactionRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.hamcrest.Matchers.hasProperty;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.not;
//import static org.hamcrest.Matchers.empty;
//
//import java.util.List;
//
//@SpringBootTest
//@ActiveProfiles("test") // Utiliza un perfil de configuración para pruebas
//public class TransactionIntegrationTest {
//
//    @Autowired
//    private TransactionRepository transactionRepository;
//
//    @Test
//    public void existsTransactions() {
//        List<Transaction> transactions = transactionRepository.findAll();
//        assertThat(transactions, not(empty()));
//    }
//
//    @Test
//    public void existsTransactionWithSpecificDetails() {
//        List<Transaction> transactions = transactionRepository.findAll();
//        assertThat(transactions, hasItem(hasProperty("description", is("Pago de factura"))));
//    }
//}
//
