package com.apsi.repo.user.exception;

public class UserByMailExistsException extends Exception {
    public UserByMailExistsException() {
        super();
    }

    public UserByMailExistsException(String msg) {
        super(msg);
    }
}
