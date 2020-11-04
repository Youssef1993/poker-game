package com.poker.game.exceptions;

public class InvalidDataException extends RuntimeException{
    public InvalidDataException(String msg) {
        super(msg);
    }
}
