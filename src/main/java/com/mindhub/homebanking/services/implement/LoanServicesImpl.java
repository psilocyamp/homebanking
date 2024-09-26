package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.exceptions.AccountNotFoundException;
import com.mindhub.homebanking.exceptions.InvalidInputException;
import com.mindhub.homebanking.exceptions.LoanNotFoundException;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.AccountServices;
import com.mindhub.homebanking.services.ClientServices;
import com.mindhub.homebanking.services.LoanServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServicesImpl implements LoanServices {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientServices clientServices;

    @Autowired
    private AccountServices accountServices;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public List<LoanDTO> getAllLoanDTO() {
        return getAllLoans().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ResponseEntity<?> applyLoan(Authentication authentication, LoanApplicationDTO loanApplicationDTO) {
        Client client = clientServices.getAuthenticatedClient(authentication);
        Account account = accountServices.findAccountByNumber(loanApplicationDTO.destinationAccount());
        Loan loan = findLoanById(loanApplicationDTO.id());

        if(validateLoanApplication(loanApplicationDTO, account, loan, client) != null){
        return validateLoanApplication(loanApplicationDTO, account,loan,client);
        }

        double interestRate = determineInterestRate(loanApplicationDTO.payments());
        double finalAmount = calculateFinalAmount(loanApplicationDTO.amount(), interestRate);

        ClientLoan clientLoan = new ClientLoan(finalAmount, loanApplicationDTO.payments());
        clientLoan.setClient(client);
        clientLoan.setLoan(loan);
        clientLoanRepository.save(clientLoan);

        createAndSaveTransaction(finalAmount, loan, account);

        return new ResponseEntity<>("Loan application completed successfully", HttpStatus.CREATED);
    }

    private ResponseEntity<?> validateLoanApplication(LoanApplicationDTO dto, Account account, Loan loan, Client client) {
        try{
        if (dto.destinationAccount().isBlank()) {
            throw new InvalidInputException("The destination account must not be empty");
        }

        if (dto.amount() <= 0) {
            throw new InvalidInputException("Loan amount must be greater than zero");
        }

        if (dto.payments() <= 0) {
            throw new InvalidInputException("Number of payments must be greater than zero");
        }

        if (!loan.getPayments().contains(dto.payments())) {
                return new ResponseEntity<>("The selected number of payments is not allowed for this loan.", HttpStatus.BAD_REQUEST);
        }
        if (!accountServices.existAccountNumber(dto.destinationAccount())) {
                throw new AccountNotFoundException("The account number provided does not exist");
        }
        if (!account.getClient().equals(client)) {
                return new ResponseEntity<>("The account you specified for the loan does not belong to you. Please ensure that you are applying for the loan with an account you own.", HttpStatus.FORBIDDEN);
        }

        }catch (InvalidInputException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        return null;
    }

    private double determineInterestRate(int payments) {
        if (payments == 12) {
            return 0.20;
        } else if (payments > 12) {
            return 0.25;
        } else {
            return 0.15;
        }
    }

    private double calculateFinalAmount(double amount, double interestRate) {
        return amount * (1 + interestRate);
    }

    private void createAndSaveTransaction(double amount, Loan loan, Account account) {
        Transaction creditTransaction = new Transaction(amount,
                "Loan approved: " + loan.getName(),
                LocalDateTime.now(),
                TransactionType.CREDIT);
        account.addTransaction(creditTransaction);

        account.setBalance(account.getBalance() + amount);
        transactionRepository.save(creditTransaction);
        accountServices.saveAccount(account);
    }


    private Loan findLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Loan type not found"));
    }
}
