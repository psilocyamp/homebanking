package com.mindhub.homebanking.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CardNumberGenerator {
@Bean
    public static String generateCardNumber() {
        String cardNumber;
            StringBuilder cardNumberBuilder = new StringBuilder();

            for (int i = 0; i < 4; i++) {
                String randomNumbers = String.format("%04d", (int) (Math.random() * 9999));
                cardNumberBuilder.append(randomNumbers);
                if (i < 3) {
                    cardNumberBuilder.append("-");
                }
            }

            cardNumber = cardNumberBuilder.toString();
        return cardNumber;
    }
}
