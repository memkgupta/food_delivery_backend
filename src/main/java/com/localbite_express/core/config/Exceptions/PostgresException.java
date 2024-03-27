package com.localbite_express.core.config.Exceptions;

public class PostgresException extends RuntimeException{
    public PostgresException(String message) {
        super(message);
    }
}
