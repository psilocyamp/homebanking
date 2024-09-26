package com.mindhub.homebanking.dtos;

public record MakeTransactionDTO (String sourceAccount, String destinationAccount, Double amount, String description) {
}
