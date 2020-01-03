package com.apsi.repo.user.exception;

public class UserExistsException extends Exception {

    public UserExistsException() {
        super();
    }

    public UserExistsException(String message) {
        super(message);
    }
}
