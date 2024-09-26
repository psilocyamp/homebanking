package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.exceptions.AccountNotFoundException;
import com.mindhub.homebanking.models.Account;
import org.springframework.security.core.Authentication;
import java.util.List;


public interface AccountServices {

        List<AccountDTO> getAllTransactions();
        AccountDTO getAccountById(Long id) throws AccountNotFoundException;
        List<AccountDTO> getAccountsByClient(Long clientId);
        String createAccount(Authentication authentication);
        List<AccountDTO> getClientAccounts(Authentication authentication);
        Account findAccountByNumber(String accountNumber);
        void saveAccount(Account account);
        String createAccountNumber();
        Boolean existAccountNumber(String accountNumber);
        String generateAndValidateAccountNumber();
        Account createNewAccount();
}
