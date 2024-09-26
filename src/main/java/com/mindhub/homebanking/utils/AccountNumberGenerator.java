package com.mindhub.homebanking.utils;

public class AccountNumberGenerator {


    public static String makeAccountNumber() {
          return String.format("%08d", (int) (Math.random() * (100000000 - 1) + 1));
    }

}