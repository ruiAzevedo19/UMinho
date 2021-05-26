package com.gms.Exception;

public class GameDoesntExistsException extends Exception{
    public GameDoesntExistsException(String error) {
        super(error);
    }
}
