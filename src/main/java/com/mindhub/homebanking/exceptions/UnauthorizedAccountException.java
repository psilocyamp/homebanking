package com.mindhub.homebanking.exceptions;

public class UnauthorizedAccountException extends RuntimeException {
    public UnauthorizedAccountException(String message) {
        super(message);
    }
}
