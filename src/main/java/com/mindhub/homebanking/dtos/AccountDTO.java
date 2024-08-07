package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
   private Long id;
    private String number;
    private double balance;
    private Set<TransactionDTO> transactions= new HashSet<>();

    // Constructor
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream()
                .map(transaction -> new TransactionDTO(transaction))
                .collect(Collectors.toSet());
    }

    // Getters y Setters
    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public Long getId() {
        return id;
    }
    public Set<TransactionDTO> getTransactions() {
        return transactions;

    }
}