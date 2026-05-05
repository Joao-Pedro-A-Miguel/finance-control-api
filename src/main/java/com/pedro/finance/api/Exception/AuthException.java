package com.pedro.finance.api.Exception;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
