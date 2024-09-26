package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.services.LoanServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanServices loanServices;

    @GetMapping("/")
    public ResponseEntity<?> getLoans() {
        return ResponseEntity.ok(loanServices.getAllLoanDTO());
    }

    @Transactional
    @PostMapping("/")
    public ResponseEntity<?> createLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
         return loanServices.applyLoan(authentication, loanApplicationDTO);
    }


}
