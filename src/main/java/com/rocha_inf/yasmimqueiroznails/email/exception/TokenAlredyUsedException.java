package com.rocha_inf.yasmimqueiroznails.email.exception;

public class TokenAlredyUsedException extends RuntimeException {
    public TokenAlredyUsedException(String message) {
        super(message);
    }
}
