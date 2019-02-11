package com.aaparicio.redis;

public class ExpirationException extends RuntimeException {
    public ExpirationException(String msg) {
        super(msg);
    }
}
