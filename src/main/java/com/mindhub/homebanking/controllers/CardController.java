package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.CreateCardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class CardController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CardRepository cardRepository;

    @PostMapping("/current/cards")
    public ResponseEntity<?> createCard(@RequestBody CreateCardDTO createCardDTO, Authentication authentication){

        try{
            Client client = clientRepository.findByEmail(authentication.getName());

            Set<Card> cardsDebit = client.getClientCards().stream().filter(card -> card.getType() == CardType.DEBIT).collect(Collectors.toSet());
            Set<Card> cardsCredit = client.getClientCards().stream().filter(card -> card.getType() == CardType.CREDIT).collect(Collectors.toSet());


            if(cardsDebit.size() == 3 && createCardDTO.cardType() == CardType.DEBIT){
                return new ResponseEntity<>("You have reached the maximum number of allowed Debit cards (3)", HttpStatus.FORBIDDEN);
            }

            if(cardsCredit.size() == 3 && createCardDTO.cardType() == CardType.CREDIT){
                return new ResponseEntity<>("You have reached the maximum number of allowed Credit cards (3)", HttpStatus.FORBIDDEN);
            }

            Card newCard = new Card(createCardDTO.cardType(), createCardDTO.cardColor(), LocalDate.now(), LocalDate.now().plusYears(5));
            client.addClientCard(newCard);

            cardRepository.save(newCard);
            clientRepository.save(client);

            return new ResponseEntity<>("Card created", HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error creating card: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/current/cards")
    public ResponseEntity<?> getClientCards(Authentication authentication){

        Client client = clientRepository.findByEmail(authentication.getName());
        Set<Card> clientCards = client.getClientCards();
        Set<CardDTO> cardDto = clientCards.stream().map(CardDTO::new).collect(Collectors.toSet());

        return new ResponseEntity<>(cardDto, HttpStatus.OK);
    }
}

