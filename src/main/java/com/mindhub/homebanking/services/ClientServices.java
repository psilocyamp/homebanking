package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.CreateCardDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

public interface ClientServices {
    List<Client> getAllClient();
    List<ClientDTO> getAllClientDTO();
    Client findClientByEmail(String email);
    Client getClientById(Long id);
    ClientDTO getClientDTO(Client client);
    Client getAuthenticatedClient(Authentication authentication);
    void addAccountToClient(Client client, Account account);
    void saveClient(Client client);
    void addCardToClient(Client client, Card card);
}