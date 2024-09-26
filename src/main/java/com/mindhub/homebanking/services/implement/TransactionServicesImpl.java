package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.MakeTransactionDTO;
import com.mindhub.homebanking.exceptions.*;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountServices;
import com.mindhub.homebanking.services.ClientServices;
import com.mindhub.homebanking.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionServicesImpl implements TransactionServices {

    @Autowired
    private AccountServices accountServices;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientServices clientServices;

    @Override
    @Transactional
    public ResponseEntity<?> makeTransaction(MakeTransactionDTO makeTransactionDTO, Authentication authentication) {
        Client client = clientServices.getAuthenticatedClient(authentication);

        Account sourceAccount = accountServices.findAccountByNumber(makeTransactionDTO.sourceAccount());
        Account destinationAccount = accountServices.findAccountByNumber(makeTransactionDTO.destinationAccount());


        validateTransaction(makeTransactionDTO, sourceAccount, client);

        // Crear las transacciones de débito y crédito
        Transaction sourceTransaction = new Transaction(
                -makeTransactionDTO.amount(),
                makeTransactionDTO.description() + " - " + makeTransactionDTO.sourceAccount(),
                LocalDateTime.now(),
                TransactionType.DEBIT
        );
        sourceAccount.addTransaction(sourceTransaction);
        transactionRepository.save(sourceTransaction);

        Transaction destinationTransaction = new Transaction(
                makeTransactionDTO.amount(),
                makeTransactionDTO.description() + " - " + makeTransactionDTO.destinationAccount(),
                LocalDateTime.now(),
                TransactionType.CREDIT
        );
        destinationAccount.addTransaction(destinationTransaction);
        transactionRepository.save(destinationTransaction);

        // Actualizar saldos
        sourceAccount.setBalance(sourceAccount.getBalance() - makeTransactionDTO.amount());
        accountServices.saveAccount(sourceAccount);

        destinationAccount.setBalance(destinationAccount.getBalance() + makeTransactionDTO.amount());
        accountServices.saveAccount(destinationAccount);

        return new ResponseEntity<>("Transaction completed successfully", HttpStatus.CREATED);
    }

    private void validateTransaction(MakeTransactionDTO dto, Account sourceAccount, Client client) {
        if (dto.sourceAccount().isBlank()) {
            throw new InvalidTransactionException("The source account field must not be empty");
        }

        if (dto.destinationAccount().isBlank()) {
            throw new InvalidTransactionException("The destination account field must not be empty");
        }

        if (dto.amount() == null || dto.amount().isNaN() || dto.amount() <= 0) {
            throw new InvalidTransactionException("Enter a valid amount");
        }

        if (dto.description().isBlank()) {
            throw new InvalidTransactionException("The description field must not be empty");
        }

        if (dto.sourceAccount().equals(dto.destinationAccount())) {
            throw new InvalidTransactionException("The source account and the destination account must not be the same");
        }

        if (!accountServices.existAccountNumber(dto.sourceAccount())) {
            throw new AccountNotFoundException("The source account entered does not exist");
        }

        if (!accountServices.existAccountNumber(dto.destinationAccount())) {
            throw new AccountNotFoundException("The destination account entered does not exist");
        }

        if (sourceAccount.getBalance() < dto.amount()) {
            throw new InsufficientBalanceException("You do not have sufficient balance to carry out the operation");
        }
    }
    private Account findAccountByNumber(String accountNumber) {
        return accountServices.findAccountByNumber(accountNumber);
    }
}
