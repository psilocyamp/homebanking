package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CvvGenerator {

    @Autowired
    private static CardRepository cardRepository;

    public CvvGenerator(CardRepository cardRepository) {
        CvvGenerator.cardRepository = cardRepository;
    }

    public static String cvvNumber(){

        String cvvNumber;

        do{
            cvvNumber = String.format("%03d", (int)(Math.random() * (1000 - 1) + 1));
        }while (cardRepository.existsByCvv(cvvNumber));

        return cvvNumber;
    }

}
