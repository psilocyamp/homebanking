package com.mindhub.homebanking.models.utils;

import java.util.Random;

public class CardNumberGenerator {

    public String generateCardNumber() {
        Random random = new Random();
        String number = "";
        String cardNumber = "";
        for (int i = 0; i < 4; i++) {
            cardNumber += random.nextInt((9999 - 1000) + 1) + 1000;
            if (i < 3) {
                cardNumber += "-";
            }
        }
        return cardNumber;
    }
}
