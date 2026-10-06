package com.rocha_inf.yasmimqueiroznails.email.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
    }
}
