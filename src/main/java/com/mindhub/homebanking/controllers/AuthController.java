package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoginDTO;
import com.mindhub.homebanking.dtos.RegisterDTO;
import com.mindhub.homebanking.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerVerifications(@RequestBody RegisterDTO registerDTO) {
        return authService.registerVerifications(registerDTO);
    }


    @GetMapping("/current")
    public ResponseEntity<?> getClientDetails(Authentication authentication) {
        return authService.getClientDetails(authentication);
    }
}
