package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.exceptions.AccountNotFoundException;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountServices;
import com.mindhub.homebanking.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.AccountNumberGenerator.makeAccountNumber;

@Service
public class AccountServicesImpl implements AccountServices {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientServices clientServices;


    @Override
    public String createAccount(Authentication authentication) {
        Client client = clientServices.getAuthenticatedClient(authentication);
        validateAccountLimit(client);
        String accountNumber = makeAccountNumber();
        Account newAccount = createNewAccount();
        clientServices.addAccountToClient(client, newAccount);
        saveAccount(newAccount);
        clientServices.saveClient(client);
        return "Account created";
    }

    private void validateAccountLimit(Client client) {
        if (client.getAccounts().size() >= 3) {
            throw new RuntimeException("You cannot create a new account at this time. Maximum accounts reached.");
        }
    }

    @Override
    public List<AccountDTO> getAllTransactions() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @Override
    public AccountDTO getAccountById(Long id) throws AccountNotFoundException {
        return accountRepository.findById(id)
                .map(AccountDTO::new)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + id + " not found"));
    }

    @Override
    public List<AccountDTO> getAccountsByClient(Long clientId) {
        return accountRepository.findByClientId(clientId).stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> getClientAccounts(Authentication authentication) {
        Client client = clientServices.getAuthenticatedClient(authentication);
        return accountRepository.findByClient(client).stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    }
    @Override
    public String createAccountNumber(){
        return "VIN-" + makeAccountNumber();
    }

    @Override
    public Boolean existAccountNumber(String accountNumber){
        return accountRepository.existsByNumber(accountNumber);
    }

    @Override
    public String generateAndValidateAccountNumber(){
        String accountNumber;
        do{
            accountNumber = createAccountNumber();
        }while(existAccountNumber(accountNumber));
        return accountNumber;
    }
    @Override
    public Account findAccountByNumber(String accountNumber){
        return accountRepository.findByNumber(accountNumber).orElse(null);
    }

    @Override
    public Account createNewAccount(){
        return new Account(generateAndValidateAccountNumber(), LocalDate.now(), 0.0);
    }

    @Override
    public void saveAccount(Account account){
        accountRepository.save(account);
    }
}
