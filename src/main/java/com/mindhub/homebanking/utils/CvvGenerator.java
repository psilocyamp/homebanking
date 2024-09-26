package com.mindhub.homebanking.utils;

import org.springframework.stereotype.Component;

@Component
public class CvvGenerator {

    public static int cvvNumber() {
        return   (int)(Math.random() * 1000);
    }
}

