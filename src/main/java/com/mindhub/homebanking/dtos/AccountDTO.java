package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

public class AccountDTO {
   private Long id;
    private String number;
    private double balance;

    // Constructor
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.balance = account.getBalance();
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
}