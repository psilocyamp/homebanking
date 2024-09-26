package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.dtos.MakeTransactionDTO;
import com.mindhub.homebanking.exceptions.AccountNotFoundException;
import com.mindhub.homebanking.exceptions.InsufficientBalanceException;
import com.mindhub.homebanking.exceptions.InvalidTransactionException;
import com.mindhub.homebanking.exceptions.UnauthorizedAccountException;
import com.mindhub.homebanking.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionServices transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<?> makeTransaction(@RequestBody MakeTransactionDTO makeTransactionDTO, Authentication authentication) {
        try {
            return transactionService.makeTransaction(makeTransactionDTO, authentication);
        } catch (InvalidTransactionException | AccountNotFoundException | InsufficientBalanceException |
                 UnauthorizedAccountException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>("Error making transaction: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
