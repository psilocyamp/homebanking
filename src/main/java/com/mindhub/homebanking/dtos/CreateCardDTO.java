package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.Type;

public record CreateCardDTO (String type, String cardColor) {
}
