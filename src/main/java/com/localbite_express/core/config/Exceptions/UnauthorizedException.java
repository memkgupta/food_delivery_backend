package com.localbite_express.core.config.Exceptions;



public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException() {
        super("Not authorized");
    }
}
