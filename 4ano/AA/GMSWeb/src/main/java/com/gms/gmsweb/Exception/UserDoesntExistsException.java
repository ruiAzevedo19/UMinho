package com.gms.Exception;

public class UserDoesntExistsException extends Exception{
    public UserDoesntExistsException(String error) {
        super(error);
    }
}
