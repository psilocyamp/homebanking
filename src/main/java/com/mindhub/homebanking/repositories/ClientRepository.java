package com.mindhub.homebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mindhub.homebanking.models.Client;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional <Client> findByEmail(String email);
}


