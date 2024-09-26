package com.mindhub.homebanking.dtos;

public record LoanApplicationDTO(Long id, double amount, int payments, String destinationAccount) {
}
