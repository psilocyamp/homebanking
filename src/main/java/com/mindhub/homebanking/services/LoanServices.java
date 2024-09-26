package com.mindhub.homebanking.services;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;


import java.util.List;


public interface LoanServices {
    List <Loan> getAllLoans();

    List <LoanDTO> getAllLoanDTO();

    ResponseEntity<?> applyLoan(Authentication authentication, LoanApplicationDTO loanDTO);


}
