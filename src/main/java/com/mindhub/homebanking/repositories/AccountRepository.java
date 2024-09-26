package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;

import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Boolean existsByNumber(String number);
    List<Account> findByClient(Client client);
    List<Account> findByClientId(Long clientId);
    Boolean existsByIdAndClient(Long accountId, Client client);
    Optional<Account> findByNumber(String number);
}
