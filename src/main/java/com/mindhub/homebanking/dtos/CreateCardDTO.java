package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

public record CreateCardDTO(CardType cardType, CardColor cardColor) {
}
