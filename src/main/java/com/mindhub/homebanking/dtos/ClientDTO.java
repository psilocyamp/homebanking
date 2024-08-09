package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
   private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<AccountDTO> accounts = new HashSet<>();
    private List<ClientLoanDTO> loans = new ArrayList<>();

    // Constructor
    public ClientDTO(Client client) {
        this.id = client.getId();
        this.lastName = client.getLastName();
        this.firstName = client.getFirstName();
        this.email = client.getEmail();

        this.accounts = client.getAccounts()
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toSet());

        this.loans =client.getClientLoans()
                .stream()
                .map(ClientLoanDTO::new)
                .collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }
    public List<ClientLoanDTO> getLoans() {
        return loans;
    }
    }