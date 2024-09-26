package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.CreateCardDTO;
import com.mindhub.homebanking.exceptions.ClientNotFoundException;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Type;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientServices;
import com.mindhub.homebanking.utils.CardNumberGenerator;
import com.mindhub.homebanking.utils.CvvGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.models.Type.DEBIT;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private ClientServices clientServices;

    @Autowired
    private CardRepository cardRepository;

    @Override
    public void validateCard(CreateCardDTO createCardDTO, Set<Card> clientCards) {
        if (createCardDTO.type() == null ||createCardDTO.type().isBlank())  {
            throw new IllegalArgumentException("Card type cannot be empty");
        }
        if (createCardDTO.cardColor() == null || createCardDTO.cardColor().isBlank()) {
            throw new IllegalArgumentException("Card color cannot be empty");
        }
        Type type= Type.valueOf(createCardDTO.type().toUpperCase());
        CardColor color = CardColor.valueOf(createCardDTO.cardColor().toUpperCase());

        Long countCardType=clientCards.stream().filter(card -> card.getType() == type).count();
        if(countCardType >= 3){
            throw new RuntimeException("You have reached the maximum number of allowed Debit cards (3)");
        }
        if (clientCards.stream().anyMatch(card -> card.getType() == type && card.getColor() == color)) {
            throw new RuntimeException("You already have a card with this color and type");
        }
    }
    @Override
    public Card createNewCard(CreateCardDTO createCardDTO) {
        Type type= Type.valueOf(createCardDTO.type().toUpperCase());
        CardColor color = CardColor.valueOf(createCardDTO.cardColor().toUpperCase());
        return new Card(type, color, CardNumberGenerator.generateCardNumber(), CvvGenerator.cvvNumber(), LocalDate.now(), LocalDate.now().plusYears(5));
    }
    @Override
    public String createCard(CreateCardDTO createCardDTO, Authentication authentication) {
        Client client = clientServices.getAuthenticatedClient(authentication);
        validateCard(createCardDTO, client.getClientCards());
        Card card = createNewCard(createCardDTO);
        clientServices.addCardToClient(client, card);
        cardRepository.save(card);
        clientServices.saveClient(client);
        return "Card created successfully";
    }

    @Override
    public Set<CardDTO> getClientCards(Authentication authentication) {
        Client client = clientServices.findClientByEmail(authentication.getName()) ;
        Set<Card> clientCards = client.getClientCards();
        return clientCards.stream().map(CardDTO::new).collect(Collectors.toSet());
    }
}
