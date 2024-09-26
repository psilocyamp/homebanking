package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.CreateCardDTO;
import com.mindhub.homebanking.models.Card;
import org.springframework.security.core.Authentication;

import java.util.Set;

public interface CardService {
    String createCard(CreateCardDTO createCardDTO, Authentication authentication);
    Set<CardDTO> getClientCards(Authentication authentication);
    void validateCard(CreateCardDTO createCardDTO, Set<Card> clientCards);
    Card createNewCard(CreateCardDTO createCardDTO);
}