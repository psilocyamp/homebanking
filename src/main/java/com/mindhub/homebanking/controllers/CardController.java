package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.CreateCardDTO;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/clients")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/current/cards")
    public ResponseEntity<?> createCard(@RequestBody CreateCardDTO createCardDTO, Authentication authentication) {
        try {
            String response = cardService.createCard(createCardDTO, authentication);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/current/cards")
    public ResponseEntity<Set<CardDTO>> getClientCards(Authentication authentication) {
        Set<CardDTO> cards = cardService.getClientCards(authentication);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }
}