package com.apsi.repo.user.exception;

public class SamePasswordException extends Exception {
    public SamePasswordException(){
        super();
    }

    public SamePasswordException(String msg) {
        super(msg);
    }
}
