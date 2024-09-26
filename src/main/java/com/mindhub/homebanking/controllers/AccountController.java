package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.services.AccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountServices accountServices;

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDTO>> getAllTr() {
        List<AccountDTO> transactions = accountServices.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id) {
        AccountDTO accountDTO = accountServices.getAccountById(id);
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @GetMapping("/clients/{clientId}/accounts")
    public ResponseEntity<List<AccountDTO>> getAccountsByClient(@PathVariable Long clientId) {
        List<AccountDTO> accountDTOs = accountServices.getAccountsByClient(clientId);
        return new ResponseEntity<>(accountDTOs, HttpStatus.OK);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<?> createAccount(Authentication authentication) {
        try {
            String result = accountServices.createAccount(authentication);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating account: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/clients/current/accounts")
    public ResponseEntity<List<AccountDTO>> getClientAccounts(Authentication authentication) {
        List<AccountDTO> clientAccounts = accountServices.getClientAccounts(authentication);
        return new ResponseEntity<>(clientAccounts, HttpStatus.OK);
    }
}
