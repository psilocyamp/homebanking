//package com.mindhub.homebanking;
//
//import com.mindhub.homebanking.models.*;
//import com.mindhub.homebanking.repositories.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//
//// tells to Spring that it must scan en looking for @Entity classes and setting up JPA repositories.
////También hace que las operaciones realizadas en la base de datos sean transaccionales de forma predeterminada para que después de ejecutarlas se reviertan
//// y no afecten a los datos reales fuera de las pruebas, así como indicar que desea conectarse a una base de datos real
//@DataJpaTest
//@SpringJUnitConfig(TestConfig.class)
//
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//
//public class RepositoriesTest {
//
//    @Autowired
//    LoanRepository loanRepository;
//
//    @Autowired
//    AccountRepository accountRepository;
//
//    @Autowired
//    CardRepository cardRepository;
//
//    @Autowired
//    ClientRepository clientRepository;
//
//    @Autowired
//    TransactionRepository transactionRepository;
//
//
//    @Test
//    public void existLoans(){
//        List<Loan> loans = loanRepository.findAll();
//        assertThat(loans,is(not(empty())));
//    }
//    @Test
//    public void existPersonalLoan(){
//        List<Loan> loans = loanRepository.findAll();
//        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
//    }
//
//    // AccountRepository tests
//    @Test
//    public void accountsExist() {
//        List<Account> accounts = accountRepository.findAll();
//        assertThat(accounts, is(not(empty())));
//    }
//
//    @Test
//    public void accountExistsByNumber() {
//        // Asume que el número de cuenta "12345678" existe en la base de datos.
//        Boolean exists = accountRepository.existsByNumber("12345678");
//        assertThat(exists, is(true));
//    }
//
//    // CardRepository tests
//    @Test
//    public void cardsExist() {
//        List<Card> cards = cardRepository.findAll();
//        assertThat(cards, is(not(empty())));
//    }
//
//    @Test
//    public void cvvExists() {
//        // Asume que el CVV 123 existe en la base de datos.
//        Boolean exists = cardRepository.existsByCvv(123);
//        assertThat(exists, is(true));
//    }
//
//    // ClientRepository tests
//    @Test
//    public void clientsExist() {
//        List<Client> clients = clientRepository.findAll();
//        assertThat(clients, is(not(empty())));
//    }
//
//    @Test
//    public void clientExistsByEmail() {
//        // Asume que el email "example@example.com" existe en la base de datos.
//        Optional<Client> client = clientRepository.findByEmail("examplee@example.com");
//        assertThat(client.isPresent(), is(true));
//    }
//
//    // TransactionRepository tests
//    @Test
//    public void transactionsExist() {
//        List<Transaction> transactions = transactionRepository.findAll();
//        assertThat(transactions, is(not(empty())));
//    }
//
//    @Test
//    public void transactionExistsByAccountId() {
//        // Asume que el ID de cuenta 1 existe en la base de datos.
//        List<Transaction> transactions = transactionRepository.findByAccountId(1L);
//        assertThat(transactions, is(not(empty())));
//    }
//
//
//
//}
