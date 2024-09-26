package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.MakeTransactionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface TransactionServices {
    ResponseEntity<?> makeTransaction(MakeTransactionDTO makeTransactionDTO, Authentication authentication);
}

