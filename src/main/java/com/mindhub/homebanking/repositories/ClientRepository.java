package com.mindhub.homebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mindhub.homebanking.models.Client;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByEmail(String email);
}


