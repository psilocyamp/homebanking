package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoginDTO;
import com.mindhub.homebanking.dtos.RegisterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface AuthService {
    ResponseEntity<?> login(LoginDTO loginDTO);
    ResponseEntity<?> registerVerifications(RegisterDTO registerDTO);
    void saveClientAndAddAccount(RegisterDTO registerDTO);
    ResponseEntity<?> getClientDetails(Authentication authentication);
}