package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api")

    public class AccountController {

    @Autowired
    private ClientRepository clientRepository;


    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;


    @GetMapping
    public List<AccountDTO> getAllTransactions() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new AccountDTO(account), HttpStatus.OK);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<AccountDTO>> getAccountsByClient(@PathVariable Long clientId) {
        List<Account> accounts = accountRepository.findByClientId(clientId);
        if (accounts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<AccountDTO> accountDTOs = accounts.stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(accountDTOs, HttpStatus.OK);
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);
        if (transactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<TransactionDTO> transactionDTOs = transactions.stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(transactionDTOs, HttpStatus.OK);
    }

    @PostMapping ("/clients/current/accounts")
    public ResponseEntity<?> createAccount(Authentication authentication) {
        try {
            Client client = clientRepository.findByEmail(authentication.getName());

            if (client.getAccounts().size() == 3) {
                return new ResponseEntity<>("You cannot create a new account at this time. You have reached the maximum number of allowed accounts (3)", HttpStatus.FORBIDDEN);
            }

            // Genera el número de cuenta único dentro del controlador
            String accountNumber = generateUniqueAccountNumber();

            Account newAccount = new Account(accountNumber, LocalDate.now(), 0.0);
            client.addAccount(newAccount);
            accountRepository.save(newAccount);
            clientRepository.save(client);

            return new ResponseEntity<>("Account created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating account: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
    @GetMapping("/clients/current/accounts")
    public ResponseEntity<?> getClientAccounts(Authentication authentication){

        Client client = clientRepository.findByEmail(authentication.getName());
        List<Account> clientAccounts = accountRepository.findByOwner(client);
        List<AccountDTO> clientAccountDto = clientAccounts.stream().map(AccountDTO::new).collect(Collectors.toList());

        return new ResponseEntity<>(clientAccountDto, HttpStatus.OK );
    }

    // Método privado para generar un número de cuenta único
    private String generateUniqueAccountNumber() {
        String leftZero;
        do {
            leftZero = String.format("%08d", (int) (Math.random() * (100000000 - 1) + 1));
        } while (accountRepository.existsByAccountNumber("VIN" + leftZero));

        return "VIN" + leftZero;
    }

}
