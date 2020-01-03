package com.apsi.repo.user.exception;

public class BadPreviousPasswordException extends Exception {
    public BadPreviousPasswordException(){
        super();
    }

    public BadPreviousPasswordException(String msg){
        super(msg);
    }
}
